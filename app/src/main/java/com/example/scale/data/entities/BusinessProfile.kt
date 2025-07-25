package com.example.scale.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "business_profile")
data class BusinessProfile(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val businessName: String,
    val logoPath: String?,
    val physicalAddress: String,
    val phoneNumber: String,
    val emailOrWebsite: String,
    val registrationNumber: String,
    val kraPin: String,
    val receiptNumber: String,
    val dateTime: String,
    val cashierNameOrId: String,
    val paymentMethod: String,
    val thankYouMessage: String
)
