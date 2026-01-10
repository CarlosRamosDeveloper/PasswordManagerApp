package com.cr_d.passwordmanagerapp.data.dto

import com.cr_d.passwordmanagerapp.ui.model.PasswordUiState

data class AccountDetailInfo (
    val decipheredAccount: String,
    val decipheredNotes: String,
    val passwords: List<PasswordUiState>
)