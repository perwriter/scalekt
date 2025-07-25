package com.example.scale.ui.profile

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.scale.data.database.AppDatabase
import com.example.scale.data.entities.BusinessProfile
import com.example.scale.repository.BusinessProfileRepository
import kotlinx.coroutines.launch

class BusinessProfileViewModel(application: Application) : AndroidViewModel(application) {

    private val db = Room.databaseBuilder(
        application,
        AppDatabase::class.java,
        "scale_database"
    ).build()

    private val repository = BusinessProfileRepository(db.businessProfileDao())

    var profile by mutableStateOf<BusinessProfile?>(null)
        private set

    fun saveProfile(businessProfile: BusinessProfile) {
        viewModelScope.launch {
            repository.saveProfile(businessProfile)
            profile = businessProfile
        }
    }

    fun loadProfile() {
        viewModelScope.launch {
            profile = repository.getProfile()
        }
    }
}
