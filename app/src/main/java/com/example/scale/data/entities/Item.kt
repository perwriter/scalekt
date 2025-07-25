package com.example.scale.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items")
data class Item(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val category: String,
    val location: String,
    val price: Double,
    val stock: Int,
    val status: String,
    val sku: String
)
