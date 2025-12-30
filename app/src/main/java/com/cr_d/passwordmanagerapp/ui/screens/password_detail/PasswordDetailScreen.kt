package com.cr_d.passwordmanagerapp.ui.screens.password_detail

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipDescription
import android.content.ClipboardManager
import android.content.Context
import android.os.PersistableBundle
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController

import com.cr_d.passwordmanagerapp.ui.common_components.ConfirmDialog
import com.cr_d.passwordmanagerapp.ui.models.PasswordDetailUiMode
import com.cr_d.passwordmanagerapp.ui.screens.password_detail.components.BasicMode
import com.cr_d.passwordmanagerapp.ui.screens.password_detail.components.DetailedMode
import com.cr_d.passwordmanagerapp.ui.screens.password_detail.components.EditMode
import com.cr_d.passwordmanagerapp.ui.screens.password_detail.components.HeaderButtons
import com.cr_d.passwordmanagerapp.ui.screens.password_detail.components.NotesSection
import com.cr_d.passwordmanagerapp.ui.screens.password_detail.components.PasswordCard
import com.cr_d.passwordmanagerapp.ui.screens.settings.SettingsViewModel

@Composable
fun PasswordDetailScreen(
    innerPadding: PaddingValues,
    context: Context,
    snackFunction: (String)-> Unit,
    viewModel: PasswordDetailViewModel,
    settings: SettingsViewModel,
    navController: NavController
){
    Column (modifier = Modifier.padding(innerPadding)){
        HeaderButtons(viewModel)
        PasswordDetailedCard(context, snackFunction, viewModel, settings, navController)
    }
}

@SuppressLint("DefaultLocale")
@Composable
fun PasswordDetailedCard(
    context: Context,
    snackFunction: (String)-> Unit,
    viewModel: PasswordDetailViewModel,
    settings: SettingsViewModel,
    navController: NavController
){
    val state = viewModel.uiState.collectAsStateWithLifecycle().value
    val settings = settings.settings.collectAsStateWithLifecycle().value

    when {
        state.password == null -> {
            CircularProgressIndicator()
        }
        else -> {
            Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
            ) {
                when(state.mode) {
                    PasswordDetailUiMode.BASIC_INFO_MODE -> {
                        BasicMode(
                            password = state.password,
                            settings = settings
                        )
                    }

                    PasswordDetailUiMode.FULL_INFO_MODE -> {
                        DetailedMode(
                            password = state.password,
                            settings = settings,
                        )
                    }

                    PasswordDetailUiMode.EDIT_MODE -> {
                        EditMode(
                            newPlainPassword = state.newPassword,
                            isPasswordShown = state.isPasswordShown,
                            isGeneratePasswordEnabled = state.isGeneratePasswordEnabled,
                            passwordState = state.editInfo,
                            viewModel = viewModel,
                            passwordError = state.errorMessage,
                            notes = state.newNotes,
                            haveChangedNotes = viewModel.checkIfNotesHasChanged()
                        )
                    }
                }
                if (state.mode != PasswordDetailUiMode.EDIT_MODE) NotesSection(
                    state.decipheredNotes
                )
                if (state.mode!= PasswordDetailUiMode.EDIT_MODE) PasswordCard(
                    passwordPlainText = state.decipheredPassword,
                    onVisibilityToggleFunction = viewModel::onPasswordVisibilityToggle,
                    isPasswordShown = state.isPasswordShown,
                    copyToClipboardFunction = viewModel::onEnableCopyDialog
                )

                if (state.isDeleteDialogShown) ConfirmDialog(
                    title = "Eliminar contraseña",
                    message = "Este paso no se puede deshacer, ¿está seguro?",
                    confirmButtonText = "Eliminar contraseña",
                    onConfirm = {
                        viewModel.onDeletePassword()
                        snackFunction("Contraseña eliminada")
                        navController.navigate("ShowPasswordScreen")
                                },
                    onDisable = viewModel::onDisableDeleteDialog,
                    onDismiss = viewModel::onDisableDeleteDialog
                )

                if (state.isCopyToDialogShown)  ConfirmDialog(
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

                if(state.isUpdatePasswordDialogShown) ConfirmDialog(
                    title = "Actualizar contraseña",
                    message =
                        if (viewModel.checkIfPasswordHasChanged()) "Esta acción actualizará la contraseña de forma permanente"
                        else "Atención, la contraseña no ha cambiado",
                    confirmButtonText =
                        if (viewModel.checkIfPasswordHasChanged())"Actualizar contraseña"
                        else "Atrás",
                    onConfirm = {
                        if (viewModel.checkIfPasswordHasChanged()) {
                            viewModel.onUpdatePassword()
                            snackFunction("Contraseña actualizada correctamente")
                        } else {
                            snackFunction("La contraseña no ha cambiado")
                        }
                        viewModel.onDisableUpdateDialog()
                    },
                    onDisable = viewModel::onDisableUpdateDialog,
                    onDismiss = {
                        snackFunction("La contraseña no ha cambiado")
                        viewModel.onDisableUpdateDialog()
                    }
                )

                if(state.isUpdateNotesDialogShown) ConfirmDialog(
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
            }

        }
    }
}

