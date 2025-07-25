package com.example.scale.ui.items

import android.app.Application
import androidx.lifecycle.*
import com.example.scale.data.database.AppDatabase
import com.example.scale.data.entities.Item
import com.example.scale.repository.ItemRepository
import kotlinx.coroutines.launch

class ItemsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ItemRepository
    val items: LiveData<List<Item>>

    init {
        val dao = AppDatabase.getDatabase(application).itemDao()
        repository = ItemRepository(dao)
        items = repository.items.asLiveData()
    }

    fun insert(item: Item) = viewModelScope.launch {
        repository.insert(item)
    }

    fun update(item: Item) = viewModelScope.launch {
        repository.update(item)
    }

    fun delete(item: Item) = viewModelScope.launch {
        repository.delete(item)
    }

    fun deleteById(id: Int) = viewModelScope.launch {
        repository.deleteById(id)
    }

    fun deleteAll() = viewModelScope.launch {
        repository.deleteAll()
    }

    suspend fun getById(id: Int): Item? {
        return repository.getById(id)
    }
}
