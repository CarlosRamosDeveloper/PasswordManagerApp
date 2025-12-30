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

import com.cr_d.passwordmanagerapp.di.AppGraph
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
){
    val context = LocalContext.current
    val settingsViewModel: SettingsViewModel = viewModel(context as ComponentActivity )

    NavHost(navController = navController, startDestination = "MainScreen") {
        composable("MainScreen") {
            val mainViewModel: MainScreenViewModel = viewModel(
                factory = AppGraph.mainScreenFactory()
            )
            MainScreen(innerPadding, mainViewModel)
        }
        composable("CreatePasswordScreen") {
            val createPasswordViewModel: CreatePasswordViewModel = viewModel(
                factory = AppGraph.createPasswordFactory(),
                viewModelStoreOwner = context,
            )
            CreatePasswordScreen(
                innerPadding = innerPadding,
                viewModel = createPasswordViewModel,
                context = context,
                snackFunction = snackFunction
            )
        }
        composable("ShowPasswordScreen") {
            val passwordListViewModel: PasswordListViewModel = viewModel(
                factory = AppGraph.listPasswordFactory()
            )
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
            val passwordDetailVM:PasswordDetailViewModel = viewModel(
                factory = AppGraph.detailPasswordFactory(passwordId)
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

