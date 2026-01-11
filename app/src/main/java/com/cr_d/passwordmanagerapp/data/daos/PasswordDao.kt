package com.cr_d.passwordmanagerapp.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.cr_d.passwordmanagerapp.data.entities.AccountEntity
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

    @Transaction
    suspend fun insertPasswordWithRelations(
        accountHash: String,
        accountCipher: ByteArray,
        accountIv: ByteArray,
        accountNotes: ByteArray,
        accountNotesIv: ByteArray,

        appName: String,
        appUrl: String?,
        appNotes: ByteArray,
        appNotesIv: ByteArray,

        passwordCipher: ByteArray,
        passwordIv: ByteArray,
        notesCipher: ByteArray,
        notesIv: ByteArray,
        creationDate: String,
        lastUpdate: String
    ) {
        val accountId = getAccountIdByHash(accountHash)
            ?: insertAccount(
                AccountEntity(
                    cipheredAccount = accountCipher,
                    accountIv = accountIv,
                    accountHash = accountHash,
                    cipheredNotes = accountNotes,
                    notesIv = accountNotesIv
                )
            )

        // 2. Application
        val appId = getApplicationIdByName(appName)
            ?: insertApplication(
                ApplicationEntity(
                    appName = appName,
                    appUrl = appUrl,
                    cipheredNotes = appNotes,
                    notesIv = appNotesIv
                )
            )

        // 3. Password
        insertPassword(
            PasswordEntity(
                cipheredPassword = passwordCipher,
                passwordIv = passwordIv,
                appId = appId,
                accountId = accountId,
                creationDate = creationDate,
                lastUpdate = lastUpdate,
                cipheredNotes = notesCipher,
                notesIv = notesIv
            )
        )
    }

    @Query("SELECT id FROM accounts WHERE account_hash = :hash LIMIT 1")
    suspend fun getAccountIdByHash(hash: String): Long?

    @Query("SELECT id FROM applications WHERE app_name = :name LIMIT 1")
    suspend fun getApplicationIdByName(name: String): Long?

    @Insert
    suspend fun insertAccount(entity: AccountEntity): Long

    @Insert
    suspend fun insertApplication(entity: ApplicationEntity): Long

}