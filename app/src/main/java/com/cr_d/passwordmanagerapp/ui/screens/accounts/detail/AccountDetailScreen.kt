package com.cr_d.passwordmanagerapp.ui.screens.accounts.detail

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController

import com.cr_d.passwordmanagerapp.ui.common_components.CardTitle
import com.cr_d.passwordmanagerapp.ui.common_components.InfoCard
import com.cr_d.passwordmanagerapp.ui.common_components.SectionTitle

@Composable
fun AccountDetailScreen(
    innerPadding: PaddingValues,
    context: Context,
    snackFunction: (String)-> Unit,
    viewModel: AccountDetailViewModel,
    navController: NavController
){
    Column(modifier = Modifier.padding(innerPadding)) {
        AccountDetailCard(
            context = context,
            snackFunction = snackFunction,
            viewModel = viewModel,
            navController = navController
        )
    }
}

@Composable
fun AccountDetailCard(
    context: Context,
    snackFunction: (String)-> Unit,
    viewModel: AccountDetailViewModel,
    navController: NavController
){
    val state = viewModel.uiState.collectAsStateWithLifecycle().value

    Log.d("AccountDetail", "Card ${state.account}")
    if (state.account != null) {
        InfoCard {
            CardTitle(state.account.account)
        }
        NotesSection("asd")
        PasswordSection(state.account.totalApplications)
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
fun PasswordSection(totalPasswords: Int){
    InfoCard {
        SectionTitle("Passwords")
        if(totalPasswords==0){
            Text("Esta cuenta no tiene contraseñas asociadas")
        } else {
            Text("Esta cuenta tiene $totalPasswords contraseñas asociadas")
        }
    }
}