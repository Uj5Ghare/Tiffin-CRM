package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "meal_plans")
data class MealPlanEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val description: String = "",
    val mealTime: String = "BOTH", // LUNCH, DINNER, BOTH
    val defaultDiet: String = "VEG", // VEG, NON_VEG, ANY
    val price: Double,
    val durationDays: Int = 30,
    val totalMeals: Int = 30,
    val isActive: Boolean = true
)
