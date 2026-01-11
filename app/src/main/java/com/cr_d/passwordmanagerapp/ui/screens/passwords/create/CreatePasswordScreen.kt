package com.cr_d.passwordmanagerapp.ui.screens.passwords.create

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle

import com.cr_d.passwordmanagerapp.domain.value_objects.PasswordMetadata
import com.cr_d.passwordmanagerapp.ui.common_components.CardTitle
import com.cr_d.passwordmanagerapp.ui.common_components.ConfirmDialog
import com.cr_d.passwordmanagerapp.ui.common_components.CustomButton
import com.cr_d.passwordmanagerapp.ui.common_components.CustomCheckboxForm
import com.cr_d.passwordmanagerapp.ui.common_components.CustomOutlinedTextField
import com.cr_d.passwordmanagerapp.ui.common_components.DecimalFormField
import com.cr_d.passwordmanagerapp.ui.common_components.ErrorMessage
import com.cr_d.passwordmanagerapp.ui.common_components.InfoCard
import com.cr_d.passwordmanagerapp.ui.common_components.UnderFormSpacer
import com.cr_d.passwordmanagerapp.ui.model.PasswordOption

@SuppressLint("DefaultLocale")
@Composable
fun CreatePasswordScreen(innerPadding: PaddingValues, viewModel: CreatePasswordViewModel, context: Context, snackFunction: (String)-> Unit){
    val state = viewModel.uiState.collectAsStateWithLifecycle().value

    Column(Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())
        .padding(innerPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CardTitle("Crear nueva contraseña")
        AddPasswordSection(state.plainPassword, String.format("%.2f",state.passwordScore),state.passwordNotes, viewModel)

        if (state.isPasswordGenerationEnabled) GeneratePasswordSection(state.passwordError, state.password.metadata, state.passwordLength, viewModel, snackFunction)
        if(state.isAccountSectionEnabled) AddAccountSection(state.accountName, state.accountNotes, viewModel)
        if(state.isApplicationSectionEnabled) AddApplicationSection(state.appName, state.appUrl, state.appNotes, viewModel)
        if(state.isSaveDialogEnabled) ConfirmDialog(
            title = "Guardar aplicación",
            message = "¿Guardar aplicación en la base de datos?",
            confirmButtonText = "Guardar aplicación",
            onConfirm = viewModel::savePassword,
            onDisable = viewModel::onDisableSaveDialog,
            onDismiss = viewModel::onDisableSaveDialog
        )
    }
}

@Composable
fun AddPasswordSection(password: String, formatedScore: String, notes: String, viewModel: CreatePasswordViewModel){

    InfoCard {
        CardTitle("Información de contraseña")

        Text("Puntuación: $formatedScore")
        CustomOutlinedTextField(
            label = "Contraseña",
            param = password,
            onValueChange = viewModel::onPlainPasswordChange,
            isSingleLine = false
        )
        CustomOutlinedTextField(
            label = "Notas",
            param = notes,
            onValueChange = viewModel::onPasswordNotesChange,
        )
        Row {
            CustomButton("Generar",viewModel::onEnablePasswordGeneration )
            CustomButton("Limpiar", viewModel::onPlainPasswordClear)
        }
    }
}

@Composable
fun AddAccountSection(accountName: String, accountNotes: String, viewModel: CreatePasswordViewModel){
    InfoCard {
        CardTitle("Información de cuenta")
        CustomOutlinedTextField("Nombre de cuenta", accountName, viewModel::onAccountNameChanged)
        CustomOutlinedTextField("Notas", accountNotes, viewModel::onAccountNotesChanged)
        CustomButton("Limpiar", viewModel::onAccountClear)
    }
}

@Composable
fun AddApplicationSection(appName: String, appUrl: String, appNotes: String, viewModel: CreatePasswordViewModel){
    InfoCard {
        CardTitle("Información de aplicación")
        CustomOutlinedTextField("Nombre de aplicación", appName, viewModel::onAppNameChange)
        CustomOutlinedTextField("Dirección", appUrl, viewModel::onAppUrlChange)
        CustomOutlinedTextField("Notas", appNotes, viewModel::onAppNotesChange)
        CustomButton("Limpiar", viewModel::onAppClear)
    }
}

@Composable
fun GeneratePasswordSection(error: String, metadataInfo: PasswordMetadata, passwordLength: Int, viewModel: CreatePasswordViewModel, snackFunction: (String)-> Unit){
    InfoCard {
        CardTitle("Características")
        CustomCheckboxForm("Minúsculas", metadataInfo.hasLowerCase) { viewModel.onOptionChanged(PasswordOption.LOWERCASE, it) }
        CustomCheckboxForm("Mayúsculas", metadataInfo.hasUpperCase) { viewModel.onOptionChanged(PasswordOption.UPPERCASE, it) }
        CustomCheckboxForm("Números", metadataInfo.hasNumbers) { viewModel.onOptionChanged(PasswordOption.NUMBERS, it) }
        CustomCheckboxForm("Carácteres especiales", metadataInfo.hasSpecials) { viewModel.onOptionChanged(PasswordOption.SPECIALS, it) }
        DecimalFormField(passwordLength, viewModel::onPasswordLengthChanged)
        UnderFormSpacer()
        if (error.isNotBlank()) ErrorMessage(error)
        PasswordButtonsSection(
            generatePasswordFunction = viewModel::generatePassword,
            clearPasswordFunction = viewModel::onDisablePasswordGeneration,
        )
    }
}

@Composable
fun PasswordButtonsSection(
    generatePasswordFunction: () -> Unit,
    clearPasswordFunction: () -> Unit,
){
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly){
        CustomButton("Generar contraseña", generatePasswordFunction)
        CustomButton("Ocultar Generación", clearPasswordFunction)
    }
}
