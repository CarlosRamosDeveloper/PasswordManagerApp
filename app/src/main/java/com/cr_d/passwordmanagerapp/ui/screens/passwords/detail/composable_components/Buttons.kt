package com.cr_d.passwordmanagerapp.ui.screens.passwords.detail.composable_components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
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
import kotlin.math.max

import com.cr_d.passwordmanagerapp.R
import com.cr_d.passwordmanagerapp.ui.common_components.FullWidthButton

@Composable
fun TogglePasswordVisibilityButton(isPasswordShown: Boolean, onclick: () -> Unit){
    Button(
        onClick = { onclick() }
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
fun PasswordGenerationToggle(isGeneratePasswordEnabled: Boolean, onclick: () -> Unit){
    FullWidthButton(
        if (isGeneratePasswordEnabled) "Desactivar generación de contraseñas" else "Activar generación de contraseñas",
        { onclick() },
        40
    )
}
