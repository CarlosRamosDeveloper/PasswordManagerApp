package com.cr_d.passwordmanagerapp.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

import com.cr_d.passwordmanagerapp.data.entities.AccountEntity

@Dao
interface AccountDao {
    @Query("SELECT * FROM AccountEntity")
    suspend fun getAll(): List<AccountEntity>

    @Query("SELECT * FROM AccountEntity where id in (:accountId)")
    suspend fun getAccountById(accountId: Long): AccountEntity?

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertAccount(account: AccountEntity): Long

    @Update
    suspend fun updateAccount(account: AccountEntity): Int

    @Query("DELETE FROM AccountEntity WHERE id= :accountId")
    suspend fun deleteAccount(accountId: Long): Int

    @Query("DELETE FROM AccountEntity")
    suspend fun deleteAll()
}
