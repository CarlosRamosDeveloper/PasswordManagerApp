package com.cr_d.passwordmanagerapp.ui.screens.create_password

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

import com.cr_d.passwordmanagerapp.ui.common_components.ApplicationOutlinedTextField
import com.cr_d.passwordmanagerapp.ui.common_components.CardTitle
import com.cr_d.passwordmanagerapp.ui.common_components.CopyToClipboardButton
import com.cr_d.passwordmanagerapp.ui.common_components.CustomButton
import com.cr_d.passwordmanagerapp.ui.common_components.CustomCheckboxForm
import com.cr_d.passwordmanagerapp.ui.common_components.CustomRow
import com.cr_d.passwordmanagerapp.ui.common_components.DecimalFormField
import com.cr_d.passwordmanagerapp.ui.models.PasswordOption
import com.cr_d.passwordmanagerapp.ui.common_components.ErrorMessage
import com.cr_d.passwordmanagerapp.ui.common_components.FullWidthButton
import com.cr_d.passwordmanagerapp.ui.common_components.InfoCard
import com.cr_d.passwordmanagerapp.ui.common_components.UnderFormSpacer

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
        MetadataInfo(state, viewModel)
        PasswordButtonsSection(viewModel, snackFunction)

        if(state.passwordError.isNotBlank()) ErrorMessage(state.passwordError)

        if(state.generatedPassword.isNotBlank()) PasswordInfoSection(state.generatedPassword, state.passwordScore, context, snackFunction)

        if(state.generatedPassword.isNotBlank() &&
            state.appUrl.isNotBlank() &&
            state.account.isNotBlank() &&
            state.appName.isNotBlank())
            FullWidthButton("Almacenar contraseña",
                { viewModel.savePassword(state.generatedPassword) })

        if(state.generatedPassword.isNotBlank()) ApplicationSection(state, viewModel)
    }
}

@Composable
fun MetadataInfo(metadataInfo: CreatePasswordViewModel.UiState, viewModel: CreatePasswordViewModel){
    InfoCard {
        CardTitle("Información de contraseña")
        CustomCheckboxForm("Minúsculas", metadataInfo.hasLowerCase) { viewModel.onOptionChanged(PasswordOption.LOWERCASE, it) }
        CustomCheckboxForm("Mayúsculas", metadataInfo.hasUpperCase) { viewModel.onOptionChanged(PasswordOption.UPPERCASE, it) }
        CustomCheckboxForm("Números", metadataInfo.hasNumbers) { viewModel.onOptionChanged(PasswordOption.NUMBERS, it) }
        CustomCheckboxForm("Carácteres especiales", metadataInfo.hasSpecials) { viewModel.onOptionChanged(PasswordOption.SPECIALS, it) }
        DecimalFormField(metadataInfo.passwordLength, viewModel::onPasswordLengthChanged)
        UnderFormSpacer()
    }
}

@Composable
fun PasswordInfoSection(password: String, passwordScore: Double, context: Context, snackFunction: (String)-> Unit){
    InfoCard {
        CardTitle("Contraseña")
        CustomRow("Puntuación de la contraseña",String.format("%.2f", passwordScore) )
        Box(Modifier.padding(10.dp)){
            Text(password)
        }
        CopyToClipboardButton(password, context, snackFunction)
        UnderFormSpacer()
    }
}

@Composable
fun PasswordButtonsSection(
    viewModel: CreatePasswordViewModel,
    snackFunction: (String)-> Unit
){
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly){
        CustomButton("Generar contraseña",
            {
                viewModel.generatePassword()
                snackFunction("Contraseña creada satisfactoriamente")
            }
        )
        CustomButton("Limpiar Contraseña", viewModel::clearPassword)
    }
}

@Composable
fun ApplicationSection(state: CreatePasswordViewModel.UiState, viewModel: CreatePasswordViewModel){
    InfoCard {
        CardTitle("Información de aplicación")
        ApplicationOutlinedTextField("Nombre de aplicación", state.appName, viewModel::onAppNameChanged)
        ApplicationOutlinedTextField("Url de la aplicación", state.appUrl, viewModel::onAppUrlChanged)
        ApplicationOutlinedTextField("Cuenta", state.account, viewModel::onAccountChanged)
        UnderFormSpacer()
    }
}
