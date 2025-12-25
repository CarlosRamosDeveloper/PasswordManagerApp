package com.cr_d.passwordmanagerapp.ui.screens.password_detail.components

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

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
            CardTitle("Contraseña")
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

@Composable
fun ButtonsSection(
    password: PasswordData?,
    viewModel: PasswordDetailViewModel,
    context: Context,
    snackFunction: (String)-> Unit,
){
    if (password == null) return
    Row (Modifier
        .fillMaxWidth()
        .padding(bottom = 5.dp), horizontalArrangement = Arrangement.SpaceAround){
        TogglePasswordVisibilityButton(viewModel)
        CopyToClipboardButton(password.plainPassword.value, context, snackFunction)
    }
}