package com.cr_d.passwordmanagerapp.ui.router

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import com.cr_d.passwordmanagerapp.application.interfaces.IPasswordRepository
import com.cr_d.passwordmanagerapp.ui.screens.CreatePasswordScreen
import com.cr_d.passwordmanagerapp.ui.screens.MainScreen
import com.cr_d.passwordmanagerapp.ui.screens.ManagePasswordScreen
import com.cr_d.passwordmanagerapp.ui.screens.PasswordDataScreen
import com.cr_d.passwordmanagerapp.ui.screens.ShowPasswordsScreen

@Composable
fun Router(
    innerPadding: PaddingValues,
    navController: NavHostController,
    snackFunction: (String)-> Unit,
    repo: IPasswordRepository
){

    val context = LocalContext.current
    NavHost(navController = navController, startDestination = "PasswordDataScreen") {
        composable("MainScreen") {
            MainScreen(innerPadding, navController)
        }
        composable("CreatePasswordScreen") {
            CreatePasswordScreen(innerPadding, repo)
        }
        composable("ShowPasswordScreen") {
            ShowPasswordsScreen(innerPadding, context, snackFunction, repo)
        }
        composable("ManagePasswordScreen") {
            ManagePasswordScreen(innerPadding)
        }
        composable("PasswordDataScreen") {
            PasswordDataScreen(innerPadding, context, snackFunction, repo.findAll().last())
        }
    }
}