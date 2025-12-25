package com.cr_d.passwordmanagerapp.ui.screens.password_detail.components

import androidx.compose.runtime.Composable

import com.cr_d.passwordmanagerapp.domain.value_objects.PasswordData
import com.cr_d.passwordmanagerapp.ui.common_components.ApplicationTitle
import com.cr_d.passwordmanagerapp.ui.common_components.CustomRow
import com.cr_d.passwordmanagerapp.ui.common_components.InfoCard
import com.cr_d.passwordmanagerapp.ui.common_components.SectionTitle
import com.cr_d.passwordmanagerapp.ui.models.formatAs
import com.cr_d.passwordmanagerapp.ui.screens.settings.SettingsViewModel

@Composable
fun BasicMode(
    password: PasswordData,
    settings: SettingsViewModel.AppSettings
){
    InfoCard {
        ApplicationTitle(password.appInfo.applicationName)
        SectionTitle("Información resumida")
        CustomRow("Nombre de usuario", password.appInfo.account)
        CustomRow("Sitio Web", password.appInfo.url)
        CustomRow("Última Modificación", password.metadata.lastUpdate.formatAs(settings.dateFormat))
        CustomRow("Puntuación de seguridad", String.format("%.2f", password.securityScore))
    }
}