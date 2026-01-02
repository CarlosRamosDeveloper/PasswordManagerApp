package com.cr_d.passwordmanagerapp.data.daos

import androidx.room.Dao
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
    suspend fun getPasswordById(userId: Long): PasswordEntity?

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertPassword(password: PasswordEntity): Long

    @Update
    suspend fun updatePassword(password: PasswordEntity): Int

    @Query("DELETE FROM PasswordEntity WHERE id = :passwordId")
    suspend fun deletePassword(passwordId: Long): Int

    @Query("DELETE FROM PasswordEntity")
    suspend fun deleteAll()
}