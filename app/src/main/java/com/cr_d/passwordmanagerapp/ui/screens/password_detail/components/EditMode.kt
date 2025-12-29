package com.cr_d.passwordmanagerapp.ui.screens.password_detail.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

import com.cr_d.passwordmanagerapp.domain.value_objects.PlainPassword
import com.cr_d.passwordmanagerapp.ui.common_components.ApplicationOutlinedTextField
import com.cr_d.passwordmanagerapp.ui.common_components.CardTitle
import com.cr_d.passwordmanagerapp.ui.common_components.CustomCheckboxForm
import com.cr_d.passwordmanagerapp.ui.common_components.CustomRow
import com.cr_d.passwordmanagerapp.ui.common_components.DecimalFormField
import com.cr_d.passwordmanagerapp.ui.common_components.ErrorMessage
import com.cr_d.passwordmanagerapp.ui.common_components.InfoCard
import com.cr_d.passwordmanagerapp.ui.models.PasswordEditUiState
import com.cr_d.passwordmanagerapp.ui.models.PasswordOption
import com.cr_d.passwordmanagerapp.ui.screens.password_detail.PasswordDetailViewModel

@Composable
fun EditMode(
    newPlainPassword: PlainPassword,
    isPasswordShown: Boolean,
    isGeneratePasswordEnabled: Boolean,
    passwordState: PasswordEditUiState,
    viewModel: PasswordDetailViewModel,
    snackFunction: (String)-> Unit
){
    Column(modifier = Modifier
        .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val error = viewModel.uiState.collectAsStateWithLifecycle().value.errorMessage

        ApplicationEditInfo(passwordState, viewModel)
        PasswordGenerationToggle(isGeneratePasswordEnabled, viewModel::onGeneratePasswordSectionToggle)
        if(isGeneratePasswordEnabled) MetadataEditInfo(passwordState, error, viewModel)
        NewPasswordInfoCard(
            newPassword = newPlainPassword,
            isPasswordShown = isPasswordShown,
            passwordState = passwordState,
            viewModel = viewModel,
            snackFunction = snackFunction
        )
    }
}

@Composable
fun ApplicationEditInfo(passwordState: PasswordEditUiState, viewModel: PasswordDetailViewModel){
    InfoCard {
        CardTitle("Información de aplicación")
        ApplicationOutlinedTextField(
            "Aplicacion",
            passwordState.appName,
            viewModel::onAppNameChanged
        )
        ApplicationOutlinedTextField(
            "Url",
            passwordState.appUrl,
            viewModel::onUrlChanged
        )
        ApplicationOutlinedTextField(
            "Cuenta",
            passwordState.appAccount,
            viewModel::onAccountChanged
        )
        Spacer(Modifier.size(15.dp))
    }
}

@Composable
fun NewPasswordInfoCard(newPassword: PlainPassword, isPasswordShown: Boolean, passwordState: PasswordEditUiState, viewModel: PasswordDetailViewModel, snackFunction: (String)-> Unit){
    InfoCard {
        CardTitle("Contraseña")
        CustomRow(
            "Puntuación de seguridad",
            String.format("%.2f", passwordState.score),
            false
        )
        TogglePasswordVisibilityButton(isPasswordShown, viewModel::onPasswordVisibilityToggle)
        ApplicationOutlinedTextField(
            "Contraseña",
            if(isPasswordShown) newPassword.value
            else "*******",
            viewModel::onPlainPasswordChange
        )
        UpdatePasswordButton(snackFunction, viewModel::onUpdatePassword)
    }
}

@Composable
fun MetadataEditInfo(passwordState: PasswordEditUiState, passwordError: String, viewModel: PasswordDetailViewModel){

    InfoCard {
        CardTitle("Nueva contraseña")
        CustomCheckboxForm(
            "Contiene minúsculas",
            passwordState.hasLowerCase,
        ) { viewModel.onOptionChanged(PasswordOption.LOWERCASE, it) }
        CustomCheckboxForm(
            "Contiene mayusculas",
            passwordState.hasUpperCase,
        ) { viewModel.onOptionChanged(PasswordOption.UPPERCASE, it) }
        CustomCheckboxForm(
            "Contiene numeros",
            passwordState.hasNumbers,
        ) { viewModel.onOptionChanged(PasswordOption.NUMBERS, it) }
        CustomCheckboxForm(
            "Contiene carácteres especiales",
            passwordState.hasSpecials,
        ) { viewModel.onOptionChanged(PasswordOption.SPECIALS, it) }

        DecimalFormField(
            passwordState.passwordLength,
            viewModel::onPasswordLengthChanged
        )
        if (passwordError != "") ErrorMessage(passwordError)
        GeneratePasswordButton(viewModel)
    }
}

