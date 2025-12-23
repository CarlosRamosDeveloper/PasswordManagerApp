package com.cr_d.passwordmanagerapp.ui.router

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

import com.cr_d.passwordmanagerapp.application.interfaces.IPasswordRepository
import com.cr_d.passwordmanagerapp.application.use_cases.GeneratePasswordUseCase
import com.cr_d.passwordmanagerapp.domain.entities.PasswordGenerator
import com.cr_d.passwordmanagerapp.ui.screens.create_password.CreatePasswordScreen
import com.cr_d.passwordmanagerapp.ui.screens.MainScreen
import com.cr_d.passwordmanagerapp.ui.screens.ManagePasswordScreen
import com.cr_d.passwordmanagerapp.ui.screens.PasswordDataScreen
import com.cr_d.passwordmanagerapp.ui.screens.ShowPasswordsScreen
import com.cr_d.passwordmanagerapp.ui.screens.create_password.CreatePasswordViewModel

@SuppressLint("ViewModelConstructorInComposable")
@Composable
fun Router(
    innerPadding: PaddingValues,
    navController: NavHostController,
    snackFunction: (String)-> Unit,
    repo: IPasswordRepository
){
    val context = LocalContext.current

    NavHost(navController = navController, startDestination = "ShowPasswordScreen") {
        composable("MainScreen") {
            MainScreen(innerPadding, navController)
        }
        composable("CreatePasswordScreen") {
            val generator = PasswordGenerator()
            val generatePassword = GeneratePasswordUseCase(generator)
            CreatePasswordScreen(innerPadding, CreatePasswordViewModel(repo, generatePassword))
        }
        composable("ShowPasswordScreen") {
            ShowPasswordsScreen(innerPadding, navController, repo)
        }
        composable("ManagePasswordScreen") {
            ManagePasswordScreen(innerPadding)
        }
        composable("PasswordDataScreen/{passwordId}", arguments = listOf(navArgument("passwordId") {
            type = NavType.IntType
        })) { backstackEntry ->
            val passwordId = backstackEntry.arguments?.getInt("passwordId") ?: 1
            PasswordDataScreen(innerPadding, context, snackFunction, passwordId, repo)
        }
    }
}