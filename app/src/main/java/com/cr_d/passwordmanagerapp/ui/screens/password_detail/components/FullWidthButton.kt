package com.cr_d.passwordmanagerapp.ui.screens.password_detail.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FullWidthButton(buttonText: String, onclick: () -> Unit, horizontalPadding: Int = 20){
    Button(
        modifier =
            Modifier.fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = horizontalPadding.dp),
        onClick = { onclick() }

    ) {
        Text(buttonText)
    }

}