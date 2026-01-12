package com.cr_d.passwordmanagerapp.ui.screens.accounts.detail

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
import com.cr_d.passwordmanagerapp.ui.common_components.ConfirmDialog
import com.cr_d.passwordmanagerapp.ui.common_components.InfoCard
import com.cr_d.passwordmanagerapp.ui.common_components.SectionTitle
import com.cr_d.passwordmanagerapp.ui.model.PasswordUiState

@Composable
fun AccountDetailScreen(
    innerPadding: PaddingValues,
    snackFunction: (String)-> Unit,
    viewModel: AccountDetailViewModel,
    navController: NavController
){
    Column(modifier = Modifier.padding(innerPadding)) {
        AccountDetailCard(
            snackFunction = snackFunction,
            viewModel = viewModel,
            navController = navController
        )
    }
}

@Composable
fun AccountDetailCard(
    snackFunction: (String)-> Unit,
    viewModel: AccountDetailViewModel,
    navController: NavController
){
    val state = viewModel.uiState.collectAsStateWithLifecycle().value

    if (state.account != null) {
        InfoCard {
            CardTitle(state.account.account)
        }
        NotesSection(state.account.notes)
        PasswordSection(state.account.passwords)

        if(state.isDeleteDialogShown) ConfirmDialog(
            title = "Eliminar cuenta",
            message = "Esto eliminará la cuenta de forma permanente",
            confirmButtonText = "Eliminar cuenta",
            onConfirm = {
                viewModel.onDeleteAccount()
                snackFunction("Cuenta eliminada satisfactoriamente")
                navController.navigate("AccountListScreen")
            },
            onDisable = viewModel::onDisableDeleteDialog,
            onDismiss = viewModel::onDisableDeleteDialog
        )
    } else {
        CircularProgressIndicator()
    }
}

@Composable
fun NotesSection(notes: String){
    InfoCard {
        SectionTitle("Notes")
        Text(notes)
    }
}

@Composable
fun PasswordSection(passwords: List<PasswordUiState>){
    InfoCard {
        SectionTitle("Passwords")
        if(passwords.count()==0){
            Text("Esta cuenta no tiene contraseñas asociadas")
        } else {
            LazyColumn {
                items(passwords) { pwd ->
                    PasswordCard(pwd)
                }
            }
        }
    }
}

@Composable
fun PasswordCard(password: PasswordUiState){
    Text("Aplicación: ${password.appInfo.appName}")
}