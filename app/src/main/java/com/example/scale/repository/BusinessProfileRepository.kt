package com.example.scale.repository

import com.example.scale.data.dao.BusinessProfileDao
import com.example.scale.data.entities.BusinessProfile

class BusinessProfileRepository(private val dao: BusinessProfileDao) {

    suspend fun saveProfile(profile: BusinessProfile) {
        dao.insertProfile(profile)
    }

    suspend fun getProfile(): BusinessProfile? {
        return dao.getProfile()
    }
}
