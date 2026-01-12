package com.cr_d.passwordmanagerapp.ui.model

data class AccountUiState (
    val id: Long = 0L,
    val account : String = "",
    val notes : String = "",
    val passwords: List<PasswordUiState> = emptyList()
)