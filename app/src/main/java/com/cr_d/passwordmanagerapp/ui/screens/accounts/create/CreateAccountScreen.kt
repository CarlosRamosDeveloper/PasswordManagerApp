package com.cr_d.passwordmanagerapp.ui.screens.accounts.create

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController

import com.cr_d.passwordmanagerapp.ui.common_components.CardTitle
import com.cr_d.passwordmanagerapp.ui.common_components.ConfirmDialog
import com.cr_d.passwordmanagerapp.ui.common_components.CustomOutlinedTextField


@Composable
fun CreateAccountScreen (
    innerPadding: PaddingValues,
    viewModel: CreateAccountViewModel,
    navController: NavController,
    snackFunction: (String)-> Unit
){
    val state = viewModel.uiState.collectAsStateWithLifecycle().value

    Column(Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())
        .padding(innerPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CardTitle("Crear Cuenta")
        CustomOutlinedTextField(
            label = "Nombre de cuenta",
            param = state.account.account,
            onValueChange = viewModel::onAccountNameChange
        )
        CustomOutlinedTextField(
            label = "Notas de cuenta",
            param = state.account.notes,
            onValueChange = viewModel::onAccountNotesChange,
            isSingleLine = false
        )
    }

    if (state.isSaveDialogShown) ConfirmDialog(
        title = "Guardar cuenta",
        message = "¿Guardar cuenta en la base de datos?",
        confirmButtonText = "Guardar cuenta",
        onConfirm = {
            viewModel.onSaveAccount()
            snackFunction("Cuenta almacenada con éxito")
        },
        onDisable = viewModel::onDisableSaveDialog,
        onDismiss = viewModel::onDisableSaveDialog
    )
}