package com.cr_d.passwordmanagerapp.ui.screens.password_detail.components

import androidx.compose.runtime.Composable

import com.cr_d.passwordmanagerapp.ui.common_components.ApplicationOutlinedTextField
import com.cr_d.passwordmanagerapp.ui.screens.password_detail.PasswordDetailViewModel

@Composable
fun EditMode(passwordState: PasswordDetailViewModel.UiState, viewModel: PasswordDetailViewModel, snackFunction: (String)-> Unit){

    InfoCard {
        ApplicationOutlinedTextField("Aplicacion", passwordState.newAppName, viewModel::onAppNameChanged)
        ApplicationOutlinedTextField("Url", passwordState.newUrl, viewModel::onUrlChanged)
        ApplicationOutlinedTextField("Cuenta", passwordState.newAccount, viewModel::onAccountChanged)
        ApplicationOutlinedTextField("Contraseña", passwordState.newPlainPassword.value, viewModel::onPlainPasswordChange)
        UpdatePasswordButton(viewModel, snackFunction)
    }
}