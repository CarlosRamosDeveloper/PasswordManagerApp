package com.cr_d.passwordmanagerapp.ui.screens.password_detail.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.cr_d.passwordmanagerapp.ui.models.AppConfig

@Composable
fun InfoCard(content: @Composable ColumnScope.() -> Unit){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = AppConfig.HORIZONTAL_FRAME_PADDING, vertical = 10.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally, content = content)
    }
}