package com.cr_d.passwordmanagerapp.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType

import com.cr_d.passwordmanagerapp.domain.entities.PasswordGenerator
import com.cr_d.passwordmanagerapp.domain.value_objects.PasswordDataGeneration

@Composable
fun CreatePasswordScreen(innerPadding: PaddingValues){
    var hasLowerCase by remember { mutableStateOf(false) }
    var hasUpperCase by remember { mutableStateOf(false) }
    var hasNumbers by remember { mutableStateOf(false) }
    var hasSpecials by remember { mutableStateOf(false) }
    var passwordLength by remember { mutableStateOf(0) }

    Column(Modifier
        .fillMaxSize()
        .padding(innerPadding), horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Crear contraseña")
        CustomCheckbox("Minúsculas", hasLowerCase, { hasLowerCase=it })
        CustomCheckbox("Mayúsculas", hasUpperCase, { hasUpperCase=it })
        CustomCheckbox("Números", hasNumbers, { hasNumbers=it })
        CustomCheckbox("Carácteres especiales", hasSpecials, { hasSpecials=it })
        CampoDecimal(passwordLength) { passwordLength=it }

        CreatePasswordButton(hasLowerCase, hasUpperCase, hasNumbers, hasSpecials, passwordLength)
    }
}

@Composable
fun CustomCheckbox(labelText: String, value: Boolean, onValueChange: (Boolean) -> Unit ) {
    Row(
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
fun CreatePasswordButton(
    hasLowerCase: Boolean,
    hasUpperCase: Boolean,
    hasNumbers: Boolean,
    hasSpecials: Boolean,
    passwordLength: Int
) {
    val passwordDataGeneration = PasswordDataGeneration(hasLowerCase, hasUpperCase, hasNumbers, hasSpecials, passwordLength)
    val generator = PasswordGenerator(passwordDataGeneration)

    Column {
        Button(onClick = {
            val password = generator.generatePassword()
            Log.d("Password", password)
        }) {
            Text("Generar contraseña")
        }
    }

}
