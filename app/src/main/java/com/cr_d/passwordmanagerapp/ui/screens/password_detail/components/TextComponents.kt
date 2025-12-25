package com.cr_d.passwordmanagerapp.ui.screens.password_detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ApplicationTitle(appTitle : String){
    Text(appTitle, fontSize = 50.sp, modifier = Modifier.padding(5.dp))
}

@Composable
fun CardTitle(cardTitle: String){
    Text(cardTitle, fontSize = 35.sp, modifier = Modifier.padding(vertical = 10.dp))
}

@Composable
fun SectionTitle(sectionTitle: String){
    Text(sectionTitle, fontSize = 25.sp, modifier = Modifier.padding(vertical = 10.dp))
}

@Composable
fun CustomRow(fieldName: String, value: String, showDivider: Boolean = true){
    Row(Modifier
        .fillMaxWidth()
        .padding(horizontal = 20.dp, vertical = 5.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(fieldName)
        Spacer(modifier = Modifier.weight(1f))
        Text(value)
    }
    if (showDivider) HorizontalDivider(thickness = 2.dp)
}

@Composable
fun CustomCheck(fieldName: String, isTrue: Boolean) {
    Row(Modifier
        .fillMaxWidth()
        .padding(horizontal = 20.dp, vertical = 5.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(fieldName)
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            if (isTrue) Icons.Default.Check
            else Icons.Default.Close,
            contentDescription = ""
        )
    }
    HorizontalDivider(thickness = 2.dp)
}