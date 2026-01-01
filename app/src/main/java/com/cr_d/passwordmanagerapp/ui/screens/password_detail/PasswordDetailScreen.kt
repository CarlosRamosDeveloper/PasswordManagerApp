package com.cr_d.passwordmanagerapp.ui.screens.password_detail

import android.annotation.SuppressLint
import android.content.Context
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

import com.cr_d.passwordmanagerapp.ui.models.PasswordDetailUiMode
import com.cr_d.passwordmanagerapp.ui.screens.password_detail.composable_components.BasicMode
import com.cr_d.passwordmanagerapp.ui.screens.password_detail.composable_components.ConfirmDialogComponents
import com.cr_d.passwordmanagerapp.ui.screens.password_detail.composable_components.DetailedMode
import com.cr_d.passwordmanagerapp.ui.screens.password_detail.composable_components.EditMode
import com.cr_d.passwordmanagerapp.ui.screens.password_detail.composable_components.HeaderButtons
import com.cr_d.passwordmanagerapp.ui.screens.password_detail.composable_components.NotesSection
import com.cr_d.passwordmanagerapp.ui.screens.password_detail.composable_components.PasswordCard
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
                            haveChangedNotes = viewModel.checkIfNotesHasChanged(),
                            hasPasswordChanged = viewModel.checkIfPasswordHasChanged()
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

                ConfirmDialogComponents(
                    viewModel = viewModel,
                    dialogData = viewModel.getData(),
                    navController = navController,
                    context = context,
                    snackFunction = snackFunction
                )
            }
        }
    }
}

