package com.cr_d.passwordmanagerapp.ui.screens.password_detail.components

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.cr_d.passwordmanagerapp.domain.value_objects.ApplicationInfo
import com.cr_d.passwordmanagerapp.domain.value_objects.PasswordData
import com.cr_d.passwordmanagerapp.domain.value_objects.PasswordMetadata
import com.cr_d.passwordmanagerapp.ui.models.formatAs
import com.cr_d.passwordmanagerapp.ui.screens.password_detail.PasswordDetailViewModel
import com.cr_d.passwordmanagerapp.ui.screens.settings.SettingsViewModel

@Composable
fun ApplicationInfoSection(appInfo: ApplicationInfo){
    InfoCard {
        ApplicationTitle(appInfo.applicationName)
        SectionTitle("Información de aplicación")
        CustomRow("Nombre de usuario", appInfo.account)
        CustomRow("Sitio Web", appInfo.url)
    }
}

@Composable
fun MetadataInfoSection(metadataInfo: PasswordMetadata, settings: SettingsViewModel.AppSettings){
    InfoCard {
        SectionTitle("Metadatos de la contraseña")
        CustomRow("Fecha de creación:", metadataInfo.creationDate.formatAs(settings.dateFormat))
        CustomRow("Última actualización:", metadataInfo.lastUpdate.formatAs(settings.dateFormat))
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
    password: PasswordData,
    viewModel: PasswordDetailViewModel,
    isPasswordShown: Boolean,
    context: Context,
    snackFunction: (String) -> Unit
){
    InfoCard {
        CardTitle("Contraseña")
        HorizontalDivider(thickness = 2.dp, modifier = Modifier.padding(vertical = 10.dp))
        ButtonsInPasswordSection(
            password = password,
            viewModel = viewModel,
            context = context,
            snackFunction = snackFunction
        )
        if (isPasswordShown) Text(password.plainPassword.value)
        else Text("********")

    }
}

@Composable
fun ButtonsInPasswordSection(
    password: PasswordData?,
    viewModel: PasswordDetailViewModel,
    context: Context,
    snackFunction: (String)-> Unit,
){
    if (password == null) return
    Row (Modifier
        .fillMaxWidth()
        .padding(bottom = 5.dp), horizontalArrangement = Arrangement.SpaceAround){
        TogglePasswordVisibilityButton(viewModel)
        CopyToClipboardButton(password.plainPassword.value, context, snackFunction)
    }
}
