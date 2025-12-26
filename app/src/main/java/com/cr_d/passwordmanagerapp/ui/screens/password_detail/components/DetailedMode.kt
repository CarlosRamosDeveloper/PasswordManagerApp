package com.cr_d.passwordmanagerapp.ui.screens.password_detail.components

import androidx.compose.runtime.Composable

import com.cr_d.passwordmanagerapp.domain.value_objects.PasswordData
import com.cr_d.passwordmanagerapp.ui.screens.settings.SettingsViewModel

@Composable
fun DetailedMode(
    password: PasswordData,
    settings: SettingsViewModel.AppSettings
){
    ApplicationInfoSection(password.appInfo)
    MetadataInfoSection(password.metadata, password.dateInfo, settings)
    SecurityInfoSection(String.format("%.2f", password.securityScore))
}
