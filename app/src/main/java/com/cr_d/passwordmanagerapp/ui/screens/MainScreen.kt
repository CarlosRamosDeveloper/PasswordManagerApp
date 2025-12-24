package com.cr_d.passwordmanagerapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun MainScreen(innerPadding: PaddingValues, navController: NavHostController){
    Column(
        modifier= Modifier.padding(innerPadding).fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ClickableText(navController, "Ver contraseñas", "ShowPasswordScreen")
        ClickableText(navController,"Crear contraseña", "CreatePasswordScreen")
        ClickableText(navController,"Modificar contraseña")
        ClickableText(navController,"Asociar nueva cuenta")
        ClickableText(navController,"Cerrar aplicación")
    }
}

@Composable
fun ClickableText(navController: NavHostController, text: String, route: String = ""){
    Text(text, modifier = Modifier.padding(20.dp).clickable(
        onClick = {
            if (route == "")
            else navController.navigate(route)
        }
    ))
}
