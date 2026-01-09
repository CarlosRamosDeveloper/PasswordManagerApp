package com.cr_d.passwordmanagerapp.ui.screens.accounts.list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

import com.cr_d.passwordmanagerapp.data.mapper.toUiState
import com.cr_d.passwordmanagerapp.domain.use_cases.DecryptStringUseCase
import com.cr_d.passwordmanagerapp.domain.use_cases.GetAllAccountsUseCase
import com.cr_d.passwordmanagerapp.ui.model.AccountUiState

class AccountListViewModel(
    private val getAllAccountsUseCase: GetAllAccountsUseCase,
    private val decrypt: DecryptStringUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    data class UiState(
        val accounts: List<AccountUiState> = emptyList(),
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
        val parsedAccounts = getAllAccountsUseCase().map{
            it.toUiState(decrypt(it.cipheredAccount))
        }

        _uiState.update {
            it.copy(
                accounts = parsedAccounts
            )
        }
    }
}