package com.cr_d.passwordmanagerapp.ui.screens

import android.annotation.SuppressLint
import android.util.Log
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.cr_d.passwordmanagerapp.application.interfaces.IPasswordRepository
import com.cr_d.passwordmanagerapp.domain.entities.PasswordGenerator
import com.cr_d.passwordmanagerapp.domain.entities.PasswordPolicy
import com.cr_d.passwordmanagerapp.domain.entities.SecurityScoreCalculator
import com.cr_d.passwordmanagerapp.domain.value_objects.PasswordData
import com.cr_d.passwordmanagerapp.domain.value_objects.PasswordDataGeneration

@Composable
fun CreatePasswordScreen(innerPadding: PaddingValues, repo: IPasswordRepository){
    var hasLowerCase by remember { mutableStateOf(false) }
    var hasUpperCase by remember { mutableStateOf(false) }
    var hasNumbers by remember { mutableStateOf(false) }
    var hasSpecials by remember { mutableStateOf(false) }
    var passwordLength by remember { mutableIntStateOf(PasswordPolicy.MIN_GENERATED_LENGTH) }
    var passwordError by remember { mutableStateOf("") }
    var generatedPassword by remember { mutableStateOf("") }

    Column(Modifier
        .fillMaxSize()
        .padding(innerPadding), horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Crear contraseña")
        Row (modifier= Modifier.fillMaxWidth()){
            CustomCheckbox("Minúsculas", hasLowerCase, { hasLowerCase=it }, Modifier.weight(1f) )
            CustomCheckbox("Mayúsculas", hasUpperCase, { hasUpperCase=it }, Modifier.weight(1f) )
        }
        Row{
            CustomCheckbox("Números", hasNumbers, { hasNumbers=it }, Modifier.weight(1f) )
            CustomCheckbox("Carácteres especiales", hasSpecials, { hasSpecials=it }, Modifier.weight(1f) )
        }

        CampoDecimal(passwordLength) { passwordLength=it }

        PasswordButtonsSection(hasLowerCase, hasUpperCase, hasNumbers, hasSpecials, passwordLength, { passwordError=it}, {generatedPassword=it})

        if(passwordError != "") ErrorMessage(passwordError)

        if(generatedPassword != "") PasswordSection(generatedPassword, {generatedPassword=it}, repo)
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
    hasLowerCase: Boolean,
    hasUpperCase: Boolean,
    hasNumbers: Boolean,
    hasSpecials: Boolean,
    passwordLength: Int,
    onErrorChange: (String) -> Unit,
    generatedPassword: (String) -> Unit,
){
    Row(Modifier.fillMaxWidth()){
        CreatePasswordButton(hasLowerCase, hasUpperCase, hasNumbers, hasSpecials, passwordLength, onErrorChange, generatedPassword, Modifier.weight(1f))
        ClearPasswordButton(onErrorChange, generatedPassword, Modifier.weight(1f))
    }
}

@Composable
fun CreatePasswordButton(
    hasLowerCase: Boolean,
    hasUpperCase: Boolean,
    hasNumbers: Boolean,
    hasSpecials: Boolean,
    passwordLength: Int,
    onErrorChange: (String) -> Unit,
    generatedPassword: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val passwordDataGeneration = PasswordDataGeneration(hasLowerCase, hasUpperCase, hasNumbers, hasSpecials, passwordLength)
    val generator = PasswordGenerator(passwordDataGeneration)

    Column (modifier = modifier.padding(top = 30.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Button(onClick = {
            try {
                val password = generator.generatePassword()
                generatedPassword(password)
                onErrorChange("")
            } catch (e: Exception) {
                Log.d("Error", e.message.toString())
                onErrorChange(e.message.toString())
                generatedPassword("")
            }
        }) {
            Text("Generar contraseña")
        }
    }
}

@Composable
fun ClearPasswordButton(
    onErrorChange: (String) -> Unit,

    generatedPassword: (String) -> Unit,
    modifier: Modifier = Modifier
){
    Column (modifier = modifier.padding(top = 30.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Button(onClick = {
            try {
                generatedPassword("")
                onErrorChange("")

            } catch (e: Exception) {
                Log.d("Error", e.message.toString())
                onErrorChange(e.message.toString())
            }
        }) {
            Text("Resetear contraseña")
        }
    }
}

@SuppressLint("DefaultLocale")
@Composable
fun PasswordSection(password: String, generatedPassword:(String) -> Unit, repo: IPasswordRepository){
    val score = SecurityScoreCalculator(password)
    val formatedScore = String.format("%.2f",score.calculate())

    Column (Modifier.padding(20.dp).fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally){
        PasswordSectionText("Contraseña generada de forma satisfactoria")
        PasswordSectionText("Puntuación de la contraseña: $formatedScore")
        Box(Modifier.padding(20.dp)){
            Text(password)
        }
        AddPasswordButton(password, generatedPassword, repo)
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
fun AddPasswordButton(password: String, generatedPassword:(String) -> Unit, repo: IPasswordRepository){
    Button(
        onClick = {
            val newPassword =
                PasswordData(
                    0,
                    "test",
                    password,
                    false,
                    false,
                    false,
                    false,
                    "test",
                    "test",
                    "Ahora",
                    "Despues",
                    9.1
                    )
            repo.save(newPassword)
            generatedPassword("")
        },
        modifier = Modifier.fillMaxWidth().padding(20.dp)
    ){
        Text("Almacenar contraseña")
    }
}