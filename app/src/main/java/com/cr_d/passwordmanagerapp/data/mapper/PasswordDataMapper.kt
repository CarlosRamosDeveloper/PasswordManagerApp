package com.cr_d.passwordmanagerapp.data.mapper

import com.cr_d.passwordmanagerapp.application.use_cases.DecryptStringUseCase
import com.cr_d.passwordmanagerapp.data.crypto.CryptoService
import com.cr_d.passwordmanagerapp.data.entities.PasswordEntity
import com.cr_d.passwordmanagerapp.domain.value_objects.PasswordData
import com.cr_d.passwordmanagerapp.ui.models.PasswordUiState

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

fun PasswordData.toUIState(): PasswordUiState  {
    val decrypt = DecryptStringUseCase(CryptoService())

    return PasswordUiState(
            id = id,
    plainPassword = decrypt(cipheredPassword),
    appInfo = appInfo,
    metadata = metadata,
    dateInfo = dateInfo,
    score = score,
    notes = decrypt(cipheredNotes)
    )
}
