package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pause_schedules")
data class PauseScheduleEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val customerId: Long,
    val startDate: String, // YYYY-MM-DD
    val endDate: String,   // YYYY-MM-DD
    val mealTime: String = "BOTH", // LUNCH, DINNER, BOTH
    val reason: String = "Vacation"
)
