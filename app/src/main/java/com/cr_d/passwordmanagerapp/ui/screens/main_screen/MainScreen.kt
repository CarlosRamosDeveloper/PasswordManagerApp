package com.cr_d.passwordmanagerapp.ui.screens.main_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle

import com.cr_d.passwordmanagerapp.ui.common_components.FullWidthButton

@Composable
fun MainScreen(innerPadding: PaddingValues, viewModel: MainScreenViewModel){
    val state = viewModel.uiState.collectAsStateWithLifecycle().value

    Column(
        modifier= Modifier.padding(innerPadding).fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Placeholder total")
        Text("Filtros")
        Text("Cantidad warnings")
        Text("Total de contraseñas: ${state.totalPasswords}")
        FullWidthButton("Mass populate", viewModel::onPopulate)
        FullWidthButton("Mass delete", viewModel::onMassDelete)
    }
}
