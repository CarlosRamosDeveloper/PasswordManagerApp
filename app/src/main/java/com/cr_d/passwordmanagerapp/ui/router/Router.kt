package com.cr_d.passwordmanagerapp.ui.router

import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

import com.cr_d.passwordmanagerapp.application.interfaces.IPasswordRepository
import com.cr_d.passwordmanagerapp.application.use_cases.SavePasswordUseCase
import com.cr_d.passwordmanagerapp.application.use_cases.GeneratePasswordUseCase
import com.cr_d.passwordmanagerapp.domain.entities.PasswordGenerator
import com.cr_d.passwordmanagerapp.domain.entities.SecurityScoreCalculator
import com.cr_d.passwordmanagerapp.ui.screens.create_password.CreatePasswordScreen
import com.cr_d.passwordmanagerapp.ui.screens.MainScreen
import com.cr_d.passwordmanagerapp.ui.screens.password_detail.PasswordDetailScreen
import com.cr_d.passwordmanagerapp.ui.screens.passwords_list.PasswordsListScreen
import com.cr_d.passwordmanagerapp.ui.screens.create_password.CreatePasswordViewModel
import com.cr_d.passwordmanagerapp.ui.screens.password_detail.PasswordDetailViewModel
import com.cr_d.passwordmanagerapp.ui.screens.passwords_list.PasswordListViewModel
import com.cr_d.passwordmanagerapp.ui.screens.settings.SettingsScreen
import com.cr_d.passwordmanagerapp.ui.screens.settings.SettingsViewModel

@SuppressLint("ViewModelConstructorInComposable")
@Composable
fun Router(
    innerPadding: PaddingValues,
    navController: NavHostController,
    snackFunction: (String)-> Unit,
    repo: IPasswordRepository
){
    val context = LocalContext.current
    val settingsViewModel: SettingsViewModel = viewModel(LocalContext.current as ComponentActivity )

    NavHost(navController = navController, startDestination = "SettingsScreen") {
        composable("MainScreen") {
            MainScreen(innerPadding, navController)
        }
        composable("CreatePasswordScreen") {
            val generator = PasswordGenerator()
            val generatePasswordUseCase = GeneratePasswordUseCase(generator)
            val scoreCalculator = SecurityScoreCalculator()
            val createPasswordUseCase = SavePasswordUseCase(repo)
            CreatePasswordScreen(
                innerPadding,
                CreatePasswordViewModel(
                    generatePasswordUseCase,
                    scoreCalculator,
                    createPasswordUseCase
                ), context,
                snackFunction
            )
        }
        composable("ShowPasswordScreen") {
            PasswordsListScreen(innerPadding, navController, PasswordListViewModel(repo))
        }
        composable("PasswordDataScreen/{passwordId}", arguments = listOf(navArgument("passwordId") {
            type = NavType.IntType
        })) { backstackEntry ->
            val passwordId = backstackEntry.arguments?.getInt("passwordId") ?: 1
            PasswordDetailScreen(innerPadding, context, snackFunction,
                PasswordDetailViewModel(repo, passwordId))
        }
        composable("SettingsScreen"){
            SettingsScreen(innerPadding, settingsViewModel)
        }
    }
}