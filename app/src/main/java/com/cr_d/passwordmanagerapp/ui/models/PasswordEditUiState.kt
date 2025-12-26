package com.cr_d.passwordmanagerapp.ui.models

import com.cr_d.passwordmanagerapp.domain.entities.PasswordPolicy
import com.cr_d.passwordmanagerapp.domain.value_objects.PlainPassword

data class PasswordEditUiState (
    val newAppName: String = "",
    val newUrl: String = "",
    val newAccount: String = "",
    val newLowerCase: Boolean = false,
    val newUpperCase: Boolean = false,
    val newNumbers: Boolean = false,
    val newSpecials: Boolean = false,
    val newPasswordLength: Int = PasswordPolicy.MIN_LENGTH,
    val newPlainPassword: PlainPassword = PlainPassword(""),
    val newSecurityScore: Double = 0.0
)