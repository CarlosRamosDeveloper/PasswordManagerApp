package com.cr_d.passwordmanagerapp.data.mapper

import com.cr_d.passwordmanagerapp.ui.models.moved.ApplicationInfo
import com.cr_d.passwordmanagerapp.domain.entities.Password
import com.cr_d.passwordmanagerapp.data.dto.PasswordDetail
import com.cr_d.passwordmanagerapp.ui.models.PasswordEditUiState
import com.cr_d.passwordmanagerapp.ui.models.PasswordUiState

fun PasswordDetail.toDomain(): Password = Password(
    id = id,
    cipheredPassword = cipheredPassword,
    appId = 1L,
    accountId = 1L,
    dateInfo = dateInfo,
    cipheredNotes = cipheredNotes,
)

fun PasswordDetail.toUiState(): PasswordUiState  {
    return PasswordUiState(
        id = id,
        cipheredPassword = cipheredPassword,
        appInfo = ApplicationInfo(
            appName = appData.appName,
            appUrl = appData.appUrl,
            appAccount = accountData.account
        ),
        metadata = metadata,
        dateInfo = dateInfo,
        score = score,
        cipheredNotes = cipheredNotes,
    )
}

fun PasswordDetail.toEditUiState(passwordLength: Int): PasswordEditUiState  {
    return PasswordEditUiState(
        appName = appData.appName,
        appUrl = appData.appUrl,
        appAccount = accountData.account,
        hasLowerCase = metadata.hasLowerCase,
        hasUpperCase = metadata.hasUpperCase,
        hasNumbers = metadata.hasNumbers,
        hasSpecials = metadata.hasSpecials,
        passwordLength = passwordLength,
        score = score
    )
}
