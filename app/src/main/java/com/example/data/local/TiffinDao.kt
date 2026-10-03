package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TiffinDao {

    // Customer Queries
    @Query("SELECT * FROM customers ORDER BY name ASC")
    fun getAllCustomers(): Flow<List<CustomerEntity>>

    @Query("SELECT * FROM customers WHERE id = :id LIMIT 1")
    suspend fun getCustomerById(id: Long): CustomerEntity?

    @Query("SELECT * FROM customers WHERE id = :id LIMIT 1")
    fun getCustomerByIdFlow(id: Long): Flow<CustomerEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCustomer(customer: CustomerEntity): Long

    @Update
    suspend fun updateCustomer(customer: CustomerEntity)

    @Query("DELETE FROM customers WHERE id = :id")
    suspend fun deleteCustomerById(id: Long)


    // Meal Plans
    @Query("SELECT * FROM meal_plans ORDER BY name ASC")
    fun getAllMealPlans(): Flow<List<MealPlanEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMealPlan(plan: MealPlanEntity): Long

    @Update
    suspend fun updateMealPlan(plan: MealPlanEntity)

    @Query("DELETE FROM meal_plans WHERE id = :id")
    suspend fun deleteMealPlanById(id: Long)


    // Subscriptions
    @Query("SELECT * FROM subscriptions WHERE customerId = :customerId ORDER BY id DESC")
    fun getSubscriptionsForCustomer(customerId: Long): Flow<List<SubscriptionEntity>>

    @Query("SELECT * FROM subscriptions WHERE status = 'ACTIVE'")
    fun getActiveSubscriptions(): Flow<List<SubscriptionEntity>>

    @Query("SELECT * FROM subscriptions ORDER BY id DESC")
    fun getAllSubscriptions(): Flow<List<SubscriptionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubscription(subscription: SubscriptionEntity): Long

    @Update
    suspend fun updateSubscription(subscription: SubscriptionEntity)

    @Query("DELETE FROM subscriptions WHERE id = :id")
    suspend fun deleteSubscriptionById(id: Long)


    // Delivery Records
    @Query("SELECT * FROM delivery_records WHERE date = :date ORDER BY area ASC, customerName ASC")
    fun getDeliveriesByDate(date: String): Flow<List<DeliveryRecordEntity>>

    @Query("SELECT * FROM delivery_records WHERE customerId = :customerId ORDER BY date DESC")
    fun getDeliveriesForCustomer(customerId: Long): Flow<List<DeliveryRecordEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDelivery(delivery: DeliveryRecordEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDeliveries(deliveries: List<DeliveryRecordEntity>)

    @Update
    suspend fun updateDelivery(delivery: DeliveryRecordEntity)

    @Query("UPDATE delivery_records SET status = :status, updatedAt = :updatedAt WHERE id = :id")
    suspend fun updateDeliveryStatus(id: Long, status: String, updatedAt: Long = System.currentTimeMillis())

    @Query("DELETE FROM delivery_records WHERE date = :date")
    suspend fun deleteDeliveriesForDate(date: String)


    // Payment Records
    @Query("SELECT * FROM payment_records ORDER BY timestamp DESC")
    fun getAllPayments(): Flow<List<PaymentRecordEntity>>

    @Query("SELECT * FROM payment_records WHERE customerId = :customerId ORDER BY timestamp DESC")
    fun getPaymentsForCustomer(customerId: Long): Flow<List<PaymentRecordEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPayment(payment: PaymentRecordEntity): Long

    @Query("DELETE FROM payment_records WHERE id = :id")
    suspend fun deletePaymentById(id: Long)


    // Pause Schedules
    @Query("SELECT * FROM pause_schedules WHERE customerId = :customerId")
    fun getPausesForCustomer(customerId: Long): Flow<List<PauseScheduleEntity>>

    @Query("SELECT * FROM pause_schedules")
    fun getAllPauses(): Flow<List<PauseScheduleEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPause(pause: PauseScheduleEntity): Long

    @Query("DELETE FROM pause_schedules WHERE id = :id")
    suspend fun deletePauseById(id: Long)
}
