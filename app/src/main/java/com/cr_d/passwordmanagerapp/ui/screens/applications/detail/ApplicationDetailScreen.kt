package com.cr_d.passwordmanagerapp.ui.screens.applications.detail

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController

import com.cr_d.passwordmanagerapp.ui.common_components.CardTitle
import com.cr_d.passwordmanagerapp.ui.common_components.InfoCard
import com.cr_d.passwordmanagerapp.ui.model.PasswordUiState
import com.cr_d.passwordmanagerapp.ui.screens.accounts.detail.NotesSection

@Composable
fun ApplicationDetailScreen(
    innerPadding: PaddingValues,
    context: Context,
    snackFunction: (String)-> Unit,
    viewModel: ApplicationDetailViewModel,
    navController: NavController
){
    Column (modifier = Modifier.padding(innerPadding)){
        ApplicationDetailCard(
            context = context,
            snackFunction = snackFunction,
            viewModel = viewModel,
            navController = navController
        )
    }
}

@Composable
fun ApplicationDetailCard(
    context: Context,
    snackFunction: (String)-> Unit,
    viewModel: ApplicationDetailViewModel,
    navController: NavController
){
    val state = viewModel.uiState.collectAsStateWithLifecycle().value

    if (state.application != null) {
        InfoCard {
            CardTitle(state.application.applicationName)
        }
        NotesSection(state.application.notes)
        AccountsSection(state.application.passwords)
    } else {
        CircularProgressIndicator()
    }
}

@Composable
fun AccountsSection(passwords: List<PasswordUiState>){
    LazyColumn {
        items(passwords) { pwd ->
            AccountItem(pwd)
        }
    }
}

@Composable
fun AccountItem(password: PasswordUiState){
    InfoCard {
        Text("Cuenta: ${password.appInfo.appAccount}")
    }
}