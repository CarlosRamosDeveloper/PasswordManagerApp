package com.cr_d.passwordmanagerapp.ui.screens.password_detail.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.cr_d.passwordmanagerapp.domain.value_objects.PasswordData
import com.cr_d.passwordmanagerapp.ui.models.formatAs
import com.cr_d.passwordmanagerapp.ui.screens.password_detail.horizontalFramePadding
import com.cr_d.passwordmanagerapp.ui.screens.settings.SettingsViewModel

@Composable
fun BasicMode(
    password: PasswordData,
    settings: SettingsViewModel.AppSettings
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = horizontalFramePadding, vertical = 10.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ApplicationTitle(password.appInfo.applicationName)
            SectionTitle("Información resumida")
            CustomRow("Nombre de usuario", password.appInfo.account)
            CustomRow("Sitio Web", password.appInfo.url)
            CustomRow("Última Modificación", password.metadata.lastUpdate.formatAs(settings.dateFormat))
            CustomRow("Puntuación de seguridad", String.format("%.2f", password.securityScore))
        }
    }
}