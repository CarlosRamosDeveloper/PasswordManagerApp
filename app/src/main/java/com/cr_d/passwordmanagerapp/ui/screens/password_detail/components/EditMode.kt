package com.cr_d.passwordmanagerapp.ui.screens.password_detail.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.cr_d.passwordmanagerapp.domain.value_objects.PlainPassword
import com.cr_d.passwordmanagerapp.ui.common_components.CustomOutlinedTextField
import com.cr_d.passwordmanagerapp.ui.common_components.CardTitle
import com.cr_d.passwordmanagerapp.ui.common_components.CustomCheckboxForm
import com.cr_d.passwordmanagerapp.ui.common_components.CustomRow
import com.cr_d.passwordmanagerapp.ui.common_components.DecimalFormField
import com.cr_d.passwordmanagerapp.ui.common_components.ErrorMessage
import com.cr_d.passwordmanagerapp.ui.common_components.FullWidthButton
import com.cr_d.passwordmanagerapp.ui.common_components.InfoCard
import com.cr_d.passwordmanagerapp.ui.common_components.PasswordTextField
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
    passwordError: String,
    notes: String,
    haveChangedNotes: Boolean,
    hasPasswordChanged: Boolean
){
    Column(modifier = Modifier
        .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ApplicationEditInfo(passwordState, viewModel)
        NoteEditInfo(
            notes = notes,
            onChangeNotesFunction = viewModel::onNotesHasChanged,
            haveNotesChanged = haveChangedNotes,
            enableUpdateNotesDialogFunction = viewModel::onEnableUpdateNotesDialog,
            enableDeleteNotesDialogFunction = viewModel::onEnableDeleteNotesDialog
        )
        PasswordGenerationToggle(isGeneratePasswordEnabled, viewModel::onGeneratePasswordSectionToggle)
        if(isGeneratePasswordEnabled) MetadataEditInfo(passwordState, passwordError, viewModel)
        NewPasswordInfoCard(
            newPassword = newPlainPassword,
            isPasswordShown = isPasswordShown,
            scoreValue = String.format("%.2f", passwordState.score),
            viewModel = viewModel,
            hasPasswordChanged = hasPasswordChanged
        )
    }
}

@Composable
fun ApplicationEditInfo(passwordState: PasswordEditUiState, viewModel: PasswordDetailViewModel){
    InfoCard {
        CardTitle("Información de aplicación")
        CustomOutlinedTextField(
            "Aplicacion",
            passwordState.appName,
            viewModel::onAppNameChanged
        )
        CustomOutlinedTextField(
            "Url",
            passwordState.appUrl,
            viewModel::onUrlChanged
        )
        CustomOutlinedTextField(
            "Cuenta",
            passwordState.appAccount,
            viewModel::onAccountChanged
        )
        Spacer(Modifier.size(15.dp))
    }
}

@Composable
fun NewPasswordInfoCard(
    newPassword: PlainPassword,
    isPasswordShown: Boolean,
    scoreValue: String,
    viewModel: PasswordDetailViewModel,
    hasPasswordChanged: Boolean
    ){
    InfoCard {
        CardTitle("Contraseña")
        CustomRow(
            "Puntuación de seguridad",
            scoreValue,
            false
        )
        TogglePasswordVisibilityButton(isPasswordShown, viewModel::onPasswordVisibilityToggle)
        PasswordTextField(isPasswordShown, newPassword.value, viewModel::onPlainPasswordChange)
        if(hasPasswordChanged) FullWidthButton("Actualizar contraseña",viewModel::onEnableUpdateDialog)
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

@Composable
fun NoteEditInfo(
    notes: String,
    onChangeNotesFunction: (String) -> Unit,
    haveNotesChanged: Boolean,
    enableUpdateNotesDialogFunction: () -> Unit,
    enableDeleteNotesDialogFunction: () -> Unit
){
    InfoCard {
        CustomOutlinedTextField(
            label = "Notas",
            param = notes,
            onValueChange = { onChangeNotesFunction(it) },
            isSingleLine = false
        )
        if (notes.isNotBlank()) FullWidthButton("Eliminar notas", enableDeleteNotesDialogFunction)
        if (haveNotesChanged) FullWidthButton("Actualizar notas", enableUpdateNotesDialogFunction)
    }
}

