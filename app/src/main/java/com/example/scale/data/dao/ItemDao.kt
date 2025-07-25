package com.example.scale.data.dao

import androidx.room.*
import com.example.scale.data.entities.Item
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: Item)

    @Query("SELECT * FROM items")
    fun getAll(): Flow<List<Item>>

    @Query("SELECT * FROM items WHERE id = :itemId")
    suspend fun getById(itemId: Int): Item?

    @Update
    suspend fun update(item: Item)

    @Delete
    suspend fun delete(item: Item)

    @Query("DELETE FROM items WHERE id = :itemId")
    suspend fun deleteById(itemId: Int)

    @Query("DELETE FROM items")
    suspend fun deleteAll()
}
