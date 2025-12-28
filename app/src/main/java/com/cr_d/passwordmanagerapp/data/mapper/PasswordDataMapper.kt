package com.cr_d.passwordmanagerapp.data.mapper

import com.cr_d.passwordmanagerapp.data.entities.PasswordEntity
import com.cr_d.passwordmanagerapp.domain.value_objects.PasswordData
import com.cr_d.passwordmanagerapp.ui.models.PasswordEditUiState
import com.cr_d.passwordmanagerapp.ui.models.PasswordUiState

fun PasswordData.toEntity(): PasswordEntity = PasswordEntity(
    id = id,
    plainPassword = plainPassword.value,
    appName = appInfo.appName,
    appUrl = appInfo.appUrl,
    account = appInfo.appAccount,
    creationDate = dateInfo.creationDate.toString(),
    lastUpdate = dateInfo.lastUpdate.toString(),
    notes = notes
)

fun PasswordData.toUIState(): PasswordUiState = PasswordUiState(
    plainPassword = plainPassword.value,
    appInfo = appInfo,
    metadata = metadata,
    dateInfo = dateInfo,
    score = score,
    notes = notes
)

fun PasswordData.toEditUiState(passwordLength: Int): PasswordEditUiState = PasswordEditUiState(
    plainPassword = plainPassword,
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