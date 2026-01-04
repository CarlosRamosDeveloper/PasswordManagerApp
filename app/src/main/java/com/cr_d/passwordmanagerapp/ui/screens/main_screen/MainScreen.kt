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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch

import com.cr_d.passwordmanagerapp.ui.common_components.FullWidthButton
import com.cr_d.passwordmanagerapp.ui.common_components.SectionTitle
import com.cr_d.passwordmanagerapp.ui.model.AppConfig

@Composable
fun MainScreen(innerPadding: PaddingValues, viewModel: MainScreenViewModel){
    val scope = rememberCoroutineScope()
    val state = viewModel.uiState.collectAsStateWithLifecycle().value

    LaunchedEffect(Unit) {
        scope.launch {
            viewModel.onRefresh()
        }
    }

    Column(
        modifier= Modifier
            .padding(innerPadding)
            .fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PasswordSection(
            totalStoredPasswords = state.totalPasswords,
            totalWarnings = state.totalWarnings,
            viewModel = viewModel
        )

        AccountSection(
            totalStoredAccounts = state.totalAccounts,
            viewModel = viewModel
        )

        ApplicationSection(
            totalStoredApps = state.totalApps,
            viewModel = viewModel
        )

        MainConfirmDialogs(
            dialogData = state.dialogData,
            viewModel = viewModel
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

@Composable
fun PasswordSection(
    totalStoredPasswords: Int,
    totalWarnings: Int,
    viewModel: MainScreenViewModel
){
    Row (Modifier
        .fillMaxWidth()
        .padding(AppConfig.HORIZONTAL_FRAME_PADDING), horizontalArrangement = Arrangement.SpaceBetween){
        MainCard("Contraseñas",totalStoredPasswords.toString(), Icons.Default.Key, modifier = Modifier.weight(0.45f))
        MainCard("Alertas",totalWarnings.toString(), Icons.Default.Warning, modifier = Modifier.weight(0.45f))
    }

    FullWidthButton("Generar contraseñas de prueba", viewModel::onEnablePopulatePasswordDatabaseDialog)
    if(totalStoredPasswords > 0) FullWidthButton("Eliminar todas las contraseñas", viewModel::onEnableMassDeletePasswordDialog)
}

@Composable
fun AccountSection(
    totalStoredAccounts: Int,
    viewModel: MainScreenViewModel
) {
    Row (Modifier
        .fillMaxWidth()
        .padding(AppConfig.HORIZONTAL_FRAME_PADDING), horizontalArrangement = Arrangement.SpaceBetween){
        MainCard("Cuentas",totalStoredAccounts.toString(), Icons.Default.Key, modifier = Modifier.weight(0.45f))
    }

    FullWidthButton("Generar cuentas de prueba", viewModel::onEnablePopulateAccountDatabaseDialog)
    if(totalStoredAccounts > 0) FullWidthButton("Eliminar todas las cuentas", viewModel::onEnableMassDeleteAccountDialog)
}

@Composable
fun ApplicationSection(
    totalStoredApps: Int,
    viewModel: MainScreenViewModel
){
    Row (Modifier
        .fillMaxWidth()
        .padding(AppConfig.HORIZONTAL_FRAME_PADDING), horizontalArrangement = Arrangement.SpaceBetween){
        MainCard("Aplicaciones",totalStoredApps.toString(), Icons.Default.Key, modifier = Modifier.weight(0.45f))
    }

    FullWidthButton("Generar aplicaciones de prueba", viewModel::onEnablePopulateApplicationDatabaseDialog)
    if(totalStoredApps > 0) FullWidthButton("Eliminar todas las aplicaciones", viewModel::onEnableMassDeleteApplicationDialog)
}