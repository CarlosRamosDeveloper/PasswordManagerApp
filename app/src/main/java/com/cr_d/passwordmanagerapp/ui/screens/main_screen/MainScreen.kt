package com.cr_d.passwordmanagerapp.ui.screens.main_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextButton

import com.cr_d.passwordmanagerapp.ui.common_components.FullWidthButton
import com.cr_d.passwordmanagerapp.ui.common_components.SectionTitle
import com.cr_d.passwordmanagerapp.ui.models.AppConfig

@Composable
fun MainScreen(innerPadding: PaddingValues, viewModel: MainScreenViewModel){
    val state = viewModel.uiState.collectAsStateWithLifecycle().value

    Column(
        modifier= Modifier
            .padding(innerPadding)
            .fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row (Modifier
            .fillMaxWidth()
            .padding(AppConfig.HORIZONTAL_FRAME_PADDING), horizontalArrangement = Arrangement.SpaceBetween){
            MainCard("Contraseñas",state.totalPasswords.toString(), Icons.Default.Key, modifier = Modifier.weight(0.45f))
            MainCard("Alertas",state.totalWarnings.toString(), Icons.Default.Warning, modifier = Modifier.weight(0.45f))
        }

        FullWidthButton("Generar contraseñas de prueba", viewModel::onEnablePopulateDatabaseDialog)
        FullWidthButton("Eliminar contraseñas", viewModel::onEnableMassDeleteDialog)

        if(state.isPopulateDatabaseDialogShown) ConfirmMassDeleteDialog(
            title = "Poblar la base de datos",
            message = "¿Inyectar información de prueba en la base de datos?",
            confirmButtonText = "Poblar la base de datos",
            onConfirm = viewModel::onPopulate,
            onDisable = viewModel::onDisablePopulateDatabaseDialog,
            onDismiss = viewModel::onDisablePopulateDatabaseDialog
        )

        if(state.isMassDeleteDialogShown) ConfirmMassDeleteDialog(
            title = "Eliminar todas las contraseñas",
            message = "Este paso no se puede deshacer, ¿está seguro?",
            confirmButtonText = "Eliminar todas las contraseñas",
            onConfirm = viewModel::onMassDelete,
            onDisable = viewModel::onDisableMassDeleteDialog,
            onDismiss = viewModel::onDisableMassDeleteDialog
        )
    }
}

@Composable
fun MainCard(title: String, value: String, icon: ImageVector, modifier: Modifier = Modifier){
    Card (modifier = modifier.padding(10.dp)){
        Row (modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Column (verticalArrangement = Arrangement.Center){
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    modifier = Modifier.padding(top = 10.dp)
                )
                SectionTitle(title)
            }
            Text(value, fontSize = 20.sp, modifier = Modifier.padding(horizontal = 5.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmMassDeleteDialog(
    title: String,
    message: String,
    confirmButtonText: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    onDisable: () -> Unit,
    dismissButtonText: String = "Atrás"
){
    AlertDialog(
        onDismissRequest = onDisable,
        title = { Text(text = title) },
        text = { Text(text = message) },
        confirmButton =
            { TextButton(onClick = onConfirm)  { Text(confirmButtonText) } },
        dismissButton =
            { TextButton(onClick = onDismiss)  { Text(dismissButtonText) } }
    )
}