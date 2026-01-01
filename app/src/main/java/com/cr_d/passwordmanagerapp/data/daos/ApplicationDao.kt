package com.cr_d.passwordmanagerapp.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

import com.cr_d.passwordmanagerapp.data.entities.ApplicationEntity

@Dao
interface ApplicationDao {
    @Query("SELECT * FROM ApplicationEntity")
    suspend fun getAll(): List<ApplicationEntity>

    @Query("SELECT * FROM ApplicationEntity where id in (:appId)")
    suspend fun getApplicationById(appId: Long): ApplicationEntity?

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertApplication(app: ApplicationEntity): Long

    @Update
    suspend fun updateApplication(app: ApplicationEntity): Int

    @Query("DELETE FROM ApplicationEntity WHERE id = :appId")
    suspend fun deletePassword(appId: Long): Int

    @Query("DELETE FROM ApplicationEntity")
    suspend fun deleteAll()
}
