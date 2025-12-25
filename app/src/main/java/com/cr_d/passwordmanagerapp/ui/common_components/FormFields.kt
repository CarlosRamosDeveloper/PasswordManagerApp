package com.cr_d.passwordmanagerapp.ui.common_components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.input.ImeAction

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