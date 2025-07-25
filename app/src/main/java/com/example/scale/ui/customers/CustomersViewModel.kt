package com.example.scale.ui.customers

import android.app.Application
import androidx.lifecycle.*
import com.example.scale.data.database.AppDatabase
import com.example.scale.data.entities.Customer
import com.example.scale.repository.CustomerRepository
import kotlinx.coroutines.launch

class CustomersViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: CustomerRepository
    val customers: LiveData<List<Customer>>

    init {
        val dao = AppDatabase.getDatabase(application).customerDao()
        repository = CustomerRepository(dao)
        customers = repository.customers.asLiveData()
    }

    fun insert(customer: Customer) = viewModelScope.launch {
        repository.insert(customer)
    }

    fun update(customer: Customer) = viewModelScope.launch {
        repository.update(customer)
    }

    fun delete(customer: Customer) = viewModelScope.launch {
        repository.delete(customer)
    }

    fun deleteById(id: Int) = viewModelScope.launch {
        repository.deleteById(id)
    }

    fun deleteAll() = viewModelScope.launch {
        repository.deleteAll()
    }

    suspend fun getById(id: Int): Customer? {
        return repository.getById(id)
    }
}
