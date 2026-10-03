package com.example.data.repository

import com.example.data.SampleData
import com.example.data.local.CustomerEntity
import com.example.data.local.DeliveryRecordEntity
import com.example.data.local.MealPlanEntity
import com.example.data.local.PauseScheduleEntity
import com.example.data.local.PaymentRecordEntity
import com.example.data.local.SubscriptionEntity
import com.example.data.local.TiffinDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

class TiffinRepository(private val dao: TiffinDao) {

    suspend fun initializeSampleDataIfNeeded() = withContext(Dispatchers.IO) {
        val currentCustomers = dao.getAllCustomers().first()
        if (currentCustomers.isEmpty()) {
            // Seed Customers
            SampleData.sampleCustomers.forEach { dao.insertCustomer(it) }
            
            // Seed Meal Plans
            SampleData.sampleMealPlans.forEach { dao.insertMealPlan(it) }
            
            // Seed Subscriptions
            SampleData.getSampleSubscriptions().forEach { dao.insertSubscription(it) }
            
            // Seed Deliveries
            dao.insertDeliveries(SampleData.getSampleTodayDeliveries())
            
            // Seed Payments
            SampleData.samplePayments.forEach { dao.insertPayment(it) }
            
            // Seed Pauses
            SampleData.samplePauses.forEach { dao.insertPause(it) }
        } else {
            // Check if today's deliveries exist; if not, create them based on active subscriptions
            val today = SampleData.getTodayDate()
            val todayDeliveries = dao.getDeliveriesByDate(today).first()
            if (todayDeliveries.isEmpty()) {
                generateTodayDeliveriesFromSubscriptions(today)
            }
        }
    }

    private suspend fun generateTodayDeliveriesFromSubscriptions(date: String) {
        val customers = dao.getAllCustomers().first().associateBy { it.id }
        val activeSubs = dao.getActiveSubscriptions().first()
        val deliveriesToInsert = mutableListOf<DeliveryRecordEntity>()

        for (sub in activeSubs) {
            val customer = customers[sub.customerId] ?: continue
            
            // Add Lunch if plan includes lunch
            if (sub.mealTime == "LUNCH" || sub.mealTime == "BOTH") {
                deliveriesToInsert.add(
                    DeliveryRecordEntity(
                        date = date,
                        customerId = customer.id,
                        customerName = customer.name,
                        phone = customer.phone,
                        address = customer.address,
                        area = customer.area,
                        mealTime = "LUNCH",
                        dietaryType = sub.dietaryType,
                        status = "PENDING",
                        deliveryNotes = customer.deliveryNotes
                    )
                )
            }

            // Add Dinner if plan includes dinner
            if (sub.mealTime == "DINNER" || sub.mealTime == "BOTH") {
                deliveriesToInsert.add(
                    DeliveryRecordEntity(
                        date = date,
                        customerId = customer.id,
                        customerName = customer.name,
                        phone = customer.phone,
                        address = customer.address,
                        area = customer.area,
                        mealTime = "DINNER",
                        dietaryType = sub.dietaryType,
                        status = "PENDING",
                        deliveryNotes = customer.deliveryNotes
                    )
                )
            }
        }

        if (deliveriesToInsert.isNotEmpty()) {
            dao.insertDeliveries(deliveriesToInsert)
        }
    }

    // Customers
    val allCustomers: Flow<List<CustomerEntity>> = dao.getAllCustomers()

    suspend fun getCustomerById(id: Long): CustomerEntity? = dao.getCustomerById(id)

    fun getCustomerByIdFlow(id: Long): Flow<CustomerEntity?> = dao.getCustomerByIdFlow(id)

    suspend fun saveCustomer(customer: CustomerEntity): Long {
        return if (customer.id == 0L) {
            dao.insertCustomer(customer)
        } else {
            dao.updateCustomer(customer)
            customer.id
        }
    }

    suspend fun deleteCustomer(id: Long) = dao.deleteCustomerById(id)

    // Meal Plans
    val allMealPlans: Flow<List<MealPlanEntity>> = dao.getAllMealPlans()

    suspend fun saveMealPlan(plan: MealPlanEntity): Long {
        return if (plan.id == 0L) {
            dao.insertMealPlan(plan)
        } else {
            dao.updateMealPlan(plan)
            plan.id
        }
    }

    suspend fun deleteMealPlan(id: Long) = dao.deleteMealPlanById(id)

    // Subscriptions
    val allSubscriptions: Flow<List<SubscriptionEntity>> = dao.getAllSubscriptions()

    fun getSubscriptionsForCustomer(customerId: Long): Flow<List<SubscriptionEntity>> =
        dao.getSubscriptionsForCustomer(customerId)

    suspend fun saveSubscription(subscription: SubscriptionEntity): Long {
        return if (subscription.id == 0L) {
            dao.insertSubscription(subscription)
        } else {
            dao.updateSubscription(subscription)
            subscription.id
        }
    }

    suspend fun deleteSubscription(id: Long) = dao.deleteSubscriptionById(id)

    // Deliveries
    fun getDeliveriesByDate(date: String): Flow<List<DeliveryRecordEntity>> =
        dao.getDeliveriesByDate(date)

    fun getDeliveriesForCustomer(customerId: Long): Flow<List<DeliveryRecordEntity>> =
        dao.getDeliveriesForCustomer(customerId)

    suspend fun updateDeliveryStatus(id: Long, status: String) {
        dao.updateDeliveryStatus(id, status)
    }

    suspend fun addDelivery(delivery: DeliveryRecordEntity): Long = dao.insertDelivery(delivery)

    // Payments
    val allPayments: Flow<List<PaymentRecordEntity>> = dao.getAllPayments()

    fun getPaymentsForCustomer(customerId: Long): Flow<List<PaymentRecordEntity>> =
        dao.getPaymentsForCustomer(customerId)

    suspend fun recordPayment(payment: PaymentRecordEntity): Long {
        val paymentId = dao.insertPayment(payment)
        
        // Also update the active subscription paid amount if applicable
        val activeSubs = dao.getSubscriptionsForCustomer(payment.customerId).first()
        val currentActiveSub = activeSubs.firstOrNull { it.status == "ACTIVE" }
        if (currentActiveSub != null) {
            val updatedPaidAmount = currentActiveSub.paidAmount + payment.amount
            dao.updateSubscription(currentActiveSub.copy(paidAmount = updatedPaidAmount))
        }
        
        return paymentId
    }

    // Pauses
    fun getPausesForCustomer(customerId: Long): Flow<List<PauseScheduleEntity>> =
        dao.getPausesForCustomer(customerId)

    suspend fun addPauseSchedule(pause: PauseScheduleEntity): Long = dao.insertPause(pause)

    suspend fun deletePauseSchedule(id: Long) = dao.deletePauseById(id)
}
