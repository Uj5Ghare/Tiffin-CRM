package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "subscriptions")
data class SubscriptionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val customerId: Long,
    val planId: Long,
    val planName: String,
    val startDate: String, // YYYY-MM-DD
    val endDate: String,   // YYYY-MM-DD
    val totalMeals: Int,
    val consumedMeals: Int = 0,
    val skippedMeals: Int = 0,
    val price: Double,
    val paidAmount: Double = 0.0,
    val mealTime: String = "BOTH", // LUNCH, DINNER, BOTH
    val dietaryType: String = "VEG", // VEG, NON_VEG, JAIN
    val status: String = "ACTIVE" // ACTIVE, PAUSED, COMPLETED, CANCELLED
)
