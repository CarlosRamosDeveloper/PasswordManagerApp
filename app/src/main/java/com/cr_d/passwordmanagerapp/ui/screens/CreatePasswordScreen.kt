package com.cr_d.passwordmanagerapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

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
        //CampoDecimal(passwordLength) { passwordLength=it }
    }
}
/*
@Composable
fun CampoDecimal(value: Int, onValueChange: (Int) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = { newValue ->
            // Permite solo números y un punto
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
 */

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
/*
@Composable
fun CampoDecimal(value: Int, onValueChange: (Int) -> Unit) {
    // Convertimos el Int a String para mostrarlo en el TextField
    val textValue = value.toString()

    OutlinedTextField(
        value = textValue,
        onValueChange = { newText ->
            // Acepta solo números (sin punto si quieres Int)
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
*/