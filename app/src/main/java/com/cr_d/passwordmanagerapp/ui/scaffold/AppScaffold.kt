package com.cr_d.passwordmanagerapp.ui.scaffold

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch

import com.cr_d.passwordmanagerapp.application.AppGraph
import com.cr_d.passwordmanagerapp.ui.model.CustomNavigationItem
import com.cr_d.passwordmanagerapp.ui.model.FabState
import com.cr_d.passwordmanagerapp.ui.router.Router

@Composable
fun AppScaffold(appGraph: AppGraph){
    val navController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutine = rememberCoroutineScope()
    var fabState by remember { mutableStateOf<FabState?>(null) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { CustomAppBar(navController) },
        bottomBar = { CustomNavBar(navController) },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        floatingActionButton = {
            fabState?.let {
                FloatingActionButton(
                    onClick = if(fabState!!.isEnabled) it.onclick else {{}},
                    containerColor = it.color ?: MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .alpha(if (fabState!!.isEnabled) 1f else 0.3f)
                        .pointerInput(fabState!!.isEnabled) {
                            awaitPointerEventScope {
                                while(true) awaitPointerEvent()
                            }
                        }
                ) {
                    Icon(
                        it.icon, contentDescription = ""
                    )
                }
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { innerPadding ->
        Router(
            innerPadding = innerPadding,
            navController = navController,
            snackFunction = { coroutine.launch { snackbarHostState.showSnackbar(it) } },
            appGraph = appGraph,
            setFabState = { fabState=it }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomAppBar(navController: NavHostController){
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    CenterAlignedTopAppBar(
        title= { Text("Password Manager App") },
        navigationIcon = {
            if (currentDestination?.route != "MainScreen") {
                IconButton(
                    onClick = { navController.navigate("MainScreen") }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Localized description"
                    )}
            }
        },
        actions = {
                Icon(
                    Icons.Filled.Settings,
                    contentDescription = "",
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .clickable(
                            onClick = { navController.navigate("SettingsScreen") }
                        )
                )
        }
    )
}

@Composable
fun CustomNavBar(navController: NavHostController){
    var selectedDestination by rememberSaveable { mutableIntStateOf(0) }

    val destinations = listOf(
        CustomNavigationItem(Icons.Filled.Done, "Contraseñas", "ShowPasswordScreen"),
        CustomNavigationItem(Icons.Filled.Info, "Cuentas", "AccountListScreen"),
        CustomNavigationItem(Icons.Filled.Settings, "Aplicaciones", "ApplicationsListScreen"),
    )

    Column {
        NavigationBar(windowInsets = NavigationBarDefaults.windowInsets) {
            destinations.forEachIndexed { index, destination ->
                NavigationBarItem(
                    selected = selectedDestination == index,
                    onClick = {
                        navController.navigate(route = destination.route)
                        selectedDestination = index
                    },
                    icon = {
                        Icon(
                            destination.icon,
                            contentDescription = ""
                        )
                    },
                    label = { Text(destination.label) }
                )
            }
        }
    }
}