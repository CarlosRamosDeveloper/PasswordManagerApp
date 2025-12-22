package com.cr_d.passwordmanagerapp.ui.screens

import android.content.ClipData
import android.content.ClipDescription
import android.content.ClipboardManager
import android.content.Context
import android.os.PersistableBundle
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowRight
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.max

import com.cr_d.passwordmanagerapp.R
import com.cr_d.passwordmanagerapp.domain.value_objects.PasswordData
import com.cr_d.passwordmanagerapp.ui.PasswordRepository

@Composable
fun ShowPasswordsScreen(innerPadding: PaddingValues, context: Context, snackFunction: (String)-> Unit){

    PasswordCardsList(innerPadding, context, snackFunction)
}

@Composable
fun PasswordCard(password: PasswordData, context: Context, snackFunction: (String)-> Unit){
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
                        onClick = {}
                    )
                    .size(50.dp)
            )
        }
    }
    HorizontalDivider(thickness = 2.dp, modifier = Modifier.fillMaxWidth())
}

@Composable
fun PasswordCardsList(innerPadding: PaddingValues, context: Context, snackFunction: (String)-> Unit){
    LazyColumn (Modifier.padding(innerPadding)){
        items(PasswordRepository.passwords) { pwd ->
            PasswordCard(pwd, context, snackFunction)
        }
    }
}
