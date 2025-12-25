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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController

import com.cr_d.passwordmanagerapp.ui.models.PasswordDetailUiMode
import com.cr_d.passwordmanagerapp.ui.screens.password_detail.components.BasicMode
import com.cr_d.passwordmanagerapp.ui.screens.password_detail.components.DetailedMode
import com.cr_d.passwordmanagerapp.ui.screens.password_detail.components.EditMode
import com.cr_d.passwordmanagerapp.ui.screens.password_detail.components.HeaderButtons
import com.cr_d.passwordmanagerapp.ui.screens.password_detail.components.PasswordCard
import com.cr_d.passwordmanagerapp.ui.screens.settings.SettingsViewModel

val horizontalFramePadding = 20.dp

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
        HeaderButtons(viewModel, snackFunction, navController)
        PasswordDetailedCard(context, snackFunction, viewModel, settings)
    }
}

@SuppressLint("DefaultLocale")
@Composable
fun PasswordDetailedCard(
    context: Context,
    snackFunction: (String)-> Unit,
    viewModel: PasswordDetailViewModel,
    settings: SettingsViewModel,
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
                    PasswordDetailUiMode.FULL_INFO_MODE -> {
                        DetailedMode(
                            password = state.password,
                            settings = settings,
                        )
                    }
                    PasswordDetailUiMode.EDIT_MODE -> {
                        EditMode(
                            viewModel = viewModel,
                            snackFunction = snackFunction
                        )
                    }

                    PasswordDetailUiMode.BASIC_INFO_MODE -> {
                        BasicMode(
                            password = state.password,
                            settings = settings,
                        )
                    }
                    else -> {

                    }
                }
                if (state.mode!= PasswordDetailUiMode.EDIT_MODE) PasswordCard(
                    password = state.password,
                    viewModel = viewModel,
                    isPasswordShown = state.isPasswordShown,
                    context = context,
                    snackFunction = snackFunction
                )
            }

        }
    }
}

