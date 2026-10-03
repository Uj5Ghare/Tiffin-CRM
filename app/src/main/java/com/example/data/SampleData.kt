package com.example.data

import com.example.data.local.CustomerEntity
import com.example.data.local.DeliveryRecordEntity
import com.example.data.local.MealPlanEntity
import com.example.data.local.PauseScheduleEntity
import com.example.data.local.PaymentRecordEntity
import com.example.data.local.SubscriptionEntity
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object SampleData {

    fun getTodayDate(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return sdf.format(Date())
    }

    fun getDateOffset(days: Int): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, days)
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return sdf.format(calendar.time)
    }

    val sampleCustomers = listOf(
        CustomerEntity(
            id = 1,
            name = "Rahul Sharma",
            phone = "+91 98765 43210",
            address = "Flat 302, Sunrise Apartments",
            area = "Indiranagar",
            dietaryPreference = "VEG",
            deliveryNotes = "Leave at security desk if door locked",
            isActive = true
        ),
        CustomerEntity(
            id = 2,
            name = "Priya Patel",
            phone = "+91 98123 45678",
            address = "No. 45, 2nd Cross, 5th Block",
            area = "Koramangala",
            dietaryPreference = "VEG",
            deliveryNotes = "Ring bell twice, less spicy curry preferred",
            isActive = true
        ),
        CustomerEntity(
            id = 3,
            name = "Ananya Roy",
            phone = "+91 97654 32109",
            address = "Villa 12, Green Glen Layout",
            area = "HSR Layout",
            dietaryPreference = "NON_VEG",
            deliveryNotes = "Non-veg on Wed & Sun, extra rotis",
            isActive = true
        ),
        CustomerEntity(
            id = 4,
            name = "Vikram Singh",
            phone = "+91 99887 76655",
            address = "Tower A-804, Tech Park Heights",
            area = "Whitefield",
            dietaryPreference = "NON_VEG",
            deliveryNotes = "Deliver before 1:00 PM for lunch",
            isActive = true
        ),
        CustomerEntity(
            id = 5,
            name = "Meera Nambiar",
            phone = "+91 91234 56789",
            address = "Plot 88, 10th Main",
            area = "Indiranagar",
            dietaryPreference = "JAIN",
            deliveryNotes = "Strictly Jain food - no onion no garlic",
            isActive = true
        ),
        CustomerEntity(
            id = 6,
            name = "Suresh Kumar",
            phone = "+91 98450 11223",
            address = "Flat 101, BTM Residency",
            area = "BTM Layout",
            dietaryPreference = "VEG",
            deliveryNotes = "Lunch only, call before coming",
            isActive = true
        ),
        CustomerEntity(
            id = 7,
            name = "Kavita Reddy",
            phone = "+91 97312 34567",
            address = "No. 12, 4th Main Road",
            area = "Jayanagar",
            dietaryPreference = "VEG",
            deliveryNotes = "Extra salad required",
            isActive = true
        ),
        CustomerEntity(
            id = 8,
            name = "Amit Verma",
            phone = "+91 96543 21098",
            address = "Flat 204, Opus Enclave",
            area = "Koramangala",
            dietaryPreference = "NON_VEG",
            deliveryNotes = "Weekly plan",
            isActive = true
        ),
        CustomerEntity(
            id = 9,
            name = "Pooja Hegde",
            phone = "+91 98860 98765",
            address = "House #34, Sector 3",
            area = "HSR Layout",
            dietaryPreference = "VEG",
            deliveryNotes = "Dinner only, deliver by 8:00 PM",
            isActive = true
        ),
        CustomerEntity(
            id = 10,
            name = "Rohan Gupta",
            phone = "+91 95380 44332",
            address = "Flat 501, Palms Residency",
            area = "Indiranagar",
            dietaryPreference = "NON_VEG",
            deliveryNotes = "On vacation till Friday",
            isActive = true
        ),
        CustomerEntity(
            id = 11,
            name = "Sneha Kulkarni",
            phone = "+91 94480 55667",
            address = "7th Cross, 3rd Block",
            area = "Jayanagar",
            dietaryPreference = "VEG",
            deliveryNotes = "Hot meals required, deliver on time",
            isActive = true
        ),
        CustomerEntity(
            id = 12,
            name = "Deepa Shah",
            phone = "+91 93410 77889",
            address = "Flat 12B, Lakeview Apts",
            area = "BTM Layout",
            dietaryPreference = "JAIN",
            deliveryNotes = "Pure Jain food",
            isActive = true
        )
    )

    val sampleMealPlans = listOf(
        MealPlanEntity(
            id = 1,
            name = "Monthly Full Tiffin (Lunch & Dinner)",
            description = "30 Days complete home meal service with 4 Rotis, Rice, Dal, 2 Sabzi, Salad & Sweet",
            mealTime = "BOTH",
            defaultDiet = "VEG",
            price = 5500.0,
            durationDays = 30,
            totalMeals = 60
        ),
        MealPlanEntity(
            id = 2,
            name = "Monthly Lunch Only Plan",
            description = "30 Days wholesome lunch meal box delivered to home/office",
            mealTime = "LUNCH",
            defaultDiet = "VEG",
            price = 3000.0,
            durationDays = 30,
            totalMeals = 30
        ),
        MealPlanEntity(
            id = 3,
            name = "Monthly Dinner Only Plan",
            description = "30 Days fresh light dinner tiffin box",
            mealTime = "DINNER",
            defaultDiet = "VEG",
            price = 2800.0,
            durationDays = 30,
            totalMeals = 30
        ),
        MealPlanEntity(
            id = 4,
            name = "Non-Veg Deluxe Plan (Lunch & Dinner)",
            description = "Complete monthly meal plan with Chicken/Egg curry 3 times a week",
            mealTime = "BOTH",
            defaultDiet = "NON_VEG",
            price = 6800.0,
            durationDays = 30,
            totalMeals = 60
        ),
        MealPlanEntity(
            id = 5,
            name = "Jain Special Monthly Plan",
            description = "Custom cooked Jain meals with no onion, garlic, or root vegetables",
            mealTime = "BOTH",
            defaultDiet = "JAIN",
            price = 5800.0,
            durationDays = 30,
            totalMeals = 60
        ),
        MealPlanEntity(
            id = 6,
            name = "Weekly Trial Pack (7 Days)",
            description = "7 days trial subscription for new customers",
            mealTime = "BOTH",
            defaultDiet = "ANY",
            price = 1400.0,
            durationDays = 7,
            totalMeals = 14
        )
    )

    fun getSampleSubscriptions(): List<SubscriptionEntity> {
        val today = getTodayDate()
        val start1 = getDateOffset(-10)
        val end1 = getDateOffset(20)
        val start2 = getDateOffset(-5)
        val end2 = getDateOffset(25)
        val start3 = getDateOffset(-15)
        val end3 = getDateOffset(15)

        return listOf(
            SubscriptionEntity(
                id = 1,
                customerId = 1,
                planId = 1,
                planName = "Monthly Full Tiffin (Lunch & Dinner)",
                startDate = start1,
                endDate = end1,
                totalMeals = 60,
                consumedMeals = 20,
                skippedMeals = 2,
                price = 5500.0,
                paidAmount = 5500.0,
                mealTime = "BOTH",
                dietaryType = "VEG",
                status = "ACTIVE"
            ),
            SubscriptionEntity(
                id = 2,
                customerId = 2,
                planId = 2,
                planName = "Monthly Lunch Only Plan",
                startDate = start2,
                endDate = end2,
                totalMeals = 30,
                consumedMeals = 5,
                skippedMeals = 0,
                price = 3000.0,
                paidAmount = 3000.0,
                mealTime = "LUNCH",
                dietaryType = "VEG",
                status = "ACTIVE"
            ),
            SubscriptionEntity(
                id = 3,
                customerId = 3,
                planId = 4,
                planName = "Non-Veg Deluxe Plan (Lunch & Dinner)",
                startDate = start1,
                endDate = end1,
                totalMeals = 60,
                consumedMeals = 20,
                skippedMeals = 1,
                price = 6800.0,
                paidAmount = 6800.0,
                mealTime = "BOTH",
                dietaryType = "NON_VEG",
                status = "ACTIVE"
            ),
            SubscriptionEntity(
                id = 4,
                customerId = 4,
                planId = 4,
                planName = "Non-Veg Deluxe Plan (Lunch & Dinner)",
                startDate = start3,
                endDate = end3,
                totalMeals = 60,
                consumedMeals = 30,
                skippedMeals = 3,
                price = 6800.0,
                paidAmount = 3400.0, // Partial payment (pending due)
                mealTime = "BOTH",
                dietaryType = "NON_VEG",
                status = "ACTIVE"
            ),
            SubscriptionEntity(
                id = 5,
                customerId = 5,
                planId = 5,
                planName = "Jain Special Monthly Plan",
                startDate = start1,
                endDate = end1,
                totalMeals = 60,
                consumedMeals = 20,
                skippedMeals = 0,
                price = 5800.0,
                paidAmount = 5800.0,
                mealTime = "BOTH",
                dietaryType = "JAIN",
                status = "ACTIVE"
            ),
            SubscriptionEntity(
                id = 6,
                customerId = 6,
                planId = 2,
                planName = "Monthly Lunch Only Plan",
                startDate = start3,
                endDate = end3,
                totalMeals = 30,
                consumedMeals = 15,
                skippedMeals = 1,
                price = 3000.0,
                paidAmount = 1000.0, // Pending balance 2000
                mealTime = "LUNCH",
                dietaryType = "VEG",
                status = "ACTIVE"
            ),
            SubscriptionEntity(
                id = 7,
                customerId = 7,
                planId = 1,
                planName = "Monthly Full Tiffin (Lunch & Dinner)",
                startDate = start2,
                endDate = end2,
                totalMeals = 60,
                consumedMeals = 10,
                skippedMeals = 0,
                price = 5500.0,
                paidAmount = 5500.0,
                mealTime = "BOTH",
                dietaryType = "VEG",
                status = "ACTIVE"
            ),
            SubscriptionEntity(
                id = 8,
                customerId = 8,
                planId = 6,
                planName = "Weekly Trial Pack (7 Days)",
                startDate = start2,
                endDate = end2,
                totalMeals = 14,
                consumedMeals = 8,
                skippedMeals = 0,
                price = 1400.0,
                paidAmount = 1400.0,
                mealTime = "BOTH",
                dietaryType = "NON_VEG",
                status = "ACTIVE"
            ),
            SubscriptionEntity(
                id = 9,
                customerId = 9,
                planId = 3,
                planName = "Monthly Dinner Only Plan",
                startDate = start1,
                endDate = end1,
                totalMeals = 30,
                consumedMeals = 10,
                skippedMeals = 2,
                price = 2800.0,
                paidAmount = 2800.0,
                mealTime = "DINNER",
                dietaryType = "VEG",
                status = "ACTIVE"
            ),
            SubscriptionEntity(
                id = 10,
                customerId = 10,
                planId = 4,
                planName = "Non-Veg Deluxe Plan (Lunch & Dinner)",
                startDate = start1,
                endDate = end1,
                totalMeals = 60,
                consumedMeals = 18,
                skippedMeals = 4,
                price = 6800.0,
                paidAmount = 6800.0,
                mealTime = "BOTH",
                dietaryType = "NON_VEG",
                status = "PAUSED"
            ),
            SubscriptionEntity(
                id = 11,
                customerId = 11,
                planId = 1,
                planName = "Monthly Full Tiffin (Lunch & Dinner)",
                startDate = start2,
                endDate = end2,
                totalMeals = 60,
                consumedMeals = 10,
                skippedMeals = 1,
                price = 5500.0,
                paidAmount = 5500.0,
                mealTime = "BOTH",
                dietaryType = "VEG",
                status = "ACTIVE"
            ),
            SubscriptionEntity(
                id = 12,
                customerId = 12,
                planId = 5,
                planName = "Jain Special Monthly Plan",
                startDate = start1,
                endDate = end1,
                totalMeals = 60,
                consumedMeals = 20,
                skippedMeals = 0,
                price = 5800.0,
                paidAmount = 2800.0, // Pending 3000
                mealTime = "LUNCH",
                dietaryType = "JAIN",
                status = "ACTIVE"
            )
        )
    }

    fun getSampleTodayDeliveries(): List<DeliveryRecordEntity> {
        val today = getTodayDate()
        return listOf(
            DeliveryRecordEntity(
                id = 1,
                date = today,
                customerId = 1,
                customerName = "Rahul Sharma",
                phone = "+91 98765 43210",
                address = "Flat 302, Sunrise Apartments",
                area = "Indiranagar",
                mealTime = "LUNCH",
                dietaryType = "VEG",
                status = "DELIVERED",
                deliveryNotes = "Leave at security desk"
            ),
            DeliveryRecordEntity(
                id = 2,
                date = today,
                customerId = 2,
                customerName = "Priya Patel",
                phone = "+91 98123 45678",
                address = "No. 45, 2nd Cross, 5th Block",
                area = "Koramangala",
                mealTime = "LUNCH",
                dietaryType = "VEG",
                status = "DELIVERED",
                deliveryNotes = "Ring bell twice"
            ),
            DeliveryRecordEntity(
                id = 3,
                date = today,
                customerId = 3,
                customerName = "Ananya Roy",
                phone = "+91 97654 32109",
                address = "Villa 12, Green Glen Layout",
                area = "HSR Layout",
                mealTime = "LUNCH",
                dietaryType = "NON_VEG",
                status = "DELIVERED",
                deliveryNotes = "Extra rotis"
            ),
            DeliveryRecordEntity(
                id = 4,
                date = today,
                customerId = 4,
                customerName = "Vikram Singh",
                phone = "+91 99887 76655",
                address = "Tower A-804, Tech Park Heights",
                area = "Whitefield",
                mealTime = "LUNCH",
                dietaryType = "NON_VEG",
                status = "PENDING",
                deliveryNotes = "Deliver before 1:00 PM"
            ),
            DeliveryRecordEntity(
                id = 5,
                date = today,
                customerId = 5,
                customerName = "Meera Nambiar",
                phone = "+91 91234 56789",
                address = "Plot 88, 10th Main",
                area = "Indiranagar",
                mealTime = "LUNCH",
                dietaryType = "JAIN",
                status = "DELIVERED",
                deliveryNotes = "Strictly Jain food"
            ),
            DeliveryRecordEntity(
                id = 6,
                date = today,
                customerId = 6,
                customerName = "Suresh Kumar",
                phone = "+91 98450 11223",
                address = "Flat 101, BTM Residency",
                area = "BTM Layout",
                mealTime = "LUNCH",
                dietaryType = "VEG",
                status = "PENDING",
                deliveryNotes = "Call before coming"
            ),
            DeliveryRecordEntity(
                id = 7,
                date = today,
                customerId = 7,
                customerName = "Kavita Reddy",
                phone = "+91 97312 34567",
                address = "No. 12, 4th Main Road",
                area = "Jayanagar",
                mealTime = "LUNCH",
                dietaryType = "VEG",
                status = "DELIVERED",
                deliveryNotes = "Extra salad"
            ),
            DeliveryRecordEntity(
                id = 8,
                date = today,
                customerId = 8,
                customerName = "Amit Verma",
                phone = "+91 96543 21098",
                address = "Flat 204, Opus Enclave",
                area = "Koramangala",
                mealTime = "LUNCH",
                dietaryType = "NON_VEG",
                status = "PENDING",
                deliveryNotes = "Weekly plan"
            ),
            DeliveryRecordEntity(
                id = 9,
                date = today,
                customerId = 10,
                customerName = "Rohan Gupta",
                phone = "+91 95380 44332",
                address = "Flat 501, Palms Residency",
                area = "Indiranagar",
                mealTime = "LUNCH",
                dietaryType = "NON_VEG",
                status = "SKIPPED",
                deliveryNotes = "Out of town - paused"
            ),
            DeliveryRecordEntity(
                id = 10,
                date = today,
                customerId = 11,
                customerName = "Sneha Kulkarni",
                phone = "+91 94480 55667",
                address = "7th Cross, 3rd Block",
                area = "Jayanagar",
                mealTime = "LUNCH",
                dietaryType = "VEG",
                status = "DELIVERED",
                deliveryNotes = "Hot meals required"
            ),
            DeliveryRecordEntity(
                id = 11,
                date = today,
                customerId = 12,
                customerName = "Deepa Shah",
                phone = "+91 93410 77889",
                address = "Flat 12B, Lakeview Apts",
                area = "BTM Layout",
                mealTime = "LUNCH",
                dietaryType = "JAIN",
                status = "DELIVERED",
                deliveryNotes = "Pure Jain food"
            ),
            // Dinner deliveries for today
            DeliveryRecordEntity(
                id = 12,
                date = today,
                customerId = 1,
                customerName = "Rahul Sharma",
                phone = "+91 98765 43210",
                address = "Flat 302, Sunrise Apartments",
                area = "Indiranagar",
                mealTime = "DINNER",
                dietaryType = "VEG",
                status = "PENDING",
                deliveryNotes = "Deliver by 8:00 PM"
            ),
            DeliveryRecordEntity(
                id = 13,
                date = today,
                customerId = 3,
                customerName = "Ananya Roy",
                phone = "+91 97654 32109",
                address = "Villa 12, Green Glen Layout",
                area = "HSR Layout",
                mealTime = "DINNER",
                dietaryType = "NON_VEG",
                status = "PENDING",
                deliveryNotes = "Deliver by 8:30 PM"
            ),
            DeliveryRecordEntity(
                id = 14,
                date = today,
                customerId = 5,
                customerName = "Meera Nambiar",
                phone = "+91 91234 56789",
                address = "Plot 88, 10th Main",
                area = "Indiranagar",
                mealTime = "DINNER",
                dietaryType = "JAIN",
                status = "PENDING",
                deliveryNotes = "Jain food"
            ),
            DeliveryRecordEntity(
                id = 15,
                date = today,
                customerId = 9,
                customerName = "Pooja Hegde",
                phone = "+91 98860 98765",
                address = "House #34, Sector 3",
                area = "HSR Layout",
                mealTime = "DINNER",
                dietaryType = "VEG",
                status = "PENDING",
                deliveryNotes = "Dinner only customer"
            )
        )
    }

    val samplePayments = listOf(
        PaymentRecordEntity(
            id = 1,
            customerId = 1,
            customerName = "Rahul Sharma",
            amount = 5500.0,
            date = getDateOffset(-10),
            paymentMethod = "UPI",
            note = "Monthly subscription full payment via GPay"
        ),
        PaymentRecordEntity(
            id = 2,
            customerId = 2,
            customerName = "Priya Patel",
            amount = 3000.0,
            date = getDateOffset(-5),
            paymentMethod = "UPI",
            note = "Lunch plan paid via Paytm"
        ),
        PaymentRecordEntity(
            id = 3,
            customerId = 3,
            customerName = "Ananya Roy",
            amount = 6800.0,
            date = getDateOffset(-10),
            paymentMethod = "BANK_TRANSFER",
            note = "NEFT transfer received"
        ),
        PaymentRecordEntity(
            id = 4,
            customerId = 4,
            customerName = "Vikram Singh",
            amount = 3400.0,
            date = getDateOffset(-15),
            paymentMethod = "CASH",
            note = "First installment paid in cash. Balance 3400 pending"
        ),
        PaymentRecordEntity(
            id = 5,
            customerId = 6,
            customerName = "Suresh Kumar",
            amount = 1000.0,
            date = getDateOffset(-12),
            paymentMethod = "CASH",
            note = "Advance cash paid. Balance 2000 pending"
        )
    )

    val samplePauses = listOf(
        PauseScheduleEntity(
            id = 1,
            customerId = 10,
            startDate = getTodayDate(),
            endDate = getDateOffset(4),
            mealTime = "BOTH",
            reason = "Travelling to home town"
        )
    )
}
