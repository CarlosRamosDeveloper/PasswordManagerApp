package com.cr_d.passwordmanagerapp.ui.screens.accounts.list

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import kotlinx.coroutines.launch

import com.cr_d.passwordmanagerapp.ui.model.AccountUiState

@Composable
fun AccountListScreen(innerPadding: PaddingValues, navController: NavController, viewModel: AccountListViewModel){
    val scope = rememberCoroutineScope()
    val accounts = viewModel.uiState.collectAsStateWithLifecycle().value.accounts

    LaunchedEffect(Unit) {
        scope.launch {
            viewModel.onRefresh()
        }
    }

    AccountCardsList(innerPadding, navController, accounts)
}

@Composable
fun AccountCard(account: AccountUiState, navController: NavController){
    val verticalPadding = 10.dp
    val horizontalPadding = 20.dp
    val totalMessage: String = when (account.passwords.count()) {
        0 -> "No utilizado en ninguna aplicación"
        1 -> "Usado en 1 aplicación"
        else -> "Usado en un total de ${account.passwords.count()} aplicaciones"
    }

    Row (modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 20.dp, vertical = 10.dp), verticalAlignment = Alignment.CenterVertically){
        Box (modifier = Modifier
            .size(75.dp)
            .clip(CircleShape)
            .background(Color.White)
            .border(2.dp, Color.Gray, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                account.account.first().uppercaseChar().toString(),
                fontWeight = FontWeight.Bold,
                fontSize = 45.sp,
                color = Color.Black,
            )
        }
        Column (Modifier.weight(1f)){
            Text(
                account.account, fontSize = 20.sp, fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(
                    vertical = verticalPadding,
                    horizontal = horizontalPadding
                )
            )
            Spacer(Modifier.weight(1f))
            Text(
                totalMessage,
                modifier = Modifier.padding(
                    vertical = verticalPadding,
                    horizontal = horizontalPadding
                )
            )
        }
        Column (Modifier.align(Alignment.CenterVertically)){
            Icon(
                Icons.AutoMirrored.Filled.ArrowRight,
                contentDescription = "",
                modifier = Modifier
                    .clickable(
                        onClick = { navController.navigate("AccountDetailScreen/${account.id}") }
                    )
                    .size(50.dp)
            )
        }
    }
    HorizontalDivider(thickness = 2.dp, modifier = Modifier.fillMaxWidth())
}

@Composable
fun AccountCardsList(innerPadding: PaddingValues, navController: NavController, accounts: List<AccountUiState>){

    LazyColumn(Modifier.padding(innerPadding)) {
        items(accounts) { acc ->
            AccountCard(acc, navController)
        }
    }
}
