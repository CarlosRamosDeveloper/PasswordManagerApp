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
import com.cr_d.passwordmanagerapp.application.use_cases.CalculateSecurityScoreUseCase
import com.cr_d.passwordmanagerapp.application.use_cases.DecryptStringUseCase
import com.cr_d.passwordmanagerapp.application.use_cases.DeletePasswordUseCase
import com.cr_d.passwordmanagerapp.application.use_cases.SavePasswordUseCase
import com.cr_d.passwordmanagerapp.application.use_cases.GeneratePasswordUseCase
import com.cr_d.passwordmanagerapp.application.use_cases.GetAllPasswordsUseCase
import com.cr_d.passwordmanagerapp.application.use_cases.UpdatePasswordUseCase
import com.cr_d.passwordmanagerapp.data.crypto.CryptoService
import com.cr_d.passwordmanagerapp.domain.entities.PasswordGenerator
import com.cr_d.passwordmanagerapp.domain.entities.SecurityScoreCalculator
import com.cr_d.passwordmanagerapp.ui.screens.create_password.CreatePasswordScreen
import com.cr_d.passwordmanagerapp.ui.screens.main_screen.MainScreen
import com.cr_d.passwordmanagerapp.ui.screens.password_detail.PasswordDetailScreen
import com.cr_d.passwordmanagerapp.ui.screens.passwords_list.PasswordsListScreen
import com.cr_d.passwordmanagerapp.ui.screens.create_password.CreatePasswordViewModel
import com.cr_d.passwordmanagerapp.ui.screens.main_screen.MainScreenViewModel
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
    val settingsViewModel: SettingsViewModel = viewModel(context as ComponentActivity )

    NavHost(navController = navController, startDestination = "MainScreen") {
        composable("MainScreen") {
            MainScreen(innerPadding, MainScreenViewModel(repo))
        }
        composable("CreatePasswordScreen") {
            val generator = PasswordGenerator()
            val generatePasswordUseCase = GeneratePasswordUseCase(generator)
            val scoreCalculator = SecurityScoreCalculator()
            val createPasswordUseCase = SavePasswordUseCase(repo)
            CreatePasswordScreen(
                innerPadding = innerPadding,
                viewModel = CreatePasswordViewModel(
                    generatePasswordUseCase,
                    CalculateSecurityScoreUseCase(scoreCalculator),
                    createPasswordUseCase
                ),
                context = context,
                snackFunction = snackFunction
            )
        }
        composable("ShowPasswordScreen") {
            PasswordsListScreen(
                innerPadding = innerPadding,
                navController = navController,
                viewModel = PasswordListViewModel(
                    GetAllPasswordsUseCase(repo)
                )
            )
        }
        composable("PasswordDataScreen/{passwordId}", arguments = listOf(navArgument("passwordId") {
            type = NavType.LongType
        })) { backstackEntry ->
            val passwordId = backstackEntry.arguments?.getLong("passwordId") ?: 1
            val generator = PasswordGenerator()
            val generatePasswordUseCase = GeneratePasswordUseCase(generator)
            val scoreCalculator = SecurityScoreCalculator()
            val scoreCalculatorUseCase = CalculateSecurityScoreUseCase(scoreCalculator)
            PasswordDetailScreen(
                innerPadding = innerPadding,
                context = context,
                snackFunction = snackFunction,
                viewModel = PasswordDetailViewModel(
                    repository = repo,
                    passwordId = passwordId,
                    generatePasswordUseCase = generatePasswordUseCase,
                    securityScoreCalculator = scoreCalculatorUseCase,
                    updatePasswordUseCase = UpdatePasswordUseCase(repo),
                    deletePasswordUseCase = DeletePasswordUseCase(repo),
                    decrypt = DecryptStringUseCase(CryptoService())
                ),
                settings = settingsViewModel,
                navController = navController
            )
        }
        composable("SettingsScreen"){
            SettingsScreen(innerPadding, settingsViewModel)
        }
    }
}