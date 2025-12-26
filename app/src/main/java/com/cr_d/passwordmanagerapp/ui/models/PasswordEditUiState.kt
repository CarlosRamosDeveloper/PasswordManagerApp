package com.cr_d.passwordmanagerapp.ui.models

import com.cr_d.passwordmanagerapp.domain.entities.PasswordPolicy
import com.cr_d.passwordmanagerapp.domain.value_objects.PlainPassword

data class PasswordEditUiState (
    val appName: String = "",
    val appUrl: String = "",
    val appAccount: String = "",
    val hasLowerCase: Boolean = false,
    val hasUpperCase: Boolean = false,
    val hasNumbers: Boolean = false,
    val hasSpecials: Boolean = false,
    val passwordLength: Int = PasswordPolicy.MIN_LENGTH,
    val plainPassword: PlainPassword = PlainPassword(""),
    val score: Double = 0.0
)