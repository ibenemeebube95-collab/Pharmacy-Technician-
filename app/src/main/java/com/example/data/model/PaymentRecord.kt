package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "payment_records")
data class PaymentRecord(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val userEmail: String,
    val accountName: String,
    val bankName: String = "Sterling Bank",
    val amountPaidNgn: Int = 3500,
    val referenceNumber: String,
    val transferDate: String,
    val status: String = "PENDING",    // "PENDING", "VERIFIED", "REJECTED"
    val submittedTimestamp: Long = System.currentTimeMillis()
)
