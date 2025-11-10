package com.cr_d.passwordmanagerapp.ui.router

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.cr_d.passwordmanagerapp.ui.screens.CreatePasswordScreen
import com.cr_d.passwordmanagerapp.ui.screens.MainScreen
import com.cr_d.passwordmanagerapp.ui.screens.ShowPasswordsScreen

@Composable
fun Router(innerPadding: PaddingValues, navController: NavHostController){

    NavHost(navController = navController, startDestination = "MainScreen") {
        composable("MainScreen") {
            MainScreen(innerPadding, navController)
        }
        composable("CreatePasswordScreen") {
            CreatePasswordScreen(innerPadding)
        }
        composable("ShowPasswordScreen") {
            ShowPasswordsScreen(innerPadding)
        }

    }
}