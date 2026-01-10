package com.cr_d.passwordmanagerapp.ui.screens.accounts.detail

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun AccountDetailScreen(
    innerPadding: PaddingValues,
    context: Context,
    snackFunction: (String)-> Unit,
    viewModel: AccountDetailViewModel,
    navController: NavController
){
    Column(modifier = Modifier.padding(innerPadding)) {

    }
}

@Composable
fun AccountDetailCard(
    context: Context,
    snackFunction: (String)-> Unit,
    viewModel: AccountDetailViewModel,
    navController: NavController
){
    // Nombre de la cuenta
    // Listado de aplicaciones en las que se tiene contraseña
}