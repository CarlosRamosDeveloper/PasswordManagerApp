package com.cr_d.passwordmanagerapp.ui.screens.main_screen

import androidx.compose.runtime.Composable

import com.cr_d.passwordmanagerapp.ui.common_components.ConfirmDialog
import com.cr_d.passwordmanagerapp.ui.model.MainConfirmDialogData

@Composable
fun MainConfirmDialogs(
    dialogData: MainConfirmDialogData,
    viewModel: MainScreenViewModel
){
    if(dialogData.isPasswordPopulateDatabaseDialogShown) ConfirmDialog(
        title = "Generar contraseñas de prueba",
        message = "¿Inyectar información de prueba en la base de datos?",
        confirmButtonText = "Poblar la base de datos",
        onConfirm = {
            viewModel.onPopulatePasswordOrchestra()
            viewModel.onDisablePopulatePasswordDatabaseDialog()
        },
        onDisable = viewModel::onDisablePopulatePasswordDatabaseDialog,
        onDismiss = viewModel::onDisablePopulatePasswordDatabaseDialog
    )

    if(dialogData.isPasswordMassDeleteDialogShown) ConfirmDialog(
        title = "Eliminar todas las contraseñas",
        message = "Este paso no se puede deshacer, ¿está seguro?",
        confirmButtonText = "Eliminar",
        onConfirm = {
            viewModel.onMassDeletePasswordOrchestra()
            viewModel.onDisableMassDeletePasswordDialog()
        },
        onDisable = viewModel::onDisableMassDeletePasswordDialog,
        onDismiss = viewModel::onDisableMassDeletePasswordDialog
    )

    if(dialogData.isAccountsPopulateDatabaseDialogShown) ConfirmDialog(
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
    if(dialogData.isAccountsMassDeleteDialogShown) ConfirmDialog(
        title = "Eliminar todas las cuentas",
        message = "Este paso no se puede deshacer, ¿está seguro?",
        confirmButtonText = "Eliminar",
        onConfirm = {
            viewModel.onMassDeleteAccountOrchestra()
            viewModel.onDisableMassDeleteAccountDialog()
        },
        onDisable = viewModel::onDisableMassDeleteAccountDialog,
        onDismiss = viewModel::onDisableMassDeleteAccountDialog
    )
    if(dialogData.isApplicationPopulateDatabaseDialogShown) ConfirmDialog(
        title = "Generación de aplicaciones de prueba",
        message = "¿Inyectar información de prueba en la base de datos?",
        confirmButtonText = "Poblar la base de datos",
        onConfirm = {
            viewModel.onPopulateApps()
            viewModel.onDisablePopulateApplicationDatabaseDialog()
        },
        onDisable = viewModel::onDisablePopulateApplicationDatabaseDialog,
        onDismiss = viewModel::onDisablePopulateApplicationDatabaseDialog
    )
    if(dialogData.isApplicationMassDeleteDialogShown) ConfirmDialog(
        title = "Eliminar todas las aplicaciones",
        message = "Este paso no se puede deshacer, ¿está seguro?",
        confirmButtonText = "Eliminar",
        onConfirm = {
            viewModel.onMassDeleteApplicationOrchestra()
            viewModel.onDisableMassDeleteApplicationDialog()
        },
        onDisable = viewModel::onDisableMassDeleteApplicationDialog,
        onDismiss = viewModel::onDisableMassDeleteApplicationDialog
    )
}