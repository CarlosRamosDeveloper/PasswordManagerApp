package com.cr_d.passwordmanagerapp.ui.router

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import com.cr_d.passwordmanagerapp.ui.PasswordRepository
import com.cr_d.passwordmanagerapp.ui.screens.CreatePasswordScreen
import com.cr_d.passwordmanagerapp.ui.screens.MainScreen
import com.cr_d.passwordmanagerapp.ui.screens.ManagePasswordScreen
import com.cr_d.passwordmanagerapp.ui.screens.PasswordDataScreen
import com.cr_d.passwordmanagerapp.ui.screens.ShowPasswordsScreen

@Composable
fun Router(
    innerPadding: PaddingValues,
    navController: NavHostController,
    snackFunction: (String)-> Unit
){
    val context = LocalContext.current
    NavHost(navController = navController, startDestination = "PasswordDataScreen") {
        composable("MainScreen") {
            MainScreen(innerPadding, navController)
        }
        composable("CreatePasswordScreen") {
            CreatePasswordScreen(innerPadding)
        }
        composable("ShowPasswordScreen") {
            ShowPasswordsScreen(innerPadding, context, snackFunction)
        }
        composable("ManagePasswordScreen") {
            ManagePasswordScreen(innerPadding)
        }
        composable("PasswordDataScreen") {
            PasswordDataScreen(innerPadding, context, snackFunction, PasswordRepository.passwords[0])
        }
    }
}