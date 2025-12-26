package com.cr_d.passwordmanagerapp.ui.models

import com.cr_d.passwordmanagerapp.domain.value_objects.ApplicationInfo
import com.cr_d.passwordmanagerapp.domain.value_objects.DateInfo
import com.cr_d.passwordmanagerapp.domain.value_objects.PasswordMetadata
import java.time.LocalDate

data class PasswordUiState(
    val password: String = "",
    val appInfo: ApplicationInfo = ApplicationInfo(
        applicationName = "",
        url = "",
        account = ""
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
