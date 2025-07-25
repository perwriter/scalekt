package com.example.scale.repository

import com.example.scale.data.dao.SaleDao
import com.example.scale.data.dao.SaleItemDao
import com.example.scale.data.entities.Sale
import com.example.scale.data.entities.SaleItem
import kotlinx.coroutines.flow.Flow

class SaleRepository(
    private val saleDao: SaleDao,
    private val saleItemDao: SaleItemDao
) {

    val sales: Flow<List<Sale>> = saleDao.getAll()

    suspend fun insertSaleWithItems(sale: Sale, items: List<SaleItem>) {
        val saleId = saleDao.insert(sale)
        items.forEach { item ->
            saleItemDao.insert(item.copy(saleId = saleId.toInt()))
        }
    }

    suspend fun update(sale: Sale) = saleDao.update(sale)

    suspend fun updateSaleWithItems(sale: Sale, items: List<SaleItem>) {
        saleDao.update(sale)
        saleItemDao.deleteItemsForSale(sale.id) // ✅ clear old items
        items.forEach { item ->
            saleItemDao.insert(item.copy(saleId = sale.id))
        }
    }

    suspend fun delete(sale: Sale) = saleDao.delete(sale)

    suspend fun deleteById(id: Int) = saleDao.deleteById(id)

    suspend fun deleteAll() = saleDao.deleteAll()

    suspend fun getSaleItems(saleId: Int): List<SaleItem> = saleItemDao.getItemsForSale(saleId)

    suspend fun updateSaleItem(saleItem: SaleItem) = saleItemDao.update(saleItem)

    suspend fun deleteSaleItem(saleItem: SaleItem) = saleItemDao.delete(saleItem)

    // ✅ ADD THIS FUNCTION
    suspend fun deleteItemsForSale(saleId: Int) {
        saleItemDao.deleteItemsForSale(saleId)
    }
}
