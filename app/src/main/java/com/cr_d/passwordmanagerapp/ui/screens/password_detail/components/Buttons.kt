package com.cr_d.passwordmanagerapp.ui.screens.password_detail.components

import android.content.ClipData
import android.content.ClipDescription
import android.content.ClipboardManager
import android.content.Context
import android.os.PersistableBundle
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlin.math.max

import com.cr_d.passwordmanagerapp.R
import com.cr_d.passwordmanagerapp.ui.screens.password_detail.PasswordDetailViewModel

@Composable
fun TogglePasswordVisibilityButton(isPasswordShown: Boolean, viewModel: PasswordDetailViewModel){
    Button(
        onClick = viewModel::onVisibilityToggle
    ) {

        val progress by animateFloatAsState(
            targetValue = if (isPasswordShown) 1f else 0f,
            animationSpec = tween(300)
        )

        if(isPasswordShown){
            Row (horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(R.drawable.outline_visibility_off_24),
                    contentDescription = null,
                    modifier = Modifier.graphicsLayer {
                        scaleY = progress
                        alpha = max(progress, 0.2f)
                        transformOrigin = TransformOrigin(0.5f, 0.5f)
                    }
                )
                Text("Ocultar", modifier = Modifier.padding(horizontal = 15.dp))
            }
        } else {
            Row (horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(R.drawable.outline_visibility_24),
                    contentDescription = null,
                    modifier = Modifier.graphicsLayer {
                        alpha = 1f - progress
                        scaleX = 1f - progress
                    }
                )
                Text("Mostrar", modifier = Modifier.padding(horizontal = 15.dp))
            }
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
        Row (horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
            Text("Copiar", modifier = Modifier.padding(horizontal = 15.dp))
            Image(
                painterResource(R.drawable.outline_content_copy_24),
                contentDescription = "",
            )
        }
    }
}

@Composable
fun DeletePasswordButton(snackFunction: (String)-> Unit, viewModel: PasswordDetailViewModel, navController: NavController){
    Button(
        onClick = {
            viewModel.onDeletePassword()
            snackFunction("Contraseña eliminada")
            navController.navigate("ShowPasswordScreen")
        }
    ) {
        Text("Eliminar")
    }
}

@Composable
fun PasswordGenerationToggle(isGeneratePasswordEnabled: Boolean, viewModel: PasswordDetailViewModel){
    FullWidthButton(
        if(isGeneratePasswordEnabled) "Desactivar generación de contraseñas" else "Activar generación de contraseñas",
        { viewModel.onGeneratePasswordSectionToggle() },
        40
    )
}

@Composable
fun UpdatePasswordButton(viewModel: PasswordDetailViewModel, snackFunction: (String)-> Unit){
    FullWidthButton("Actualizar contraseña", {
        viewModel.onUpdatePassword()
        snackFunction("Contraseña actualizada correctamente")
        viewModel.onEnableFullInfoMode()
    })
}

@Composable
fun GeneratePasswordButton(viewModel: PasswordDetailViewModel){
    FullWidthButton("Generar contraseña", viewModel::onGeneratePassword)
}
