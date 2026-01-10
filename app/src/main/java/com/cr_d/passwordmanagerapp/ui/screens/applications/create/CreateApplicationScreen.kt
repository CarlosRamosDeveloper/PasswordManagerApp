package com.cr_d.passwordmanagerapp.ui.screens.applications.create

import android.content.Context
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
import com.cr_d.passwordmanagerapp.ui.common_components.CustomOutlinedTextField

@Composable
fun CreateApplicationScreen (
    innerPadding: PaddingValues,
    viewModel: CreateApplicationViewModel,
    navController: NavController,
    context: Context,
    snackFunction: (String)-> Unit
){
    val state = viewModel.uiState.collectAsStateWithLifecycle().value

    Column(Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())
        .padding(innerPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CardTitle("Crear Aplicación")
        CustomOutlinedTextField(
            label = "Nombre de aplicación",
            param = state.application.applicationName,
            onValueChange = viewModel::onAppNameChange
        )
        CustomOutlinedTextField(
            label = "Url de la aplicación",
            param = state.application.applicationUrl,
            onValueChange = viewModel::onUrlChange
        )
        CustomOutlinedTextField(
            label = "Notas de aplicación",
            param = state.application.notes,
            onValueChange = viewModel::onNotesChange,
            isSingleLine = false
        )
    }
}