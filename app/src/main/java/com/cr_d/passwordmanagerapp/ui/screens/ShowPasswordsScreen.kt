package com.cr_d.passwordmanagerapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.cr_d.passwordmanagerapp.domain.value_objects.PasswordData
import com.cr_d.passwordmanagerapp.ui.PasswordRepository

@Composable
fun ShowPasswordsScreen(innerPadding: PaddingValues){
    PasswordCardsList(innerPadding)
}

@Composable
fun PasswordCard(password: PasswordData){
    var isPasswordShown by remember { mutableStateOf(false) }

    Card(modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 20.dp, vertical = 10.dp)) {
        Row (Modifier.fillMaxSize()){
            TogglePasswordVisibilityButton(isPasswordShown, { isPasswordShown = it })
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(1f)) {
                Text("Account: ${password.account}")
                if (isPasswordShown) Text("Password: ${password.password}")
                else Text("Password: ********}")
            }
            CopyToClipboardButton()
        }
    }
}

@Composable
fun PasswordCardsList(innerPadding: PaddingValues){
    LazyColumn (Modifier.padding(innerPadding)){
        items(PasswordRepository.passwords) { pwd ->
            PasswordCard(pwd)
        }
    }
}

@Composable
fun TogglePasswordVisibilityButton(isPasswordShown: Boolean, onVisionToggle: (Boolean) -> Unit){
    Button(
        onClick = { if(isPasswordShown) onVisionToggle(false) else onVisionToggle(true) }
    ) {
        if(isPasswordShown){
            Icon(
                imageVector = Icons.Filled.Info,
                contentDescription = "None",
            )
        } else {
            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = "None",
            )
        }

    }
}

@Composable
fun CopyToClipboardButton(){
    Button(
        onClick = {}
    ) {
        Icon(imageVector = Icons.Default.Done, contentDescription = "")
    }
}