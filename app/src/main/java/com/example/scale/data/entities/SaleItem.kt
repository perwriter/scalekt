package com.example.scale.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sale_items")
data class SaleItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val saleId: Int,
    val itemId: Int,
    val quantity: Int,
    val price: Double // total for this item = unit price * quantity
)
