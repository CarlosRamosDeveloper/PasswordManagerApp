package com.cr_d.passwordmanagerapp.ui.screens.passwords.create

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
import com.cr_d.passwordmanagerapp.ui.model.ApplicationInfo
import com.cr_d.passwordmanagerapp.ui.common_components.CustomOutlinedTextField
import com.cr_d.passwordmanagerapp.ui.common_components.CardTitle
import com.cr_d.passwordmanagerapp.ui.common_components.ConfirmDialog
import com.cr_d.passwordmanagerapp.ui.common_components.CopyToClipboardButton
import com.cr_d.passwordmanagerapp.ui.common_components.CustomButton
import com.cr_d.passwordmanagerapp.ui.common_components.CustomCheckboxForm
import com.cr_d.passwordmanagerapp.ui.common_components.CustomRow
import com.cr_d.passwordmanagerapp.ui.common_components.DecimalFormField
import com.cr_d.passwordmanagerapp.ui.common_components.ErrorMessage
import com.cr_d.passwordmanagerapp.ui.common_components.FullWidthButton
import com.cr_d.passwordmanagerapp.ui.common_components.InfoCard
import com.cr_d.passwordmanagerapp.ui.common_components.UnderFormSpacer
import com.cr_d.passwordmanagerapp.ui.model.PasswordOption

@Composable
fun CreatePasswordScreen(innerPadding: PaddingValues, viewModel: CreatePasswordViewModel, context: Context, snackFunction: (String)-> Unit){
    val state = viewModel.uiState.collectAsStateWithLifecycle().value
    val password = state.password

    Column(Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())
        .padding(innerPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CardTitle("Crear nueva contraseña")
        MetadataInfo(password.metadata, state.passwordLength, viewModel)
        PasswordButtonsSection(
            generatePasswordFunction = viewModel::generatePassword,
            clearPasswordFunction = viewModel::clearPassword,
            snackFunction = snackFunction
        )

        if(state.passwordError.isNotBlank()) ErrorMessage(state.passwordError)

        if(state.generatedPassword.isNotBlank()) PasswordInfoSection(
            password = state.generatedPassword,
            passwordScore = state.password.score,
            copyToClipboardFunction = viewModel::onEnableCopyToDialog
        )

        if(state.generatedPassword.isNotBlank() &&
            password.appInfo.appUrl.isNotBlank() &&
            password.appInfo.appAccount.isNotBlank() &&
            password.appInfo.appName.isNotBlank())
            FullWidthButton("Almacenar contraseña",
                { viewModel.savePassword(state.generatedPassword) })

        if(state.generatedPassword.isNotBlank()) ApplicationSection(password.appInfo, viewModel)
        if(state.isCopyToDialogShown) ConfirmDialog(
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
                viewModel.onDisableCopyToDialog()
                clipboard.setPrimaryClip(clip)
                snackFunction("Contraseña copiada en el portapapeles")
            },
            onDisable = viewModel::onDisableCopyToDialog,
            onDismiss = viewModel::onDisableCopyToDialog
        )
    }
}

@Composable
fun MetadataInfo(metadataInfo: PasswordMetadata, passwordLength: Int, viewModel: CreatePasswordViewModel){
    InfoCard {
        CardTitle("Información de contraseña")
        CustomCheckboxForm("Minúsculas", metadataInfo.hasLowerCase) { viewModel.onOptionChanged(PasswordOption.LOWERCASE, it) }
        CustomCheckboxForm("Mayúsculas", metadataInfo.hasUpperCase) { viewModel.onOptionChanged(PasswordOption.UPPERCASE, it) }
        CustomCheckboxForm("Números", metadataInfo.hasNumbers) { viewModel.onOptionChanged(PasswordOption.NUMBERS, it) }
        CustomCheckboxForm("Carácteres especiales", metadataInfo.hasSpecials) { viewModel.onOptionChanged(PasswordOption.SPECIALS, it) }
        DecimalFormField(passwordLength, viewModel::onPasswordLengthChanged)
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
        CustomButton("Limpiar Contraseña", clearPasswordFunction)
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
