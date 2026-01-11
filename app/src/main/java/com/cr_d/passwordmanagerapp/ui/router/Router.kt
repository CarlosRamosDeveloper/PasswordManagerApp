package com.cr_d.passwordmanagerapp.ui.router

import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

import com.cr_d.passwordmanagerapp.application.AppGraph
import com.cr_d.passwordmanagerapp.ui.model.FabState
import com.cr_d.passwordmanagerapp.ui.screens.accounts.create.CreateAccountScreen
import com.cr_d.passwordmanagerapp.ui.screens.accounts.create.CreateAccountViewModel
import com.cr_d.passwordmanagerapp.ui.screens.accounts.detail.AccountDetailScreen
import com.cr_d.passwordmanagerapp.ui.screens.accounts.detail.AccountDetailViewModel
import com.cr_d.passwordmanagerapp.ui.screens.accounts.list.AccountListScreen
import com.cr_d.passwordmanagerapp.ui.screens.accounts.list.AccountListViewModel
import com.cr_d.passwordmanagerapp.ui.screens.applications.create.CreateApplicationScreen
import com.cr_d.passwordmanagerapp.ui.screens.applications.create.CreateApplicationViewModel
import com.cr_d.passwordmanagerapp.ui.screens.applications.detail.ApplicationDetailScreen
import com.cr_d.passwordmanagerapp.ui.screens.applications.detail.ApplicationDetailViewModel
import com.cr_d.passwordmanagerapp.ui.screens.applications.list.ApplicationListScreen
import com.cr_d.passwordmanagerapp.ui.screens.applications.list.ApplicationListViewModel
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
    appGraph: AppGraph,
    setFabState: (FabState?) -> Unit
){
    val context = LocalContext.current
    val settingsViewModel: SettingsViewModel = viewModel(context as ComponentActivity )

    NavHost(navController = navController, startDestination = "MainScreen") {
        composable("MainScreen") {
            val mainViewModel: MainScreenViewModel = viewModel(
                factory = remember { appGraph.mainScreenFactory },
                viewModelStoreOwner = context
            )
            setFabState(null)
            MainScreen(innerPadding, mainViewModel, navController)
        }
        composable("CreatePasswordScreen") {
            val createPasswordViewModel: CreatePasswordViewModel = viewModel(
                factory = remember { appGraph.createPasswordFactory },
                viewModelStoreOwner = context,
            )
            val isSaveEnabled by createPasswordViewModel.isSaveEnabled.collectAsStateWithLifecycle()
            val fabState = FabState(
                icon = Icons.Default.Save,
                color = null,
                isEnabled = isSaveEnabled,
                onclick = createPasswordViewModel::onEnableSaveDialog
            )
            setFabState(fabState)
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
            val fabState = FabState(
                icon = Icons.Default.Add,
                color = null,
                onclick = { navController.navigate("CreatePasswordScreen") }
            )
            setFabState(fabState)

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
            val fabState = FabState(
                icon = Icons.Default.Delete,
                color = null,
                onclick = passwordDetailVM::onEnableDeletePasswordDialog
            )
            setFabState(fabState)

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
            setFabState(null)
            SettingsScreen(innerPadding, settingsViewModel)
        }
        composable("CreateAccountScreen") {
            val createAccVM: CreateAccountViewModel = viewModel(
                factory = remember { appGraph.createAccountFactory }
            )
            val isSaveEnabled by createAccVM.isSaveEnabled.collectAsStateWithLifecycle()
            val fabState = FabState(
                icon = Icons.Default.Save,
                color = null,
                isEnabled = isSaveEnabled,
                onclick = createAccVM::onEnableSaveDialog
            )
            setFabState(fabState)
            CreateAccountScreen(
                innerPadding = innerPadding,
                viewModel = createAccVM,
                navController = navController,
                snackFunction = snackFunction
            )
        }
        composable("AccountListScreen"){
            val accountListViewModel: AccountListViewModel = viewModel(
                factory = remember { appGraph.accountListFactory },
                viewModelStoreOwner = context
            )
            val fabState = FabState(
                icon = Icons.Default.Add,
                color = null,
                onclick = { navController.navigate("CreateAccountScreen")}
            )
            setFabState(fabState)
            AccountListScreen(
                innerPadding = innerPadding,
                navController = navController,
                viewModel = accountListViewModel
            )
        }
        composable("AccountDetailScreen/{accountId}", arguments = listOf(navArgument("accountId") {
            type = NavType.LongType
        })) { backstackEntry ->
            val accountId = backstackEntry.arguments?.getLong("accountId") ?: 1
            val accountDetailVM: AccountDetailViewModel = viewModel(
                factory = remember { appGraph.accountDetailFactory(accountId) }
            )
            val fabState = FabState(
                icon = Icons.Default.Delete,
                color = null,
                onclick = accountDetailVM::onEnableDeleteDialog
            )
            setFabState(fabState)
            AccountDetailScreen(
                innerPadding = innerPadding,
                snackFunction = snackFunction,
                viewModel = accountDetailVM,
                navController = navController
            )
        }
        composable("CreateApplicationScreen") {
            val createAppVM: CreateApplicationViewModel = viewModel(
                factory = remember { appGraph.createApplicationFactory }
            )
            val isSaveEnabled by createAppVM.isSaveEnabled.collectAsStateWithLifecycle()

            val fabState = FabState(
                icon = Icons.Default.Save,
                color = null,
                isEnabled = isSaveEnabled,
                onclick = createAppVM::onEnableSaveDialog
            )
            setFabState(fabState)
            CreateApplicationScreen(
                innerPadding = innerPadding,
                viewModel = createAppVM,
                navController = navController,
                snackFunction = snackFunction
            )
        }
        composable("ApplicationsListScreen"){
            val appListViewModel: ApplicationListViewModel = viewModel(
                factory = remember { appGraph.applicationListFactory },
                viewModelStoreOwner = context
            )
            val fabState = FabState(
                icon = Icons.Default.Add,
                color = null,
                onclick = { navController.navigate("CreateApplicationScreen")}
            )
            setFabState(fabState)
            ApplicationListScreen(
                innerPadding = innerPadding,
                navController = navController,
                viewModel = appListViewModel
            )
        }
        composable("ApplicationDetailScreen/{appId}", arguments = listOf(navArgument("appId") {
            type = NavType.LongType
        })) { backstackEntry ->
            val appId = backstackEntry.arguments?.getLong("appId") ?: 1
            val appDetailVM: ApplicationDetailViewModel = viewModel(
                factory = remember { appGraph.applicationDetailFactory(appId)}
            )
            val fabState = FabState(
                icon = Icons.Default.Delete,
                color = null,
                onclick = appDetailVM::onEnableDeleteDialog
            )
            setFabState(fabState)
            ApplicationDetailScreen(
                innerPadding = innerPadding,
                snackFunction = snackFunction,
                viewModel = appDetailVM,
                navController = navController
            )
        }
    }
}

