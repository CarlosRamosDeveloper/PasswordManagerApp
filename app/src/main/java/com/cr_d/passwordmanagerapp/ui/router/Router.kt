package com.cr_d.passwordmanagerapp.ui.router

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import com.cr_d.passwordmanagerapp.application.interfaces.IPasswordRepository
import com.cr_d.passwordmanagerapp.application.use_cases.GeneratePasswordUseCase
import com.cr_d.passwordmanagerapp.domain.entities.PasswordGenerator
import com.cr_d.passwordmanagerapp.domain.entities.PasswordPolicy
import com.cr_d.passwordmanagerapp.domain.value_objects.PasswordDataGeneration
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
    NavHost(navController = navController, startDestination = "PasswordDataScreen") {
        composable("MainScreen") {
            MainScreen(innerPadding, navController)
        }
        composable("CreatePasswordScreen") {
            val newPassword =
                PasswordDataGeneration(
                    true,
                    false,
                    false,
                    false,
                    PasswordPolicy.MIN_GENERATED_LENGTH,
                )
            val generator = PasswordGenerator()
            val generatePassword = GeneratePasswordUseCase(generator)
            CreatePasswordScreen(innerPadding, CreatePasswordViewModel(repo, generatePassword))
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