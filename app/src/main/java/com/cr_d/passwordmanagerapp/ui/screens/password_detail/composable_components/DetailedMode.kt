package com.cr_d.passwordmanagerapp.ui.screens.password_detail.composable_components

import androidx.compose.runtime.Composable

import com.cr_d.passwordmanagerapp.ui.models.PasswordUiState
import com.cr_d.passwordmanagerapp.ui.screens.settings.SettingsViewModel

@Composable
fun DetailedMode(
    password: PasswordUiState,
    settings: SettingsViewModel.AppSettings
){
    ApplicationInfoSection(password.appInfo)
    MetadataInfoSection(password.metadata, password.dateInfo, settings)
    SecurityInfoSection(String.format("%.2f", password.score))
}
