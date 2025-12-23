package com.cr_d.passwordmanagerapp.ui.screens.password_detail

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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlin.math.max

import com.cr_d.passwordmanagerapp.R

@Composable
fun PasswordDetailScreen(innerPadding: PaddingValues, context: Context, snackFunction: (String)-> Unit, viewModel: PasswordDetailViewModel){
    Column (modifier = Modifier.padding(innerPadding)){
        PasswordDetailedCard(context, snackFunction, viewModel)
    }
}

@Composable
fun PasswordDetailedCard(context: Context, snackFunction: (String)-> Unit, viewModel: PasswordDetailViewModel){
    val state = viewModel.uiState.collectAsState().value

    state.password?.let { password ->
        Card(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 10.dp)) {
            Row (Modifier.fillMaxSize()){
                TogglePasswordVisibilityButton(viewModel)

                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(1f)) {
                    Text("Account: ${password.account}")
                    if (state.isPasswordShown) Text("Password: ${password.password}")
                    else Text("Password: ********")
                    Text("Fecha de creación: ${password.creationDate}")
                    Text("Última actualización: ${password.lastUpdate}")
                    Text("Puntuación de seguridad ${password.securityScore}")
                }

                CopyToClipboardButton(password.password, context, snackFunction)
            }
        }
    }

}

@Composable
fun TogglePasswordVisibilityButton(viewModel: PasswordDetailViewModel){
    val passwordState = viewModel.uiState.collectAsState().value.isPasswordShown

    Button(
        onClick = viewModel::onVisibilityToggle
    ) {

        val progress by animateFloatAsState(
            targetValue = if (passwordState) 1f else 0f,
            animationSpec = tween(300)
        )

        if(passwordState){
            Icon(
                painter = painterResource(R.drawable.outline_visibility_24),
                contentDescription = null,
                modifier = Modifier.graphicsLayer {
                    scaleY = progress
                    alpha = max(progress, 0.2f)
                    transformOrigin = TransformOrigin(0.5f, 0.5f)
                }
            )
        } else {
            Icon(
                painter = painterResource(R.drawable.outline_visibility_off_24),
                contentDescription = null,
                modifier = Modifier.graphicsLayer {
                    alpha = 1f - progress
                    scaleX = 1f - progress
                }
            )
        }
    }
}

@Composable
fun CopyToClipboardButton(passwordText: String, context: Context, snackFunction: (String)-> Unit){
    Button(
        onClick = {
            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("Copied_Text", passwordText).apply {
                description.extras = PersistableBundle().apply {
                    putBoolean(ClipDescription.EXTRA_IS_SENSITIVE, true)
                }
            }
            clipboard.setPrimaryClip(clip)

            snackFunction("Contraseña copiada en el portapapeles")
        }
    ) {
        Image(
            painterResource(R.drawable.outline_content_copy_24),
            contentDescription = "",
        )
    }
}

