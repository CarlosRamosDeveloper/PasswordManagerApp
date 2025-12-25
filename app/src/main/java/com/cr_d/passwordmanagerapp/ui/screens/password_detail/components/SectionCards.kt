package com.cr_d.passwordmanagerapp.ui.screens.password_detail.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.cr_d.passwordmanagerapp.domain.value_objects.ApplicationInfo
import com.cr_d.passwordmanagerapp.domain.value_objects.PasswordMetadata
import com.cr_d.passwordmanagerapp.ui.models.formatAs
import com.cr_d.passwordmanagerapp.ui.screens.password_detail.horizontalFramePadding
import com.cr_d.passwordmanagerapp.ui.screens.settings.SettingsViewModel

@Composable
fun ApplicationInfoSection(appInfo: ApplicationInfo){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = horizontalFramePadding, vertical = 10.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            ApplicationTitle(appInfo.applicationName)
            SectionTitle("Información de aplicación")
            CustomRow("Nombre de usuario", appInfo.account)
            CustomRow("Sitio Web", appInfo.url)
        }
    }
}

@Composable
fun MetadataInfoSection(metadataInfo: PasswordMetadata, settings: SettingsViewModel.AppSettings){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = horizontalFramePadding, vertical = 10.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            SectionTitle("Metadatos de la contraseña")
            CustomRow("Fecha de creación:", metadataInfo.creationDate.formatAs(settings.dateFormat))
            CustomRow("Última actualización:", metadataInfo.lastUpdate.formatAs(settings.dateFormat))
            CustomCheck("Contiene minúsculas", metadataInfo.hasLowerCase)
            CustomCheck("Contiene mayúsculas", metadataInfo.hasUpperCase)
            CustomCheck("Contiene números", metadataInfo.hasNumbers)
            CustomCheck("Contiene especiales", metadataInfo.hasSpecials)
        }
    }
}

@Composable
fun SecurityInfoSection(securityScore: String){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = horizontalFramePadding, vertical = 10.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            SectionTitle("Información de seguridad")
            CustomRow("Puntuación de seguridad", securityScore)
        }
    }
}