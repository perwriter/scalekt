package com.example.scale.data.dao

import androidx.room.*
import com.example.scale.data.entities.Sale
import kotlinx.coroutines.flow.Flow

@Dao
interface SaleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(sale: Sale): Long // returns saleId for inserted sale

    @Query("SELECT * FROM sales")
    fun getAll(): Flow<List<Sale>>

    @Query("SELECT * FROM sales WHERE id = :saleId")
    suspend fun getById(saleId: Int): Sale?

    @Update
    suspend fun update(sale: Sale)

    @Delete
    suspend fun delete(sale: Sale)

    // ✅ DELETE BY ID
    @Query("DELETE FROM sales WHERE id = :saleId")
    suspend fun deleteById(saleId: Int)

    // ✅ DELETE ALL SALES
    @Query("DELETE FROM sales")
    suspend fun deleteAll()
}
