package com.cr_d.passwordmanagerapp.ui.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class FabState(
    val icon: ImageVector,
    val color: Color?,
    val isEnabled: Boolean = true,
    val onclick: () -> Unit
)
