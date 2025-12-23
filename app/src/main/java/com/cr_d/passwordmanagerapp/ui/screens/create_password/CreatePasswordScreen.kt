package com.cr_d.passwordmanagerapp.ui.screens.create_password

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cr_d.passwordmanagerapp.R

import com.cr_d.passwordmanagerapp.ui.models.PasswordOption

@Composable
fun CreatePasswordScreen(innerPadding: PaddingValues, viewModel: CreatePasswordViewModel, context: Context, snackFunction: (String)-> Unit){
    val state = viewModel.uiState.collectAsState().value

    Column(Modifier
        .fillMaxSize()
        .padding(innerPadding), horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Crear contraseña")
        Row (modifier= Modifier.fillMaxWidth()){
            CustomCheckbox(
                "Minúsculas",
                state.hasLowerCase,
                { viewModel.onOptionChanged(PasswordOption.LOWERCASE, it) },
                Modifier.weight(1f)
            )
            CustomCheckbox(
                "Mayúsculas",
                state.hasUpperCase,
                { viewModel.onOptionChanged(PasswordOption.UPPERCASE, it) },
                Modifier.weight(1f)
            )
        }
        Row{
            CustomCheckbox(
                "Números",
                state.hasNumbers,
                { viewModel.onOptionChanged(PasswordOption.NUMBERS, it) },
                Modifier.weight(1f)
            )
            CustomCheckbox(
                "Carácteres especiales",
                state.hasSpecials,
                { viewModel.onOptionChanged(PasswordOption.SPECIALS, it) },
                Modifier.weight(1f)
            )
        }

        CampoDecimal(state.passwordLength, viewModel::onPasswordLengthChanged)

        PasswordButtonsSection(viewModel)

        if(state.passwordError.isNotBlank()) ErrorMessage(state.passwordError)

        if(state.generatedPassword.isNotBlank()) PasswordSection(state.generatedPassword, viewModel, context, snackFunction)

        if(state.generatedPassword.isNotBlank()) ApplicationSection(viewModel)

        if(state.generatedPassword.isNotBlank() &&
            state.appUrl.isNotBlank() &&
            state.account.isNotBlank() &&
            state.appName.isNotBlank()) AddPasswordButton(state.generatedPassword, viewModel)
    }
}

@Composable
fun CustomCheckbox(labelText: String, value: Boolean, onValueChange: (Boolean) -> Unit, modifier:  Modifier = Modifier ) {
    Row(
        modifier = modifier.padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = value,
            onCheckedChange = { onValueChange(!value) }
        )
        Text(labelText)
    }
}

@Composable
fun CampoDecimal(value: Int, onValueChange: (Int) -> Unit) {
    val textValue = value.toString()

    OutlinedTextField(
        value = textValue,
        onValueChange = { newText ->
            if (newText.matches(Regex("^\\d*\$"))) {
                val intValue = newText.toIntOrNull() ?: 0
                onValueChange(intValue)
            }
        },
        label = { Text("Valor decimal") },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        )
    )
}

@Composable
fun PasswordButtonsSection(
    viewModel: CreatePasswordViewModel,
){
    Row(Modifier.fillMaxWidth()){
        CreatePasswordButton(viewModel, Modifier.weight(1f))
        ClearPasswordButton(viewModel, Modifier.weight(1f))
    }
}

@Composable
fun CreatePasswordButton(
    viewModel: CreatePasswordViewModel,
    modifier: Modifier = Modifier
) {
    Column (modifier = modifier.padding(top = 30.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Button(onClick = viewModel::generatePassword
        ) {
            Text("Generar contraseña")
        }
    }
}

@Composable
fun ClearPasswordButton(
    viewModel: CreatePasswordViewModel,
    modifier: Modifier = Modifier
){
    Column (modifier = modifier.padding(top = 30.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Button(onClick = viewModel::clearPassword
        ) {
            Text("Resetear contraseña")
        }
    }
}

@SuppressLint("DefaultLocale")
@Composable
fun PasswordSection(password: String, viewModel: CreatePasswordViewModel, context: Context, snackFunction: (String)-> Unit){
    val passwordScore = viewModel.uiState.collectAsState().value.passwordScore

    Column (Modifier
        .padding(20.dp)
        .fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally){
        PasswordSectionText("Contraseña generada de forma satisfactoria")
        PasswordSectionText("Puntuación de la contraseña: ${String.format("%.2f", passwordScore)}")
        Box(Modifier.padding(20.dp)){
            Text(password)
        }
        CopyToClipboardButton(password, context, snackFunction)
    }
}

@Composable
fun ErrorMessage(error: String){
    Text(error, color= Color.Red, fontSize = 30.sp)
}

@Composable
fun PasswordSectionText(text: String){
    Text(text, modifier= Modifier.padding(vertical = 5.dp))
}

@Composable
fun AddPasswordButton(password: String, viewModel: CreatePasswordViewModel){
    Button(
        onClick = {viewModel.savePassword(password)},
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ){
        Text("Almacenar contraseña")
    }
}

@Composable
fun ApplicationSection(viewModel: CreatePasswordViewModel){
    val state = viewModel.uiState.collectAsState().value
    Column {
        Text("Información de aplicación")
        ApplicationOutlinedTextField("Nombre de aplicación", state.appName, viewModel::onAppNameChanged)
        ApplicationOutlinedTextField("Url de la aplicación", state.appUrl, viewModel::onAppUrlChanged)
        ApplicationOutlinedTextField("Cuenta", state.account, viewModel::onAccountChanged)
    }

}

@Composable
fun ApplicationOutlinedTextField(label: String, param: String, onValueChange: (String) -> Unit){
    OutlinedTextField(
        value = param,
        onValueChange = {onValueChange(it)},
        label = { Text(label) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
    )
}

@Composable
fun CopyToClipboardButton(passwordText: String, context: Context, snackFunction: (String)-> Unit){
    Column (horizontalAlignment = Alignment.CenterHorizontally) {
        Button(
            onClick = {
                val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText("Copied_Text", passwordText)
                clipboard.setPrimaryClip(clip)

                snackFunction("Contraseña copiada en el portapapeles")
            }
        ) {
            Row {
                Image(
                    painterResource(R.drawable.outline_content_copy_24),
                    contentDescription = "",
                )
                Text("Copiar al portapapeles")
            }
        }
    }
}