package com.cr_d.passwordmanagerapp.ui.screens.password_detail.components

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.cr_d.passwordmanagerapp.domain.value_objects.PasswordData
import com.cr_d.passwordmanagerapp.ui.screens.password_detail.PasswordDetailViewModel
import com.cr_d.passwordmanagerapp.ui.screens.password_detail.horizontalFramePadding

@Composable
fun PasswordCard(
    password: PasswordData,
    viewModel: PasswordDetailViewModel,
    isPasswordShown: Boolean,
    context: Context,
    snackFunction: (String) -> Unit
){
    Card (modifier = Modifier.padding(vertical = 10.dp, horizontal = horizontalFramePadding)) {
        Column (modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp), horizontalAlignment = Alignment.CenterHorizontally){
            Text("Contraseña", fontSize = 25.sp)
            HorizontalDivider(thickness = 2.dp, modifier = Modifier.padding(vertical = 10.dp))
            ButtonsSection(
                password = password,
                viewModel = viewModel,
                context = context,
                snackFunction = snackFunction
            )
            if (isPasswordShown) Text(password.plainPassword.value)
            else Text("********")
        }
    }
}