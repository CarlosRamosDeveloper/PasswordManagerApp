package com.cr_d.passwordmanagerapp.ui.router

import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

import com.cr_d.passwordmanagerapp.application.AppGraph
import com.cr_d.passwordmanagerapp.ui.screens.accounts.list.AccountListScreen
import com.cr_d.passwordmanagerapp.ui.screens.accounts.list.AccountListViewModel
import com.cr_d.passwordmanagerapp.ui.screens.passwords.create.CreatePasswordScreen
import com.cr_d.passwordmanagerapp.ui.screens.main_screen.MainScreen
import com.cr_d.passwordmanagerapp.ui.screens.passwords.detail.PasswordDetailScreen
import com.cr_d.passwordmanagerapp.ui.screens.passwords.list.PasswordsListScreen
import com.cr_d.passwordmanagerapp.ui.screens.passwords.create.CreatePasswordViewModel
import com.cr_d.passwordmanagerapp.ui.screens.main_screen.MainScreenViewModel
import com.cr_d.passwordmanagerapp.ui.screens.passwords.detail.PasswordDetailViewModel
import com.cr_d.passwordmanagerapp.ui.screens.passwords.list.PasswordListViewModel
import com.cr_d.passwordmanagerapp.ui.screens.settings.SettingsScreen
import com.cr_d.passwordmanagerapp.ui.screens.settings.SettingsViewModel

@SuppressLint("ViewModelConstructorInComposable")
@Composable
fun Router(
    innerPadding: PaddingValues,
    navController: NavHostController,
    snackFunction: (String)-> Unit,
    appGraph: AppGraph
){
    val context = LocalContext.current
    val settingsViewModel: SettingsViewModel = viewModel(context as ComponentActivity )

    NavHost(navController = navController, startDestination = "MainScreen") {
        composable("MainScreen") {
            val mainViewModel: MainScreenViewModel = viewModel(
                factory = remember { appGraph.mainScreenFactory },
                viewModelStoreOwner = context
            )
            MainScreen(innerPadding, mainViewModel, navController)
        }
        composable("CreatePasswordScreen") {
            val createPasswordViewModel: CreatePasswordViewModel = viewModel(
                factory = remember { appGraph.createPasswordFactory },
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
                factory = remember { appGraph.listPasswordFactory },
                viewModelStoreOwner = context
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
                factory = appGraph.detailPasswordFactory(passwordId)
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

        composable("AccountListScreen"){
            val accountListViewModel: AccountListViewModel = viewModel(
                factory = remember { appGraph.accountListFactory },
                viewModelStoreOwner = context
            )

            AccountListScreen(
                innerPadding = innerPadding,
                navController = navController,
                viewModel = accountListViewModel
            )
        }
    }
}

