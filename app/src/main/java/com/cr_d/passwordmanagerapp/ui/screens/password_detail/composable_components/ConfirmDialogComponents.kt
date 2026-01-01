package com.cr_d.passwordmanagerapp.ui.screens.password_detail.composable_components

import android.content.ClipData
import android.content.ClipDescription
import android.content.ClipboardManager
import android.content.Context
import android.os.PersistableBundle
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

import com.cr_d.passwordmanagerapp.ui.common_components.ConfirmDialog
import com.cr_d.passwordmanagerapp.ui.models.PasswordConfirmDialogData
import com.cr_d.passwordmanagerapp.ui.screens.password_detail.PasswordDetailViewModel

@Composable
fun ConfirmDialogComponents(
    viewModel: PasswordDetailViewModel,
    dialogData: PasswordConfirmDialogData,
    navController: NavController,
    context: Context,
    snackFunction: (String)-> Unit,
    ){

    if (dialogData.isDeletePasswordDialogShown) ConfirmDialog(
        title = "Eliminar contraseña",
        message = "Este paso no se puede deshacer, ¿está seguro?",
        confirmButtonText = "Eliminar contraseña",
        onConfirm = {
            viewModel.onDeletePassword()
            snackFunction("Contraseña eliminada")
            navController.navigate("ShowPasswordScreen")
        },
        onDisable = viewModel::onDisableDeletePasswordDialog,
        onDismiss = viewModel::onDisableDeletePasswordDialog
    )

    if (dialogData.isCopyToDialogShown)  ConfirmDialog(
        title = "Copiar contraseña",
        message = "La información en el portapapeles no está cifrada, se sugiere extremar precauciones",
        confirmButtonText = "Copiar en el portapapeles",
        onConfirm = {
            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("Copied_Text", viewModel.decipherPassword()).apply {
                description.extras = PersistableBundle().apply {
                    putBoolean(ClipDescription.EXTRA_IS_SENSITIVE, true)
                }
            }
            clipboard.setPrimaryClip(clip)
            snackFunction("Contraseña copiada en el portapapeles")
            viewModel.onDisableCopyDialog()
        },
        onDisable = viewModel::onDisableCopyDialog,
        onDismiss = viewModel::onDisableCopyDialog
    )

    if(dialogData.isUpdatePasswordDialogShown) ConfirmDialog(
        title = "Actualizar contraseña",
        message = "Esta acción actualizará la contraseña de forma permanente",

        confirmButtonText = "Actualizar contraseña",
        onConfirm = {
            viewModel.onUpdatePassword()
            snackFunction("Contraseña actualizada correctamente")
            viewModel.onDisableUpdateDialog()
        },
        onDisable = viewModel::onDisableUpdateDialog,
        onDismiss = {
            snackFunction("La contraseña no ha cambiado")
            viewModel.onDisableUpdateDialog()
        }
    )

    if(dialogData.isUpdateNotesDialogShown) ConfirmDialog(
        title = "Actualización de notas",
        message = "¿Quiere actualizar las notas?",
        confirmButtonText = "Actualizar notas",
        onConfirm = {
            viewModel.onUpdateNotes()
            snackFunction("Notas actualizadas")
            viewModel.onDisableUpdateNotesDialog()
        },
        onDisable = viewModel::onDisableUpdateNotesDialog,
        onDismiss = viewModel::onDisableUpdateNotesDialog
    )

    if(dialogData.isDeleteNotesDialogShown) ConfirmDialog(
        title = "Eliminar notas",
        message = "¿Eliminar todas las notas de esta contraseña?",
        confirmButtonText = "Eliminar",
        onConfirm = {
            viewModel.onDeleteNotes()
            viewModel.onUpdateNotes()
            snackFunction("Notas eliminadas satisfactoriamente")
            viewModel.onDisableDeleteNotesDialog()
        },
        onDisable = viewModel::onDisableDeleteNotesDialog,
        onDismiss = viewModel::onDisableDeleteNotesDialog
    )
}