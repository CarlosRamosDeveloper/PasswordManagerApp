package com.cr_d.passwordmanagerapp.ui.model

data class ApplicationUiState (
    val id: Long = 0,
    val applicationName: String = "",
    val applicationUrl: String = "",
    val notes: String = "",
    val passwords: List<PasswordUiState> = emptyList()
)