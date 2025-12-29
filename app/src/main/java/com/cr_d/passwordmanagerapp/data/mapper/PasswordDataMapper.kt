package com.cr_d.passwordmanagerapp.data.mapper

import com.cr_d.passwordmanagerapp.application.use_cases.DecryptStringUseCase
import com.cr_d.passwordmanagerapp.data.crypto.CryptoService
import com.cr_d.passwordmanagerapp.data.entities.PasswordEntity
import com.cr_d.passwordmanagerapp.domain.value_objects.PasswordData
import com.cr_d.passwordmanagerapp.domain.value_objects.PlainPassword
import com.cr_d.passwordmanagerapp.ui.models.PasswordEditUiState
import com.cr_d.passwordmanagerapp.ui.models.PasswordUiState

val decrypt = DecryptStringUseCase(CryptoService())

fun PasswordData.toEntity(): PasswordEntity = PasswordEntity(
    id = id,
    cipheredPassword = cipheredPassword.encryptedText,
    passwordIv = cipheredPassword.iv,
    appName = appInfo.appName,
    appUrl = appInfo.appUrl,
    account = appInfo.appAccount,
    creationDate = dateInfo.creationDate.toString(),
    lastUpdate = dateInfo.lastUpdate.toString(),
    cipheredNotes = cipheredNotes.encryptedText,
    notesIv = cipheredNotes.iv
)

fun PasswordData.toUiState(): PasswordUiState  {
    return PasswordUiState(
        id = id,
        cipheredPassword = cipheredPassword,
        appInfo = appInfo,
        metadata = metadata,
        dateInfo = dateInfo,
        score = score,
        cipheredNotes = cipheredNotes,
    )
}

fun PasswordData.toEditUiState(passwordLength: Int): PasswordEditUiState = PasswordEditUiState(
    appName = appInfo.appName,
    appUrl = appInfo.appUrl,
    appAccount = appInfo.appAccount,
    hasLowerCase = metadata.hasLowerCase,
    hasUpperCase = metadata.hasUpperCase,
    hasNumbers = metadata.hasNumbers,
    hasSpecials = metadata.hasSpecials,
    passwordLength = passwordLength,
    score = score
)