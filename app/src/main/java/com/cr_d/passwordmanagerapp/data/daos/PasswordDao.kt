package com.cr_d.passwordmanagerapp.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.cr_d.passwordmanagerapp.data.entities.ApplicationEntity

import com.cr_d.passwordmanagerapp.data.entities.PasswordEntity

@Dao
interface PasswordDao {
    @Query("SELECT * FROM passwords")
    suspend fun getAll(): List<PasswordEntity>

    @Query("SELECT * FROM passwords where id in (:userId)")
    suspend fun getPasswordById(userId: Long): PasswordEntity?

    @Query("SELECT * FROM passwords WHERE app_id = :appId AND account_id = :accountId LIMIT 1")
    suspend fun findByAppIdAndAccountId(appId: Long, accountId: Long): PasswordEntity?

    @Query("SELECT * FROM passwords WHERE account_id = :accId")
    suspend fun findByAccountId(accId: Long): List<PasswordEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPassword(password: PasswordEntity): Long

    @Update
    suspend fun updatePassword(password: PasswordEntity): Int

    @Query("DELETE FROM passwords WHERE id = :passwordId")
    suspend fun deletePassword(passwordId: Long): Int

    @Query("DELETE FROM passwords")
    suspend fun deleteAll()
}