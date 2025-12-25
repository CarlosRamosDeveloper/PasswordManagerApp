package com.cr_d.passwordmanagerapp.ui.screens.password_detail.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.cr_d.passwordmanagerapp.domain.entities.PasswordPolicy
import com.cr_d.passwordmanagerapp.ui.common_components.ApplicationOutlinedTextField
import com.cr_d.passwordmanagerapp.ui.common_components.CardTitle
import com.cr_d.passwordmanagerapp.ui.common_components.CustomRow
import com.cr_d.passwordmanagerapp.ui.common_components.DecimalFormField
import com.cr_d.passwordmanagerapp.ui.common_components.ErrorMessage
import com.cr_d.passwordmanagerapp.ui.common_components.SectionTitle
import com.cr_d.passwordmanagerapp.ui.models.AppConfig
import com.cr_d.passwordmanagerapp.ui.models.PasswordOption
import com.cr_d.passwordmanagerapp.ui.screens.password_detail.PasswordDetailViewModel

@Composable
fun EditMode(isGeneratePasswordEnabled: Boolean, passwordState: PasswordDetailViewModel.UiState, viewModel: PasswordDetailViewModel, snackFunction: (String)-> Unit){
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(
            vertical = AppConfig.HORIZONTAL_FRAME_PADDING),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ApplicationEditInfo(passwordState, viewModel)
        PasswordGenerationToggle(isGeneratePasswordEnabled, viewModel)
        if(isGeneratePasswordEnabled) MetadataEditInfo(passwordState, viewModel)
        PasswordEditInfo(passwordState, viewModel, snackFunction)
    }
}

@Composable
fun ApplicationEditInfo(passwordState: PasswordDetailViewModel.UiState, viewModel: PasswordDetailViewModel){
    InfoCard {
        SectionTitle("Información de aplicación")
        ApplicationOutlinedTextField("Aplicacion", passwordState.editInfo.newAppName, viewModel::onAppNameChanged)
        ApplicationOutlinedTextField("Url", passwordState.editInfo.newUrl, viewModel::onUrlChanged)
        ApplicationOutlinedTextField("Cuenta", passwordState.editInfo.newAccount, viewModel::onAccountChanged)
    }
}

@Composable
fun PasswordEditInfo(passwordState: PasswordDetailViewModel.UiState, viewModel: PasswordDetailViewModel, snackFunction: (String)-> Unit){
    InfoCard {
        CardTitle("Contraseña")
        CustomRow(
            "Puntuación de seguridad",
            String.format("%.2f", passwordState.editInfo.newSecurityScore),
            false
        )
        ApplicationOutlinedTextField("Contraseña", passwordState.editInfo.newPlainPassword.value, viewModel::onPlainPasswordChange)
        UpdatePasswordButton(viewModel, snackFunction)
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
        ){viewModel.onOptionChanged(PasswordOption.LOWERCASE, it) }
        CustomCheckboxForm(
            "Contiene mayusculas",
            passwordState.editInfo.newUpperCase,
        ){viewModel.onOptionChanged(PasswordOption.UPPERCASE, it) }
        CustomCheckboxForm(
            "Contiene numeros",
            passwordState.editInfo.newNumbers,
        ){viewModel.onOptionChanged(PasswordOption.NUMBERS, it) }
        CustomCheckboxForm(
            "Contiene carácteres especiales",
            passwordState.editInfo.newSpecials,
        ){viewModel.onOptionChanged(PasswordOption.SPECIALS, it) }
        Text("La longitud mínima de la nueva contraseña será de ${PasswordPolicy.MIN_GENERATED_LENGTH}")
        DecimalFormField(passwordState.editInfo.newPasswordLength, viewModel::onPasswordLengthChanged)
        if(errorMessage != "") ErrorMessage(errorMessage)
        GeneratePasswordButton(viewModel)
    }
}

@Composable
fun CustomCheckboxForm(labelText: String, value: Boolean, onValueChange: (Boolean) -> Unit){
    Row(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(labelText)
        Spacer(Modifier.weight(1f))
        Checkbox(
            checked = value,
            onCheckedChange = { onValueChange(!value) }
        )
    }
}
