package com.cr_d.passwordmanagerapp.ui.screens.generate

import android.content.ClipData
import android.content.ClipDescription
import android.content.ClipboardManager
import android.content.Context
import android.os.PersistableBundle
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

import com.cr_d.passwordmanagerapp.domain.value_objects.PasswordMetadata
import com.cr_d.passwordmanagerapp.ui.common_components.CardTitle
import com.cr_d.passwordmanagerapp.ui.common_components.ConfirmDialog
import com.cr_d.passwordmanagerapp.ui.common_components.CopyToClipboardButton
import com.cr_d.passwordmanagerapp.ui.common_components.CustomButton
import com.cr_d.passwordmanagerapp.ui.common_components.CustomCheckboxForm
import com.cr_d.passwordmanagerapp.ui.common_components.CustomRow
import com.cr_d.passwordmanagerapp.ui.common_components.DecimalFormField
import com.cr_d.passwordmanagerapp.ui.common_components.ErrorMessage
import com.cr_d.passwordmanagerapp.ui.common_components.InfoCard
import com.cr_d.passwordmanagerapp.ui.common_components.UnderFormSpacer
import com.cr_d.passwordmanagerapp.ui.model.PasswordOption

@Composable
fun GeneratePasswordScreen(innerPadding: PaddingValues, viewModel: GeneratePasswordViewModel, context: Context, snackFunction: (String)-> Unit){
    val state = viewModel.uiState.collectAsStateWithLifecycle().value
    val metadata = state.metadata

    //TODO: FAB -> Se habilita si al menos uno de los 4 metadata está activo

    Column(Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())
        .padding(innerPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CardTitle("Generar contraseña")
        MetadataInfo(metadata, state.passwordLength, viewModel)

        /*
        PasswordButtonsSection(
            generatePasswordFunction = viewModel::generatePassword,
            clearPasswordFunction = viewModel::clearPassword,
            snackFunction = snackFunction
        )
        */
        CustomButton("Limpiar Contraseña", viewModel::clearData)
        if(state.passwordError.isNotBlank()) ErrorMessage(state.passwordError)

        if(state.generatedPassword.isNotBlank()) PasswordInfoSection(
            password = state.generatedPassword,
            passwordScore = state.score,
            copyToClipboardFunction = viewModel::onEnableCopyDialog
        )

        if(state.isCopyDialogShown) ConfirmDialog(
            title = "Copiar contraseña",
            message = "La información en el portapapeles no está cifrada, se sugiere extremar precauciones",
            confirmButtonText = "Copiar en el portapapeles",
            onConfirm = {
                val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText("Copied_Text", state.generatedPassword).apply {
                    description.extras = PersistableBundle().apply {
                        putBoolean(ClipDescription.EXTRA_IS_SENSITIVE, true)
                    }
                }
                viewModel.onDisableCopyDialog()
                clipboard.setPrimaryClip(clip)
                snackFunction("Contraseña copiada en el portapapeles")
            },
            onDisable = viewModel::onDisableCopyDialog,
            onDismiss = viewModel::onDisableCopyDialog
        )
    }
}

@Composable
fun MetadataInfo(metadataInfo: PasswordMetadata, passwordLength: Int, viewModel: GeneratePasswordViewModel){
    InfoCard {
        CardTitle("Información de contraseña")
        CustomCheckboxForm("Minúsculas", metadataInfo.hasLowerCase) { viewModel.onOptionChanged(PasswordOption.LOWERCASE, it) }
        CustomCheckboxForm("Mayúsculas", metadataInfo.hasUpperCase) { viewModel.onOptionChanged(PasswordOption.UPPERCASE, it) }
        CustomCheckboxForm("Números", metadataInfo.hasNumbers) { viewModel.onOptionChanged(PasswordOption.NUMBERS, it) }
        CustomCheckboxForm("Carácteres especiales", metadataInfo.hasSpecials) { viewModel.onOptionChanged(PasswordOption.SPECIALS, it) }
        DecimalFormField(passwordLength, viewModel::onPasswordLengthChange)
        UnderFormSpacer()
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

    }
}
