package com.cr_d.passwordmanagerapp.ui.screens.main_screen

import androidx.compose.runtime.Composable

import com.cr_d.passwordmanagerapp.ui.common_components.ConfirmDialog

@Composable
fun MainConfirmDialogs(
    isPopulateDatabasePasswordDialogShown: Boolean,
    isMassDeletePasswordDialogShown: Boolean,
    isPopulateDatabaseAccountDialogShown: Boolean,
    isMassDeleteAccountDialogShown: Boolean,
    viewModel: MainScreenViewModel
){
    if(isPopulateDatabasePasswordDialogShown) ConfirmDialog(
        title = "Generar contraseñas de prueba",
        message = "¿Inyectar información de prueba en la base de datos?",
        confirmButtonText = "Poblar la base de datos",
        onConfirm = {
            viewModel.onPopulatePasswords()
            viewModel.onDisablePopulatePasswordDatabaseDialog()
        },
        onDisable = viewModel::onDisablePopulatePasswordDatabaseDialog,
        onDismiss = viewModel::onDisablePopulatePasswordDatabaseDialog
    )

    if(isMassDeletePasswordDialogShown) ConfirmDialog(
        title = "Eliminar todas las contraseñas",
        message = "Este paso no se puede deshacer, ¿está seguro?",
        confirmButtonText = "Eliminar todas las contraseñas",
        onConfirm = {
            viewModel.onMassDeletePasswords()
            viewModel.onDisableMassDeletePasswordDialog()
        },
        onDisable = viewModel::onDisableMassDeletePasswordDialog,
        onDismiss = viewModel::onDisableMassDeletePasswordDialog
    )

    if(isPopulateDatabaseAccountDialogShown) ConfirmDialog(
        title = "Generar cuentas de prueba",
        message = "¿Inyectar información de prueba en la base de datos?",
        confirmButtonText = "Poblar la base de datos",
        onConfirm = {
            viewModel.onPopulateAccounts()
            viewModel.onDisablePopulateAccountDatabaseDialog()
        },
        onDisable = viewModel::onDisablePopulateAccountDatabaseDialog,
        onDismiss = viewModel::onDisablePopulateAccountDatabaseDialog
    )
    if(isMassDeleteAccountDialogShown) ConfirmDialog(
        title = "Eliminar todas las cuentas",
        message = "Este paso no se puede deshacer, ¿está seguro?",
        confirmButtonText = "Eliminar todas las cuentas",
        onConfirm = {
            viewModel.onMassDeleteAccounts()
            viewModel.onDisableMassDeleteAccountDialog()
        },
        onDisable = viewModel::onDisableMassDeleteAccountDialog,
        onDismiss = viewModel::onDisableMassDeleteAccountDialog
    )
}