package com.cr_d.passwordmanagerapp.ui.screens

import android.content.ClipData
import android.content.ClipDescription
import android.content.ClipboardManager
import android.content.Context
import android.os.PersistableBundle
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
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
    var isPasswordShown by remember { mutableStateOf(false) }

    Card(modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 20.dp, vertical = 10.dp)) {
        Row (Modifier.fillMaxSize()){
            TogglePasswordVisibilityButton(isPasswordShown, { isPasswordShown = it })
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(1f)) {
                Text("Account: ${password.account}")
                if (isPasswordShown) Text("Password: ${password.password}")
                else Text("Password: ********")
                Text("Fecha de creación: ${password.creationDate}")
                Text("Última actualización: ${password.lastUpdate}")
                Text("Puntuación de seguridad ${password.securityScore}")
            }
            CopyToClipboardButton(password, context, snackFunction)
        }
    }
}

@Composable
fun PasswordCardsList(innerPadding: PaddingValues, context: Context, snackFunction: (String)-> Unit){
    LazyColumn (Modifier.padding(innerPadding)){
        items(PasswordRepository.passwords) { pwd ->
            PasswordCard(pwd, context, snackFunction)
        }
    }
}

@Composable
fun TogglePasswordVisibilityButton(isPasswordShown: Boolean, onVisionToggle: (Boolean) -> Unit){
    Button(
        onClick = { if(isPasswordShown) onVisionToggle(false) else onVisionToggle(true) }
    ) {

        val progress by animateFloatAsState(
            targetValue = if (isPasswordShown) 1f else 0f,
            animationSpec = tween(300)
        )

        if(isPasswordShown){
            Icon(
                painter = painterResource(R.drawable.outline_visibility_24),
                contentDescription = null,
                modifier = Modifier.graphicsLayer {
                    scaleY = progress          // se aplasta al cerrarse
                    alpha = max(progress, 0.2f)
                    transformOrigin = TransformOrigin(0.5f, 0.5f)
                }
            )
        } else {
            Icon(
                painter = painterResource(R.drawable.outline_visibility_off_24),
                contentDescription = null,
                modifier = Modifier.graphicsLayer {
                    alpha = 1f - progress      // aparece al cerrar
                    scaleX = 1f - progress
                }
            )
        }
    }
}

@Composable
fun CopyToClipboardButton(password: PasswordData, context: Context, snackFunction: (String)-> Unit){
    Button(
        onClick = {
            copyToClipboard(password.password, context, snackFunction)
        }
    ) {
        Image(
            painterResource(R.drawable.outline_content_copy_24),
            contentDescription = "",
        )
    }
}

fun copyToClipboard(text: String, context: Context, snackFunction: (String)-> Unit) {
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("Copied_Text", text).apply {
        description.extras = PersistableBundle().apply {
            putBoolean(ClipDescription.EXTRA_IS_SENSITIVE, true)
        }
    }
    clipboard.setPrimaryClip(clip)

    snackFunction("Contraseña copiada en el portapapeles")
}