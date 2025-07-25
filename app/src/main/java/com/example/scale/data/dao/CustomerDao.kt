package com.example.scale.data.dao

import androidx.room.*
import com.example.scale.data.entities.Customer
import kotlinx.coroutines.flow.Flow

@Dao
interface CustomerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(customer: Customer)

    @Query("SELECT * FROM customers")
    fun getAll(): Flow<List<Customer>>

    @Query("SELECT * FROM customers WHERE id = :customerId")
    suspend fun getById(customerId: Int): Customer?

    @Update
    suspend fun update(customer: Customer)

    @Delete
    suspend fun delete(customer: Customer)

    @Query("DELETE FROM customers WHERE id = :customerId")
    suspend fun deleteById(customerId: Int)

    @Query("DELETE FROM customers")
    suspend fun deleteAll()
}
