package com.cr_d.passwordmanagerapp.ui.screens.password_detail.components

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.RectangleShape

@Composable
fun ModeButton(label: String, onclick: () -> Unit){
    Button(
        onClick = { onclick() },
        shape = RectangleShape
    ) {
        Text(label)
    }
}