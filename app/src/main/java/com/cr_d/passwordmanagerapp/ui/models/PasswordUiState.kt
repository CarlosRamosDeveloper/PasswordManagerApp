package com.cr_d.passwordmanagerapp.ui.models

import java.time.LocalDate

import com.cr_d.passwordmanagerapp.domain.value_objects.ApplicationInfo
import com.cr_d.passwordmanagerapp.domain.value_objects.DateInfo
import com.cr_d.passwordmanagerapp.domain.value_objects.PasswordMetadata

data class PasswordUiState(
    val plainPassword: String = "",
    val appInfo: ApplicationInfo = ApplicationInfo(
        appName = "",
        appUrl = "",
        appAccount = ""
    ),
    val metadata: PasswordMetadata = PasswordMetadata(
        hasLowerCase = false,
        hasUpperCase = false,
        hasNumbers = false,
        hasSpecials = false
    ),
    val dateInfo: DateInfo = DateInfo(
        creationDate = LocalDate.now(),
        lastUpdate = LocalDate.now()
    ),
    val score: Double = 0.0,
    val notes: String = ""
)
