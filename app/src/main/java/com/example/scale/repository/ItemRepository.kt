package com.example.scale.repository

import com.example.scale.data.dao.ItemDao
import com.example.scale.data.entities.Item
import kotlinx.coroutines.flow.Flow

class ItemRepository(private val dao: ItemDao) {

    val items: Flow<List<Item>> = dao.getAll()

    suspend fun insert(item: Item) = dao.insert(item)

    suspend fun getById(id: Int): Item? = dao.getById(id)

    suspend fun update(item: Item) = dao.update(item)

    suspend fun delete(item: Item) = dao.delete(item)

    suspend fun deleteById(id: Int) = dao.deleteById(id)

    suspend fun deleteAll() = dao.deleteAll()
}
