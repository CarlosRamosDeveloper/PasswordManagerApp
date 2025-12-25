package com.cr_d.passwordmanagerapp.ui.screens.password_detail.components

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

import com.cr_d.passwordmanagerapp.domain.value_objects.PasswordData
import com.cr_d.passwordmanagerapp.ui.models.formatAs
import com.cr_d.passwordmanagerapp.ui.screens.password_detail.PasswordDetailViewModel
import com.cr_d.passwordmanagerapp.ui.screens.settings.SettingsViewModel

@Composable
fun DetailedMode(
    viewModel: PasswordDetailViewModel,
    settings: SettingsViewModel,
    password: PasswordData?,
    context: Context,
    snackFunction: (String)-> Unit,
){
    ApplicationInfoSection(viewModel)
    PasswordInfoSection(viewModel)
    DateInfoSection(viewModel, settings)
    SecurityInfoSection(viewModel)
    ButtonsSection(
        password = password,
        viewModel = viewModel,
        context = context,
        snackFunction = snackFunction,
    )
}


@Composable
fun ApplicationInfoSection(viewModel: PasswordDetailViewModel){
    val state = viewModel.uiState.collectAsStateWithLifecycle().value.password ?: return
    val appInfo = state.appInfo

    Text("Información de la aplicación")
    Text(appInfo.applicationName)
    Text("Sitio web: ${appInfo.url}")
    Text("Cuenta asociada: ${appInfo.account}")
}

@Composable
fun PasswordInfoSection(viewModel: PasswordDetailViewModel){
    val state = viewModel.uiState.collectAsState().value

    Text("Información de la contraseña")
    if (state.isPasswordShown) Text("Password: ${state.password!!.plainPassword.value}")
    else Text("Password: ********")
    MetadataBoolSection(viewModel)
}

@Composable
fun DateInfoSection(viewModel: PasswordDetailViewModel, settings: SettingsViewModel){
    val state = viewModel.uiState.collectAsStateWithLifecycle().value.password ?: return
    val metadataInfo = state.metadata
    val settings = settings.settings.collectAsState().value

    Text("Fecha de creación: ${metadataInfo.creationDate.formatAs(settings.dateFormat)}")
    Text("Última actualización: ${metadataInfo.lastUpdate.formatAs(settings.dateFormat)}")
}

@Composable
fun SecurityInfoSection(viewModel: PasswordDetailViewModel){
    val state = viewModel.uiState.collectAsStateWithLifecycle().value.password ?: return
    val securityScore = state.securityScore

    Text("Puntuación de seguridad ${String.format("%.2f", securityScore)}")
}

@Composable
fun ButtonsSection(
    password: PasswordData?,
    viewModel: PasswordDetailViewModel,
    context: Context,
    snackFunction: (String)-> Unit,
){
    if (password == null) return
    Row (Modifier.fillMaxWidth().padding(bottom = 5.dp), horizontalArrangement = Arrangement.SpaceAround){
        TogglePasswordVisibilityButton(viewModel)
        CopyToClipboardButton(password.plainPassword.value, context, snackFunction)
    }
}


@Composable
fun MetadataBoolSection(viewModel: PasswordDetailViewModel){
    val passwordData = viewModel.uiState.collectAsState().value.password ?: return
    val metadata = passwordData.metadata

    Row(
        modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
    ) {
        Column {
            MetadataCheck("Minúsculas", metadata.hasLowerCase)
            MetadataCheck("Mayúsculas", metadata.hasUpperCase)
        }
        Column {
            MetadataCheck("Números", metadata.hasNumbers)
            MetadataCheck("Especiales", metadata.hasSpecials)
        }
    }
}

@Composable
fun MetadataCheck(label: String, value: Boolean){
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(5.dp)) {
        Text(label)
        Icon(
            if (value) Icons.Default.Check
            else Icons.Default.Close,
            contentDescription = ""
        )
    }
}