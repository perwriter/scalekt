package com.example.scale.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.scale.data.entities.BusinessProfile

@Dao
interface BusinessProfileDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProfile(profile: BusinessProfile)

    @Query("SELECT * FROM business_profile LIMIT 1")
    suspend fun getProfile(): BusinessProfile?
}
