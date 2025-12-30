package com.cr_d.passwordmanagerapp.ui.common_components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

import com.cr_d.passwordmanagerapp.R

@Composable
fun CustomButton(
    buttonText: String,
    onclick: ()-> Unit,
    modifier: Modifier = Modifier
){
    Button(
        onClick = onclick,
        modifier = modifier
    ){
        Text(buttonText)
    }
}

@Composable
fun CopyToClipboardButton(onclick: () -> Unit){
    Button(
        onClick = {
            onclick()
        }
    ) {
        Row (horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
            Text("Copiar", modifier = Modifier.padding(horizontal = 15.dp))
            Image(
                painterResource(R.drawable.outline_content_copy_24),
                contentDescription = "",
            )
        }
    }
}