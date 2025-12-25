package com.cr_d.passwordmanagerapp.ui.common_components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType

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