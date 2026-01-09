package com.cr_d.passwordmanagerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

import com.cr_d.passwordmanagerapp.application.PasswordManagerApp
import com.cr_d.passwordmanagerapp.ui.scaffold.AppScaffold
import com.cr_d.passwordmanagerapp.ui.theme.PasswordManagerAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val appGraph = (application as PasswordManagerApp).appGraph

            PasswordManagerAppTheme {
                AppScaffold(appGraph)
            }
        }
    }
}
