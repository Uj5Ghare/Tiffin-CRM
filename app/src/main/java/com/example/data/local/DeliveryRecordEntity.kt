package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "delivery_records")
data class DeliveryRecordEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val date: String, // YYYY-MM-DD
    val customerId: Long,
    val customerName: String,
    val phone: String,
    val address: String,
    val area: String,
    val mealTime: String, // LUNCH or DINNER
    val dietaryType: String, // VEG, NON_VEG, JAIN
    val status: String = "PENDING", // PENDING, DELIVERED, SKIPPED
    val deliveryNotes: String = "",
    val updatedAt: Long = System.currentTimeMillis()
)
