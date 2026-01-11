package com.cr_d.passwordmanagerapp.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

import com.cr_d.passwordmanagerapp.data.entities.AccountEntity

@Dao
interface AccountDao {
    @Query("SELECT * FROM accounts")
    suspend fun getAll(): List<AccountEntity>

    @Query("SELECT * FROM accounts where id in (:accountId)")
    suspend fun getAccountById(accountId: Long): AccountEntity?

    //TODO: Revisar
    @Query("SELECT * FROM accounts where ciphered_account in (:accountName)")
    suspend fun getAccountByName(accountName: String): AccountEntity?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAccount(account: AccountEntity): Long

    @Update
    suspend fun updateAccount(account: AccountEntity): Int

    @Query("DELETE FROM accounts WHERE id= :accountId")
    suspend fun deleteAccount(accountId: Long): Int

    @Query("DELETE FROM accounts")
    suspend fun deleteAll()
}
