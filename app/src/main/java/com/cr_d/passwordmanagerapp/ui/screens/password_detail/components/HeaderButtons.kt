package com.cr_d.passwordmanagerapp.ui.screens.password_detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

import com.cr_d.passwordmanagerapp.ui.models.AppConfig
import com.cr_d.passwordmanagerapp.ui.screens.password_detail.PasswordDetailViewModel

@Composable
fun HeaderButtons(viewModel: PasswordDetailViewModel, snackFunction: (String)-> Unit, navController: NavController){
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = AppConfig.HORIZONTAL_FRAME_PADDING, vertical = 5.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    )
    {
        ModeButton("Info", viewModel::onEnableBasicInfoMode)
        ModeButton("Detalle", viewModel::onEnableFullInfoMode)
        ModeButton("Editar", viewModel::onEnableEditMode)
        DeletePasswordButton(snackFunction, viewModel, navController)
    }
}

@Composable
fun ModeButton(label: String, onclick: () -> Unit){
    Button(
        onClick = { onclick() },
        shape = RectangleShape
    ) {
        Text(label)
    }
}