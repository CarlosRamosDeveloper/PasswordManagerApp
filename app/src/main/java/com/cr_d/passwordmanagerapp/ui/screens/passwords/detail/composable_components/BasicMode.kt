package com.cr_d.passwordmanagerapp.ui.screens.passwords.detail.composable_components

import androidx.compose.runtime.Composable

import com.cr_d.passwordmanagerapp.ui.common_components.ApplicationTitle
import com.cr_d.passwordmanagerapp.ui.common_components.CustomRow
import com.cr_d.passwordmanagerapp.ui.common_components.InfoCard
import com.cr_d.passwordmanagerapp.ui.common_components.SectionTitle
import com.cr_d.passwordmanagerapp.ui.model.PasswordUiState
import com.cr_d.passwordmanagerapp.ui.model.formatAs
import com.cr_d.passwordmanagerapp.ui.screens.settings.SettingsViewModel

@Composable
fun BasicMode(
    password: PasswordUiState,
    settings: SettingsViewModel.AppSettings
){
    InfoCard {
        ApplicationTitle(password.appInfo.appName)
        SectionTitle("Información resumida")
        CustomRow("Nombre de usuario", password.appInfo.appAccount)
        CustomRow("Sitio Web", password.appInfo.appUrl)
        CustomRow("Última Modificación", password.dateInfo.lastUpdate.formatAs(settings.dateFormat))
        CustomRow("Puntuación de seguridad", String.format("%.2f", password.score))
    }
}