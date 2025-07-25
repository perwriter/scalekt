package com.example.scale.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.scale.data.dao.*
import com.example.scale.data.entities.*

@Database(
    entities = [
        Customer::class,
        Item::class,
        Sale::class,
        SaleItem::class,
        BusinessProfile::class
    ],
    version = 2, // increment version since you added a new entity
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun customerDao(): CustomerDao
    abstract fun itemDao(): ItemDao
    abstract fun saleDao(): SaleDao
    abstract fun saleItemDao(): SaleItemDao
    abstract fun businessProfileDao(): BusinessProfileDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "scale_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
