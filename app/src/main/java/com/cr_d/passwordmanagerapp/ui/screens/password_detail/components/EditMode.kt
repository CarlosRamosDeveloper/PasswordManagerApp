package com.cr_d.passwordmanagerapp.ui.screens.password_detail.components

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState

import com.cr_d.passwordmanagerapp.ui.screens.create_password.ApplicationOutlinedTextField
import com.cr_d.passwordmanagerapp.ui.screens.password_detail.PasswordDetailViewModel

@Composable
fun EditMode(viewModel: PasswordDetailViewModel, snackFunction: (String)-> Unit){
    val state = viewModel.uiState.collectAsState().value

    Column() {
        ApplicationOutlinedTextField("Aplicacion", state.newAppName, viewModel::onAppNameChanged)
        ApplicationOutlinedTextField("Url", state.newUrl, viewModel::onUrlChanged)
        ApplicationOutlinedTextField("Cuenta", state.newAccount, viewModel::onAccountChanged)
        ApplicationOutlinedTextField("Contraseña", state.newPlainPassword.value, viewModel::onPlainPasswordChange)
        UpdatePasswordButton(viewModel, snackFunction)
    }
}