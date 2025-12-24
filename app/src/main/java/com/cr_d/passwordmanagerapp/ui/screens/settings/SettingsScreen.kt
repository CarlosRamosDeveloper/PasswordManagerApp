package com.cr_d.passwordmanagerapp.ui.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDate

import com.cr_d.passwordmanagerapp.ui.models.DateFormatOption
import com.cr_d.passwordmanagerapp.ui.models.formatAs

@Composable
fun SettingsScreen(innerPadding: PaddingValues, viewModel: SettingsViewModel){
    Column(modifier = Modifier
        .padding(innerPadding)
        .fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Opciones", fontSize = 30.sp, modifier = Modifier.padding(vertical = 10.dp))

        dateOptions(viewModel)
    }
}

@Composable
fun dateOptions(viewModel: SettingsViewModel){
    val state = viewModel.settings.collectAsState().value
    val today = LocalDate.now()

    Text("Formato de fecha")
    Row {
        Row (verticalAlignment = Alignment.CenterVertically){
            RadioButton(
                selected = state.dateFormat == DateFormatOption.YMD,
                onClick = { viewModel.setDateFormat(DateFormatOption.YMD) }
            )
            Text("Año / Mes / Día")
        }
        Row (verticalAlignment = Alignment.CenterVertically){
            RadioButton(
                selected = state.dateFormat == DateFormatOption.DMY,
                onClick = { viewModel.setDateFormat(DateFormatOption.DMY) }
            )
            Text("Día / Mes / Año")
        }
    }
    Text(today.formatAs(state.dateFormat))
}
