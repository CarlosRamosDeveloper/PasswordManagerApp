package com.cr_d.passwordmanagerapp.domain.use_cases.password_use_cases

import com.cr_d.passwordmanagerapp.data.daos.PasswordDao
import com.cr_d.passwordmanagerapp.domain.services.HashService
import com.cr_d.passwordmanagerapp.domain.use_cases.security_use_cases.EncryptStringUseCase
import com.cr_d.passwordmanagerapp.ui.model.PasswordUiState

class MassSavePasswordUseCase(
    private val hash: HashService,
    private val encrypt: EncryptStringUseCase,
    private val passwordDao: PasswordDao
) {
    suspend operator fun invoke(passwords: List<PasswordUiState>) {
        passwords.forEach { pwd ->
            val normalizedAccount = pwd.appInfo.appAccount.trim().lowercase()
            val accountHash = hash.convertToSha256(normalizedAccount)

            val encryptedAccount = encrypt(pwd.appInfo.appAccount)
            val encryptedBlank = encrypt("")
            val encryptedPasswordNotes = encrypt(pwd.notes)

            passwordDao.insertPasswordWithRelations(
                accountHash = accountHash,
                accountCipher = encryptedAccount.encryptedText,
                accountIv =encryptedAccount.iv,
                accountNotes = encryptedBlank.encryptedText,
                accountNotesIv = encryptedBlank.iv,

                appName = pwd.appInfo.appName,
                appUrl = pwd.appInfo.appUrl,
                appNotes = encryptedBlank.encryptedText,
                appNotesIv = encryptedBlank.iv,

                passwordCipher = pwd.cipheredPassword.encryptedText,
                passwordIv = pwd.cipheredPassword.iv,
                notesCipher = encryptedPasswordNotes.encryptedText,
                notesIv = encryptedPasswordNotes.iv,
                creationDate = pwd.dateInfo.creationDate.toString(),
                lastUpdate = pwd.dateInfo.lastUpdate.toString()
            )
        }
    }
}