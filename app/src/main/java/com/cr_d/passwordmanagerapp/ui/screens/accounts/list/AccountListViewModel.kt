package com.cr_d.passwordmanagerapp.ui.screens.accounts.list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

import com.cr_d.passwordmanagerapp.domain.entities.Account

class AccountListViewModel(
// private val getAllAccountsUseCase -> //TODO: Agregar el UC para obtener cuentas
): ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    //TODO: Crear la clase AccountUiState -> Agregar el mapper
    data class UiState(
        val accounts: List<Account> = emptyList(),
    )

    init {
        viewModelScope.launch {
            onRefresh()
            Log.d("CreationScreen", "AccountList\nList -> $this")
        }
    }

    fun onRefresh(){
        viewModelScope.launch {
            loadAccounts()
        }
    }

    suspend fun loadAccounts(){
        // parsear las cuentas a UiState
        // Actualizar el _uiState
    }
}