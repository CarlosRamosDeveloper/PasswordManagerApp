package com.cr_d.passwordmanagerapp.data.dto

import com.cr_d.passwordmanagerapp.ui.model.PasswordUiState

data class ApplicationDetailInfo (
    val decipheredNotes: String,
    val passwords: List<PasswordUiState>
)