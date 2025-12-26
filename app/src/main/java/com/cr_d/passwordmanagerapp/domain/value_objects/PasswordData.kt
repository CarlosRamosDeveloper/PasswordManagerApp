package com.cr_d.passwordmanagerapp.domain.value_objects

import com.cr_d.passwordmanagerapp.data.PasswordEntity
import com.cr_d.passwordmanagerapp.ui.models.PasswordEditUiState
import com.cr_d.passwordmanagerapp.ui.models.PasswordUiState

data class PasswordData (
    val id : Int,
    val plainPassword : PlainPassword,
    val appInfo: ApplicationInfo,
    val metadata: PasswordMetadata,
    val dateInfo: DateInfo,
    val score : Double,
    val notes: String = ""
)

fun PasswordData.toEntity(): PasswordEntity = PasswordEntity(
    id = id,
    plainPassword = plainPassword.value,
    appName = appInfo.appName,
    appUrl = appInfo.appUrl,
    account = appInfo.appAccount,
    creationDate = dateInfo.creationDate,
    lastUpdate = dateInfo.lastUpdate,
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