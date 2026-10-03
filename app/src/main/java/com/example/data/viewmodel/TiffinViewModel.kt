package com.example.data.viewmodel

import android.app.Application
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.SampleData
import com.example.data.local.CustomerEntity
import com.example.data.local.DeliveryRecordEntity
import com.example.data.local.MealPlanEntity
import com.example.data.local.PauseScheduleEntity
import com.example.data.local.PaymentRecordEntity
import com.example.data.local.SubscriptionEntity
import com.example.data.local.TiffinDatabase
import com.example.data.repository.TiffinRepository
import com.example.ui.util.AppLanguage
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TiffinViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: TiffinRepository

    init {
        val dao = TiffinDatabase.getDatabase(application).tiffinDao()
        repository = TiffinRepository(dao)

        viewModelScope.launch {
            repository.initializeSampleDataIfNeeded()
        }
    }

    // Theme & Language Settings
    val isDarkMode = MutableStateFlow(false)
    val currentLanguage = MutableStateFlow(AppLanguage.ENGLISH)

    fun toggleDarkMode() {
        isDarkMode.value = !isDarkMode.value
    }

    fun setLanguage(language: AppLanguage) {
        currentLanguage.value = language
    }

    // Selected Date for Deliveries
    val selectedDate = MutableStateFlow(SampleData.getTodayDate())

    // Deliveries Filter
    val deliveryMealFilter = MutableStateFlow("ALL") // ALL, LUNCH, DINNER
    val deliveryStatusFilter = MutableStateFlow("ALL") // ALL, PENDING, DELIVERED, SKIPPED
    val deliveryDietFilter = MutableStateFlow("ALL") // ALL, VEG, NON_VEG, JAIN
    val deliverySearchQuery = MutableStateFlow("")

    @OptIn(ExperimentalCoroutinesApi::class)
    val deliveriesForSelectedDate: StateFlow<List<DeliveryRecordEntity>> = selectedDate
        .flatMapLatest { date -> repository.getDeliveriesByDate(date) }
        .combine(deliveryMealFilter) { deliveries, meal ->
            if (meal == "ALL") deliveries else deliveries.filter { it.mealTime == meal }
        }
        .combine(deliveryStatusFilter) { deliveries, status ->
            if (status == "ALL") deliveries else deliveries.filter { it.status == status }
        }
        .combine(deliveryDietFilter) { deliveries, diet ->
            if (diet == "ALL") deliveries else deliveries.filter { it.dietaryType == diet }
        }
        .combine(deliverySearchQuery) { deliveries, query ->
            if (query.isBlank()) deliveries
            else deliveries.filter {
                it.customerName.contains(query, ignoreCase = true) ||
                        it.area.contains(query, ignoreCase = true) ||
                        it.address.contains(query, ignoreCase = true)
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Customers
    val customerSearchQuery = MutableStateFlow("")
    val customerDietFilter = MutableStateFlow("ALL") // ALL, VEG, NON_VEG, JAIN

    val allCustomers: StateFlow<List<CustomerEntity>> = repository.allCustomers
        .combine(customerSearchQuery) { list, query ->
            if (query.isBlank()) list
            else list.filter {
                it.name.contains(query, ignoreCase = true) ||
                        it.phone.contains(query, ignoreCase = true) ||
                        it.area.contains(query, ignoreCase = true)
            }
        }
        .combine(customerDietFilter) { list, diet ->
            if (diet == "ALL") list
            else list.filter { it.dietaryPreference == diet }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Meal Plans
    val allMealPlans: StateFlow<List<MealPlanEntity>> = repository.allMealPlans
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // All Subscriptions
    val allSubscriptions: StateFlow<List<SubscriptionEntity>> = repository.allSubscriptions
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // All Payments
    val allPayments: StateFlow<List<PaymentRecordEntity>> = repository.allPayments
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Selected Customer Details
    private val _selectedCustomerId = MutableStateFlow<Long?>(null)
    val selectedCustomerId = _selectedCustomerId.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val selectedCustomer: StateFlow<CustomerEntity?> = _selectedCustomerId
        .flatMapLatest { id ->
            if (id == null) flowOf(null)
            else repository.getCustomerByIdFlow(id)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    @OptIn(ExperimentalCoroutinesApi::class)
    val selectedCustomerSubscriptions: StateFlow<List<SubscriptionEntity>> = _selectedCustomerId
        .flatMapLatest { id ->
            if (id == null) flowOf(emptyList())
            else repository.getSubscriptionsForCustomer(id)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    @OptIn(ExperimentalCoroutinesApi::class)
    val selectedCustomerPayments: StateFlow<List<PaymentRecordEntity>> = _selectedCustomerId
        .flatMapLatest { id ->
            if (id == null) flowOf(emptyList())
            else repository.getPaymentsForCustomer(id)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    @OptIn(ExperimentalCoroutinesApi::class)
    val selectedCustomerPauses: StateFlow<List<PauseScheduleEntity>> = _selectedCustomerId
        .flatMapLatest { id ->
            if (id == null) flowOf(emptyList())
            else repository.getPausesForCustomer(id)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())


    // --- Actions ---

    fun selectCustomer(id: Long?) {
        _selectedCustomerId.value = id
    }

    fun setDate(date: String) {
        selectedDate.value = date
    }

    fun updateDeliveryStatus(deliveryId: Long, newStatus: String) {
        viewModelScope.launch {
            repository.updateDeliveryStatus(deliveryId, newStatus)
        }
    }

    fun saveCustomer(
        id: Long = 0,
        name: String,
        phone: String,
        address: String,
        area: String,
        dietaryPreference: String,
        deliveryNotes: String
    ) {
        viewModelScope.launch {
            val customer = CustomerEntity(
                id = id,
                name = name.trim(),
                phone = phone.trim(),
                address = address.trim(),
                area = area.trim(),
                dietaryPreference = dietaryPreference,
                deliveryNotes = deliveryNotes.trim()
            )
            repository.saveCustomer(customer)
        }
    }

    fun deleteCustomer(id: Long) {
        viewModelScope.launch {
            repository.deleteCustomer(id)
            if (_selectedCustomerId.value == id) {
                _selectedCustomerId.value = null
            }
        }
    }

    fun saveMealPlan(
        id: Long = 0,
        name: String,
        description: String,
        mealTime: String,
        defaultDiet: String,
        price: Double,
        durationDays: Int,
        totalMeals: Int
    ) {
        viewModelScope.launch {
            val plan = MealPlanEntity(
                id = id,
                name = name.trim(),
                description = description.trim(),
                mealTime = mealTime,
                defaultDiet = defaultDiet,
                price = price,
                durationDays = durationDays,
                totalMeals = totalMeals
            )
            repository.saveMealPlan(plan)
        }
    }

    fun addSubscription(
        customerId: Long,
        planId: Long,
        planName: String,
        startDate: String,
        endDate: String,
        totalMeals: Int,
        price: Double,
        paidAmount: Double,
        mealTime: String,
        dietaryType: String
    ) {
        viewModelScope.launch {
            val subscription = SubscriptionEntity(
                customerId = customerId,
                planId = planId,
                planName = planName,
                startDate = startDate,
                endDate = endDate,
                totalMeals = totalMeals,
                price = price,
                paidAmount = paidAmount,
                mealTime = mealTime,
                dietaryType = dietaryType,
                status = "ACTIVE"
            )
            repository.saveSubscription(subscription)
        }
    }

    fun recordPayment(
        customerId: Long,
        customerName: String,
        amount: Double,
        paymentMethod: String,
        note: String
    ) {
        viewModelScope.launch {
            val payment = PaymentRecordEntity(
                customerId = customerId,
                customerName = customerName,
                amount = amount,
                date = SampleData.getTodayDate(),
                paymentMethod = paymentMethod,
                note = note
            )
            repository.recordPayment(payment)
        }
    }

    fun addPauseSchedule(
        customerId: Long,
        startDate: String,
        endDate: String,
        mealTime: String,
        reason: String
    ) {
        viewModelScope.launch {
            val pause = PauseScheduleEntity(
                customerId = customerId,
                startDate = startDate,
                endDate = endDate,
                mealTime = mealTime,
                reason = reason
            )
            repository.addPauseSchedule(pause)
        }
    }

    fun deletePauseSchedule(pauseId: Long) {
        viewModelScope.launch {
            repository.deletePauseSchedule(pauseId)
        }
    }

    // Direct WhatsApp Message trigger
    fun sendWhatsAppMessage(context: Context, phone: String, message: String) {
        try {
            val cleanPhone = phone.replace("[^0-9+]".toRegex(), "")
            val url = "https://api.whatsapp.com/send?phone=$cleanPhone&text=${Uri.encode(message)}"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            context.startActivity(intent)
        } catch (e: Exception) {
            // Fallback to SMS if WhatsApp fails
            val smsIntent = Intent(Intent.ACTION_VIEW)
            smsIntent.data = Uri.parse("sms:${phone.replace("[^0-9+]".toRegex(), "")}")
            smsIntent.putExtra("sms_body", message)
            try {
                context.startActivity(smsIntent)
            } catch (_: Exception) {}
        }
    }
}
