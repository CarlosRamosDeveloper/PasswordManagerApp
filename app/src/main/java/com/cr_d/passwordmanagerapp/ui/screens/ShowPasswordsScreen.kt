package com.cr_d.passwordmanagerapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

import com.cr_d.passwordmanagerapp.R
import com.cr_d.passwordmanagerapp.application.interfaces.IPasswordRepository
import com.cr_d.passwordmanagerapp.domain.value_objects.PasswordData

@Composable
fun ShowPasswordsScreen(innerPadding: PaddingValues, navController: NavController, repository: IPasswordRepository){
    PasswordCardsList(innerPadding, repository, navController)
}

@Composable
fun PasswordCard(password: PasswordData, navController: NavController,){
    val verticalPadding = 10.dp
    val horizontalPadding = 20.dp

    Row (modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 20.dp, vertical = 10.dp)){
        Image(
            painter = painterResource(R.drawable.outline_visibility_24),
            contentDescription = "",
            Modifier.size(75.dp).align(Alignment.CenterVertically)
        )
        Column (Modifier.weight(1f)){
            Text(
                password.application, fontSize = 20.sp, fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(
                    vertical = verticalPadding,
                    horizontal = horizontalPadding
                )
            )
            Spacer(Modifier.weight(1f))
            Text(
                password.account,
                modifier = Modifier.padding(
                    vertical = verticalPadding,
                    horizontal = horizontalPadding
                )
            )
        }
        Column (Modifier.align(Alignment.CenterVertically)){
            Icon(
                Icons.AutoMirrored.Filled.ArrowRight,
                contentDescription = "",
                modifier = Modifier
                    .clickable(
                        onClick = { navController.navigate("PasswordDataScreen/${password.id}")}
                    )
                    .size(50.dp)
            )
        }
    }
    HorizontalDivider(thickness = 2.dp, modifier = Modifier.fillMaxWidth())
}

@Composable
fun PasswordCardsList(innerPadding: PaddingValues, repository: IPasswordRepository, navController: NavController,){
    LazyColumn (Modifier.padding(innerPadding)){
        items(repository.findAll()) { pwd ->
            PasswordCard(pwd, navController)
        }
    }
}
