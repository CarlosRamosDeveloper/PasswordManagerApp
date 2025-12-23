package com.cr_d.passwordmanagerapp.ui.screens.create_password

import android.annotation.SuppressLint
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.cr_d.passwordmanagerapp.domain.entities.SecurityScoreCalculator
import com.cr_d.passwordmanagerapp.ui.models.PasswordOption

@Composable
fun CreatePasswordScreen(innerPadding: PaddingValues, viewModel: CreatePasswordViewModel){
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

        if(state.generatedPassword.isNotBlank()) PasswordSection(state.generatedPassword, viewModel)
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
            keyboardType = KeyboardType.Number
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
fun PasswordSection(password: String, viewModel: CreatePasswordViewModel){
    val score = SecurityScoreCalculator(password)
    val formatedScore = String.format("%.2f",score.calculate())

    Column (Modifier
        .padding(20.dp)
        .fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally){
        PasswordSectionText("Contraseña generada de forma satisfactoria")
        PasswordSectionText("Puntuación de la contraseña: $formatedScore")
        Box(Modifier.padding(20.dp)){
            Text(password)
        }
        AddPasswordButton(password, viewModel)
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