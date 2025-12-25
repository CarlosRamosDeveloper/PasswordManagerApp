package com.cr_d.passwordmanagerapp.ui.screens.password_detail.components

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cr_d.passwordmanagerapp.domain.value_objects.ApplicationInfo

import com.cr_d.passwordmanagerapp.domain.value_objects.PasswordMetadata
import com.cr_d.passwordmanagerapp.ui.models.formatAs
import com.cr_d.passwordmanagerapp.ui.screens.password_detail.PasswordDetailViewModel
import com.cr_d.passwordmanagerapp.ui.screens.settings.SettingsViewModel

@Composable
fun DetailedMode(
    viewModel: PasswordDetailViewModel,
    settings: SettingsViewModel
){
    val password = viewModel.uiState.collectAsStateWithLifecycle().value.password ?: return
    val settings = settings.settings.collectAsStateWithLifecycle().value

    ApplicationInfoSection(password.appInfo)
    MetadataInfoSection(password.metadata, settings)
    SecurityInfoSection(password.securityScore)
}

@Composable
fun ApplicationInfoSection(appInfo: ApplicationInfo){
    ApplicationTitle(appInfo.applicationName)
    SectionTitle("Información de aplicación")
    CustomRow("Nombre de usuario", appInfo.account)
    CustomRow("Sitio Web", appInfo.url)
}

@Composable
fun MetadataInfoSection(metadataInfo: PasswordMetadata, settings: SettingsViewModel.AppSettings){
    SectionTitle("Metadatos de la contraseña")
    CustomRow("Fecha de creación:", "${metadataInfo.creationDate.formatAs(settings.dateFormat)}")
    CustomRow("Última actualización:", "${metadataInfo.lastUpdate.formatAs(settings.dateFormat)}")
    CustomCheck("Minúsculas", metadataInfo.hasLowerCase)
    CustomCheck("Mayúsculas", metadataInfo.hasUpperCase)
    CustomCheck("Números", metadataInfo.hasNumbers)
    CustomCheck("Especiales", metadataInfo.hasSpecials)
}

@Composable
fun SecurityInfoSection(securityScore: Double){
    SectionTitle("Información de seguridad")
    CustomRow("Puntuación de seguridad", "${String.format("%.2f", securityScore)}")
}
