package com.cr_d.passwordmanagerapp.ui.model

class ApplicationUiState (
    val id: Long = 0,
    val applicationName: String = "",
    val applicationUrl: String = "",
    val notes: String = "",
    val passwords: List<PasswordUiState> = emptyList()
    // TODO: Agregar un campo para contar cuantas contraseñas hay asociadas a cada cuenta
)