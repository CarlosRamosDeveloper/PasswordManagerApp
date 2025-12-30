package com.cr_d.passwordmanagerapp.ui.screens.password_detail.components

import android.util.Log
import androidx.compose.runtime.Composable

import com.cr_d.passwordmanagerapp.ui.common_components.ApplicationTitle
import com.cr_d.passwordmanagerapp.ui.common_components.CustomRow
import com.cr_d.passwordmanagerapp.ui.common_components.InfoCard
import com.cr_d.passwordmanagerapp.ui.common_components.SectionTitle
import com.cr_d.passwordmanagerapp.ui.models.PasswordUiState
import com.cr_d.passwordmanagerapp.ui.models.formatAs
import com.cr_d.passwordmanagerapp.ui.screens.settings.SettingsViewModel

@Composable
fun BasicMode(
    password: PasswordUiState,
    settings: SettingsViewModel.AppSettings,
    notes: String
){
    Log.d("BasicMode:notes", notes)
    InfoCard {
        ApplicationTitle(password.appInfo.appName)
        SectionTitle("Información resumida")
        CustomRow("Nombre de usuario", password.appInfo.appAccount)
        CustomRow("Sitio Web", password.appInfo.appUrl)
        CustomRow("Última Modificación", password.dateInfo.lastUpdate.formatAs(settings.dateFormat))
        CustomRow("Puntuación de seguridad", String.format("%.2f", password.score))
    }
    NotesSection(notes)
}