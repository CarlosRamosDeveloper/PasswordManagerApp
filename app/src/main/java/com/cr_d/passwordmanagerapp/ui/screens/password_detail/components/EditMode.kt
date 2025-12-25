package com.cr_d.passwordmanagerapp.ui.screens.password_detail.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.cr_d.passwordmanagerapp.domain.entities.PasswordPolicy
import com.cr_d.passwordmanagerapp.ui.common_components.ApplicationOutlinedTextField
import com.cr_d.passwordmanagerapp.ui.common_components.CardTitle
import com.cr_d.passwordmanagerapp.ui.common_components.CustomCheckboxForm
import com.cr_d.passwordmanagerapp.ui.common_components.CustomRow
import com.cr_d.passwordmanagerapp.ui.common_components.DecimalFormField
import com.cr_d.passwordmanagerapp.ui.common_components.ErrorMessage
import com.cr_d.passwordmanagerapp.ui.common_components.InfoCard
import com.cr_d.passwordmanagerapp.ui.models.PasswordOption
import com.cr_d.passwordmanagerapp.ui.screens.password_detail.PasswordDetailViewModel

@Composable
fun EditMode(isGeneratePasswordEnabled: Boolean, passwordState: PasswordDetailViewModel.UiState, viewModel: PasswordDetailViewModel, snackFunction: (String)-> Unit){
    Column(modifier = Modifier
        .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ApplicationEditInfo(passwordState, viewModel)
        PasswordGenerationToggle(isGeneratePasswordEnabled, viewModel::onGeneratePasswordSectionToggle)
        if(isGeneratePasswordEnabled) MetadataEditInfo(passwordState, viewModel)
        NewPasswordInfoCard(passwordState, viewModel, snackFunction)
    }
}

@Composable
fun ApplicationEditInfo(passwordState: PasswordDetailViewModel.UiState, viewModel: PasswordDetailViewModel){
    InfoCard {
        CardTitle("Información de aplicación")
        ApplicationOutlinedTextField(
            "Aplicacion",
            passwordState.editInfo.newAppName,
            viewModel::onAppNameChanged
        )
        ApplicationOutlinedTextField("Url", passwordState.editInfo.newUrl, viewModel::onUrlChanged)
        ApplicationOutlinedTextField(
            "Cuenta",
            passwordState.editInfo.newAccount,
            viewModel::onAccountChanged
        )
        Spacer(Modifier.size(15.dp))
    }
}

@Composable
fun NewPasswordInfoCard(passwordState: PasswordDetailViewModel.UiState, viewModel: PasswordDetailViewModel, snackFunction: (String)-> Unit){
    InfoCard {
        CardTitle("Contraseña")
        CustomRow(
            "Puntuación de seguridad",
            String.format("%.2f", passwordState.editInfo.newSecurityScore),
            false
        )
        ApplicationOutlinedTextField(
            "Contraseña",
            passwordState.editInfo.newPlainPassword.value,
            viewModel::onPlainPasswordChange
        )
        UpdatePasswordButton(snackFunction, viewModel::onUpdatePassword)
    }
}

@Composable
fun MetadataEditInfo(passwordState: PasswordDetailViewModel.UiState, viewModel: PasswordDetailViewModel){
    val errorMessage = passwordState.errorMessage
    InfoCard {
        CardTitle("Nueva contraseña")
        CustomCheckboxForm(
            "Contiene minúsculas",
            passwordState.editInfo.newLowerCase,
        ) { viewModel.onOptionChanged(PasswordOption.LOWERCASE, it) }
        CustomCheckboxForm(
            "Contiene mayusculas",
            passwordState.editInfo.newUpperCase,
        ) { viewModel.onOptionChanged(PasswordOption.UPPERCASE, it) }
        CustomCheckboxForm(
            "Contiene numeros",
            passwordState.editInfo.newNumbers,
        ) { viewModel.onOptionChanged(PasswordOption.NUMBERS, it) }
        CustomCheckboxForm(
            "Contiene carácteres especiales",
            passwordState.editInfo.newSpecials,
        ) { viewModel.onOptionChanged(PasswordOption.SPECIALS, it) }

        DecimalFormField(
            passwordState.editInfo.newPasswordLength,
            viewModel::onPasswordLengthChanged
        )
        if (errorMessage != "") ErrorMessage(errorMessage)
        GeneratePasswordButton(viewModel)
    }
}

