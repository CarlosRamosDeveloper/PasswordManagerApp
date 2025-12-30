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
import com.cr_d.passwordmanagerapp.ui.screens.create_password.CreatePasswordViewModelFactory
import com.cr_d.passwordmanagerapp.ui.screens.main_screen.MainScreenViewModel
import com.cr_d.passwordmanagerapp.ui.screens.main_screen.MainScreenViewModelFactory
import com.cr_d.passwordmanagerapp.ui.screens.password_detail.PasswordDetailViewModelFactory
import com.cr_d.passwordmanagerapp.ui.screens.password_detail.PasswordDetailViewModel
import com.cr_d.passwordmanagerapp.ui.screens.passwords_list.PasswordListViewModel
import com.cr_d.passwordmanagerapp.ui.screens.passwords_list.PasswordListViewModelFactory
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
    val generator = PasswordGenerator()
    val generatePasswordUseCase = GeneratePasswordUseCase(generator)
    val scoreCalculator = SecurityScoreCalculator()
    val createPasswordUseCase = SavePasswordUseCase(repo)

    val settingsViewModel: SettingsViewModel = viewModel(context as ComponentActivity )
    val mainViewModel: MainScreenViewModel = viewModel(
        factory = MainScreenViewModelFactory(repo)
    )
    val createPasswordViewModel: CreatePasswordViewModel = viewModel(
        factory = CreatePasswordViewModelFactory(
            generatePasswordUseCase = generatePasswordUseCase,
            scoreCalculator = CalculateSecurityScoreUseCase(scoreCalculator),
            savePasswordUseCase = createPasswordUseCase)
    )
    val passwordListViewModel: PasswordListViewModel = viewModel(
        factory = PasswordListViewModelFactory(
            getAllPasswordsUseCase = GetAllPasswordsUseCase(repo)
        )
    )

    NavHost(navController = navController, startDestination = "MainScreen") {
        composable("MainScreen") {
            MainScreen(innerPadding, mainViewModel)
        }
        composable("CreatePasswordScreen") {
            CreatePasswordScreen(
                innerPadding = innerPadding,
                viewModel = createPasswordViewModel,
                context = context,
                snackFunction = snackFunction
            )
        }
        composable("ShowPasswordScreen") {
            PasswordsListScreen(
                innerPadding = innerPadding,
                navController = navController,
                viewModel = passwordListViewModel
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
            val passwordDetailVM:PasswordDetailViewModel = viewModel(
                factory = PasswordDetailViewModelFactory(
                    repository = repo,
                    passwordId = passwordId,
                    generatePasswordUseCase = generatePasswordUseCase,
                    securityScoreCalculator = scoreCalculatorUseCase,
                    updatePasswordUseCase = UpdatePasswordUseCase(repo),
                    deletePasswordUseCase = DeletePasswordUseCase(repo),
                    decrypt = DecryptStringUseCase(CryptoService())
                )
            )
            PasswordDetailScreen(
                innerPadding = innerPadding,
                context = context,
                snackFunction = snackFunction,
                viewModel = passwordDetailVM,
                settings = settingsViewModel,
                navController = navController
            )
        }
        composable("SettingsScreen"){
            SettingsScreen(innerPadding, settingsViewModel)
        }
    }
}

