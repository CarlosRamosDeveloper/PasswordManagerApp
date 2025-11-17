package com.cr_d.passwordmanagerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.cr_d.passwordmanagerapp.ui.models.CustomNavigationItem

import com.cr_d.passwordmanagerapp.ui.router.Router
import com.cr_d.passwordmanagerapp.ui.theme.PasswordManagerAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            PasswordManagerAppTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = { CustomAppBar(navController) },
                    bottomBar = { CustomNavBar(navController) }
                ) { innerPadding ->
                    Router(innerPadding, navController)
                }
            }
        }
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
    )
}

@Composable
fun CustomNavBar(navController: NavHostController){
    var selectedDestination by rememberSaveable { mutableIntStateOf(0) }

    val destinations = listOf(
        CustomNavigationItem(Icons.Filled.Done, "Ver Contraseñas", "ShowPasswordScreen"),
        CustomNavigationItem(Icons.Filled.Info, "Crear contraseña", "CreatePasswordScreen"),
        CustomNavigationItem(Icons.Filled.Favorite, "Gestionar contraseñas", "ManagePasswordScreen"),
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