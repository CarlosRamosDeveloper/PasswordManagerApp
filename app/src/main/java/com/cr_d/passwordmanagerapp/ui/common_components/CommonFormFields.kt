package com.cr_d.passwordmanagerapp.ui.common_components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextObfuscationMode
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SecureTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

import com.cr_d.passwordmanagerapp.domain.entities.PasswordPolicy

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
fun DecimalFormField(value: Int, onValueChange: (Int) -> Unit) {
    val textValue = value.toString()
    Text("La longitud mínima de la nueva contraseña será de ${PasswordPolicy.MIN_GENERATED_LENGTH}")
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
        ),
    )
}

@Composable
fun PasswordTextField(isPasswordShown: Boolean, plainPassword: String, onUpdateMethod: (String) -> Unit){
    val state = rememberTextFieldState(initialText = plainPassword)

    LaunchedEffect(state) {
        snapshotFlow { state.text.toString() }.collect { newValue ->
            onUpdateMethod(newValue)
        }
    }

    SecureTextField(
        state = state,
        label = { Text("Contraseña") },
        textObfuscationMode =
            if (isPasswordShown) TextObfuscationMode.Visible
            else TextObfuscationMode.RevealLastTyped,
    )
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

@Composable
fun UnderFormSpacer(){
    Spacer(Modifier.size(20.dp))
}