package com.cr_d.passwordmanagerapp.ui.model

import com.cr_d.passwordmanagerapp.domain.value_objects.EncryptedPayload
import com.cr_d.passwordmanagerapp.domain.policy.PasswordPolicy

data class PasswordEditUiState (
    val appName: String = "",
    val appUrl: String = "",
    val appAccount: String = "",
    val hasLowerCase: Boolean = false,
    val hasUpperCase: Boolean = false,
    val hasNumbers: Boolean = false,
    val hasSpecials: Boolean = false,
    val passwordLength: Int = PasswordPolicy.MIN_LENGTH,
    val cipheredNotes: EncryptedPayload = AppConfig.emptyEncryptedPayload,
    val cipheredPassword : EncryptedPayload = AppConfig.emptyEncryptedPayload,
    val score: Double = 0.0
)