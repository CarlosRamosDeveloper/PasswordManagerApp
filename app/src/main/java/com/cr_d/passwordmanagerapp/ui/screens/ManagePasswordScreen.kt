package com.cr_d.passwordmanagerapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ManagePasswordScreen(innerPadding: PaddingValues){
    Column(Modifier.padding(innerPadding)) {
        Text("Gestionar Contraseñas")
    }
}