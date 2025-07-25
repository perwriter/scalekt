package com.example.scale.data.dao

import androidx.room.*

import com.example.scale.data.entities.SaleItem

@Dao
interface SaleItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(saleItem: SaleItem)

    @Query("SELECT * FROM sale_items WHERE saleId = :saleId")
    suspend fun getItemsForSale(saleId: Int): List<SaleItem>

    @Update
    suspend fun update(saleItem: SaleItem)

    @Delete
    suspend fun delete(saleItem: SaleItem)

    // ✅ Delete all items for a given sale
    @Query("DELETE FROM sale_items WHERE saleId = :saleId")
    suspend fun deleteItemsForSale(saleId: Int)


}
