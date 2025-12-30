package com.cr_d.passwordmanagerapp.ui.screens.password_detail.components

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.cr_d.passwordmanagerapp.domain.value_objects.ApplicationInfo
import com.cr_d.passwordmanagerapp.domain.value_objects.DateInfo
import com.cr_d.passwordmanagerapp.domain.value_objects.PasswordMetadata
import com.cr_d.passwordmanagerapp.ui.common_components.ApplicationTitle
import com.cr_d.passwordmanagerapp.ui.common_components.CardTitle
import com.cr_d.passwordmanagerapp.ui.common_components.CopyToClipboardButton
import com.cr_d.passwordmanagerapp.ui.common_components.CustomCheck
import com.cr_d.passwordmanagerapp.ui.common_components.CustomRow
import com.cr_d.passwordmanagerapp.ui.common_components.InfoCard
import com.cr_d.passwordmanagerapp.ui.common_components.SectionTitle
import com.cr_d.passwordmanagerapp.ui.models.formatAs
import com.cr_d.passwordmanagerapp.ui.screens.settings.SettingsViewModel

@Composable
fun ApplicationInfoSection(appInfo: ApplicationInfo){
    InfoCard {
        ApplicationTitle(appInfo.appName)
        SectionTitle("Información de aplicación")
        CustomRow("Nombre de usuario", appInfo.appAccount)
        CustomRow("Sitio Web", appInfo.appUrl)
    }
}

@Composable
fun MetadataInfoSection(metadataInfo: PasswordMetadata, dateInfo: DateInfo, settings: SettingsViewModel.AppSettings){
    InfoCard {
        SectionTitle("Metadatos de la contraseña")
        CustomRow("Fecha de creación:", dateInfo.creationDate.formatAs(settings.dateFormat))
        CustomRow("Última actualización:", dateInfo.lastUpdate.formatAs(settings.dateFormat))
        CustomCheck("Contiene minúsculas", metadataInfo.hasLowerCase)
        CustomCheck("Contiene mayúsculas", metadataInfo.hasUpperCase)
        CustomCheck("Contiene números", metadataInfo.hasNumbers)
        CustomCheck("Contiene especiales", metadataInfo.hasSpecials)
    }
}

@Composable
fun SecurityInfoSection(securityScore: String){
    InfoCard{
        SectionTitle("Información de seguridad")
        CustomRow("Puntuación de seguridad", securityScore)
    }
}

@Composable
fun PasswordCard(
    passwordPlainText: String,
    onVisibilityToggleFunction: () -> Unit,
    isPasswordShown: Boolean,
    copyToClipboardFunction: () -> Unit
){
    InfoCard {
        CardTitle("Contraseña")
        ButtonsInPasswordSection(
            isPasswordShown = isPasswordShown,
            onVisibilityToggleFunction = onVisibilityToggleFunction,
            copyToClipboardFunction = copyToClipboardFunction
        )
        if (isPasswordShown) Text(passwordPlainText)
        else Text("********")

    }
}

// TODO: Rama nueva -> Poner avisos en detailScreen
/*
*
*  Actualizar contraseña -> Este paso no se puede deshacer -> Atras / Actualizar
*
* */
@Composable
fun ButtonsInPasswordSection(
    isPasswordShown: Boolean,
    onVisibilityToggleFunction: () -> Unit,
    copyToClipboardFunction: () -> Unit
){
    Row (Modifier
        .fillMaxWidth()
        .padding(bottom = 5.dp), horizontalArrangement = Arrangement.SpaceAround){
        TogglePasswordVisibilityButton(isPasswordShown, onVisibilityToggleFunction)
        CopyToClipboardButton(copyToClipboardFunction)
    }
}
