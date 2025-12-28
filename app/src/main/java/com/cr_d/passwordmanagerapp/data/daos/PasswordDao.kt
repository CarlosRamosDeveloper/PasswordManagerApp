package com.cr_d.passwordmanagerapp.data.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

import com.cr_d.passwordmanagerapp.data.entities.PasswordEntity

@Dao
interface PasswordDao {

    @Query("SELECT * FROM PasswordEntity")
    suspend fun getAll(): List<PasswordEntity>

    @Query("SELECT * FROM PasswordEntity where id in (:userId)")
    suspend fun getUserById(userId: Int): PasswordEntity

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertPassword(password: PasswordEntity): Long

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertPasswordsAsList(passwords: List<PasswordEntity>): List<Long>

    @Update
    suspend fun updatePassword(password: PasswordEntity): Int

    @Delete
    suspend fun deletePassword(password: PasswordEntity): Int
}