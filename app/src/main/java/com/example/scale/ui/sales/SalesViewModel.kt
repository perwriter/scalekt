package com.example.scale.ui.sales

import android.app.Application
import androidx.lifecycle.*
import com.example.scale.data.database.AppDatabase
import com.example.scale.data.entities.Sale
import com.example.scale.data.entities.SaleItem
import com.example.scale.repository.SaleRepository
import kotlinx.coroutines.launch

class SalesViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: SaleRepository
    val sales: LiveData<List<Sale>>

    init {
        val db = AppDatabase.getDatabase(application)
        repository = SaleRepository(db.saleDao(), db.saleItemDao())
        sales = repository.sales.asLiveData()
    }

    // ✅ Insert new sale with its items
    fun insertSaleWithItems(sale: Sale, items: List<SaleItem>) = viewModelScope.launch {
        repository.insertSaleWithItems(sale, items)
    }

    // ✅ Update sale details only
    fun updateSale(sale: Sale) = viewModelScope.launch {
        repository.update(sale)
    }

    // ✅ Update sale and replace its items
    fun updateSaleWithItems(sale: Sale, items: List<SaleItem>) = viewModelScope.launch {
        repository.updateSaleWithItems(sale, items)
    }

    // ✅ Delete a sale
    fun deleteSale(sale: Sale) = viewModelScope.launch {
        repository.delete(sale)
    }

    // ✅ Delete sale by ID
    fun deleteSaleById(saleId: Int) = viewModelScope.launch {
        repository.deleteById(saleId)
    }

    // ✅ Delete all sales
    fun deleteAllSales() = viewModelScope.launch {
        repository.deleteAll()
    }

    // ✅ Get items for a specific sale
    fun getSaleItems(saleId: Int): LiveData<List<SaleItem>> = liveData {
        emit(repository.getSaleItems(saleId))
    }

    // ✅ Update individual sale item
    fun updateSaleItem(saleItem: SaleItem) = viewModelScope.launch {
        repository.updateSaleItem(saleItem)
    }

    // ✅ Delete individual sale item
    fun deleteSaleItem(saleItem: SaleItem) = viewModelScope.launch {
        repository.deleteSaleItem(saleItem)
    }

    // ✅ Delete all items for a specific sale
    fun deleteItemsForSale(saleId: Int) = viewModelScope.launch {
        repository.deleteItemsForSale(saleId)
    }
}
