package com.cr_d.passwordmanagerapp.data.dto

import com.cr_d.passwordmanagerapp.domain.value_objects.EncryptedPayload
import com.cr_d.passwordmanagerapp.ui.model.PasswordUiState

data class AccountDetail(
    val id: Long,
    val cipheredAccount : EncryptedPayload,
    val cipheredNotes: EncryptedPayload,
    val passwords: List<PasswordUiState>
)