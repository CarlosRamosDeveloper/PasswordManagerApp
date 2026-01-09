package com.cr_d.passwordmanagerapp.ui.model

import com.cr_d.passwordmanagerapp.domain.value_objects.EncryptedPayload

data class AccountUiState (
    val id: Long = 0L,
    val account : String = "",
    val cipheredNotes : EncryptedPayload = AppConfig.emptyEncryptedPayload,
    val totalApplications: Int
)