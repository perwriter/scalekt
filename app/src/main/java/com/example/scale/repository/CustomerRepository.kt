package com.example.scale.repository

import com.example.scale.data.dao.CustomerDao
import com.example.scale.data.entities.Customer
import kotlinx.coroutines.flow.Flow

class CustomerRepository(private val dao: CustomerDao) {

    val customers: Flow<List<Customer>> = dao.getAll()

    suspend fun insert(customer: Customer) {
        dao.insert(customer)
    }

    suspend fun getById(id: Int): Customer? {
        return dao.getById(id)
    }

    suspend fun update(customer: Customer) {
        dao.update(customer)
    }

    suspend fun delete(customer: Customer) {
        dao.delete(customer)
    }

    suspend fun deleteById(id: Int) {
        dao.deleteById(id)
    }

    suspend fun deleteAll() {
        dao.deleteAll()
    }
}
