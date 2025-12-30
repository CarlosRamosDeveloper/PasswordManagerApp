package com.cr_d.passwordmanagerapp.ui.common_components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmDialog(
    title: String,
    message: String,
    confirmButtonText: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    onDisable: () -> Unit,
    dismissButtonText: String = "Atrás"
){
    AlertDialog(
        onDismissRequest = onDisable,
        title = { Text(text = title) },
        text = { Text(text = message) },
        confirmButton =
            { TextButton(onClick = onConfirm)  { Text(confirmButtonText) } },
        dismissButton =
            { TextButton(onClick = onDismiss)  { Text(dismissButtonText) } }
    )
}