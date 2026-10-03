package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "payment_records")
data class PaymentRecordEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val customerId: Long,
    val customerName: String,
    val amount: Double,
    val date: String, // YYYY-MM-DD
    val paymentMethod: String = "UPI", // UPI, CASH, BANK_TRANSFER
    val note: String = "",
    val timestamp: Long = System.currentTimeMillis()
)
