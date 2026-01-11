package com.cr_d.passwordmanagerapp.ui.screens.passwords.create

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

import com.cr_d.passwordmanagerapp.domain.value_objects.PasswordMetadata
import com.cr_d.passwordmanagerapp.ui.common_components.CardTitle
import com.cr_d.passwordmanagerapp.ui.common_components.CopyToClipboardButton
import com.cr_d.passwordmanagerapp.ui.common_components.CustomButton
import com.cr_d.passwordmanagerapp.ui.common_components.CustomCheckboxForm
import com.cr_d.passwordmanagerapp.ui.common_components.CustomOutlinedTextField
import com.cr_d.passwordmanagerapp.ui.common_components.CustomRow
import com.cr_d.passwordmanagerapp.ui.common_components.DecimalFormField
import com.cr_d.passwordmanagerapp.ui.common_components.ErrorMessage
import com.cr_d.passwordmanagerapp.ui.common_components.InfoCard
import com.cr_d.passwordmanagerapp.ui.common_components.UnderFormSpacer
import com.cr_d.passwordmanagerapp.ui.model.ApplicationInfo
import com.cr_d.passwordmanagerapp.ui.model.PasswordOption

@SuppressLint("DefaultLocale")
@Composable
fun CreatePasswordScreen(innerPadding: PaddingValues, viewModel: CreatePasswordViewModel, context: Context, snackFunction: (String)-> Unit){
    val state = viewModel.uiState.collectAsStateWithLifecycle().value
    val password = state.password
    // TODO: Crear un campo para escribir contraseña
    // TODO: Botón de generación de contraseña
    // Activar ese botón saca el menú con las flags
    // Al darle a generar contraseña, sobreescribirá el campo de texto
    // Una vez escrita la contraseña, seleccionamos cuenta
    // Una vez seleccionada la cuenta, seleccionamos aplicación
    // Una vez seleccionada la aplicación, el botón de guardar se habilita

    Column(Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())
        .padding(innerPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CardTitle("Crear nueva contraseña")
        AddPasswordSection(state.plainPassword, String.format("%.2f",state.passwordScore),viewModel)

        if (state.isPasswordGenerationEnabled) MetadataInfo(password.metadata, state.passwordLength, viewModel, snackFunction)

        if(state.passwordError.isNotBlank()) ErrorMessage(state.passwordError)

        if(state.isAccountSectionEnabled) AddAccountSection(viewModel)
        if(state.isApplicationSectionEnabled) AddApplicationSection()
    }
}

@Composable
fun AddPasswordSection(password: String, formatedScore: String, viewModel: CreatePasswordViewModel){

    InfoCard {
        CardTitle("Información de contraseña")

        Text("Puntuación: $formatedScore")
        CustomOutlinedTextField(
            "Contraseña",
            password,
            onValueChange = viewModel::onPlainPasswordChange
        )
        Row {
            CustomButton("Mostrar generador de contraseñas",viewModel::onEnablePasswordGeneration )
            CustomButton("Limpiar contraseña", viewModel::onPlainPasswordClear)
        }
    }
}

@Composable
fun AddAccountSection(viewModel: CreatePasswordViewModel){
    val state = viewModel.uiState.collectAsStateWithLifecycle().value
    InfoCard {
        CardTitle("Información de cuenta")
        CustomOutlinedTextField("Nombre de cuenta", state.accountName, viewModel::onAccountNameChanged)
        CustomOutlinedTextField("Notas", state.accountNotes, viewModel::onAccountNotesChanged)
        Button(
            onClick = viewModel::onResetAccountData
        ) {
            Text("Reiniciar información de cuenta")
        }
    }
}

@Composable
fun AddApplicationSection(){
    Text("Application Enabled")
}

@Composable
fun MetadataInfo(metadataInfo: PasswordMetadata, passwordLength: Int, viewModel: CreatePasswordViewModel, snackFunction: (String)-> Unit){
    InfoCard {
        CardTitle("Características")
        CustomCheckboxForm("Minúsculas", metadataInfo.hasLowerCase) { viewModel.onOptionChanged(PasswordOption.LOWERCASE, it) }
        CustomCheckboxForm("Mayúsculas", metadataInfo.hasUpperCase) { viewModel.onOptionChanged(PasswordOption.UPPERCASE, it) }
        CustomCheckboxForm("Números", metadataInfo.hasNumbers) { viewModel.onOptionChanged(PasswordOption.NUMBERS, it) }
        CustomCheckboxForm("Carácteres especiales", metadataInfo.hasSpecials) { viewModel.onOptionChanged(PasswordOption.SPECIALS, it) }
        DecimalFormField(passwordLength, viewModel::onPasswordLengthChanged)
        UnderFormSpacer()

        PasswordButtonsSection(
            generatePasswordFunction = viewModel::generatePassword,
            clearPasswordFunction = viewModel::onDisablePasswordGeneration,
            snackFunction = snackFunction
        )
    }
}

@Composable
fun PasswordInfoSection(password: String, passwordScore: Double, copyToClipboardFunction: () -> Unit){
    InfoCard {
        CardTitle("Contraseña")
        CustomRow("Puntuación de la contraseña",String.format("%.2f", passwordScore) )
        Box(Modifier.padding(10.dp)){
            Text(password)
        }
        CopyToClipboardButton(copyToClipboardFunction)
        UnderFormSpacer()
    }
}

@Composable
fun PasswordButtonsSection(
    generatePasswordFunction: () -> Unit,
    clearPasswordFunction: () -> Unit,
    snackFunction: (String)-> Unit
){
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly){
        CustomButton("Generar contraseña",
            {
                generatePasswordFunction()
                snackFunction("Contraseña creada satisfactoriamente")
            }
        )
        CustomButton("Ocultar Generación", clearPasswordFunction)
    }
}

@Composable
fun ApplicationSection(appInfo: ApplicationInfo, viewModel: CreatePasswordViewModel){
    InfoCard {
        CardTitle("Información de aplicación")
        CustomOutlinedTextField("Nombre de aplicación", appInfo.appName, viewModel::onAppNameChanged)
        CustomOutlinedTextField("Url de la aplicación", appInfo.appUrl, viewModel::onAppUrlChanged)
        CustomOutlinedTextField("Cuenta", appInfo.appAccount, viewModel::onAccountChanged)
        UnderFormSpacer()
    }
}
