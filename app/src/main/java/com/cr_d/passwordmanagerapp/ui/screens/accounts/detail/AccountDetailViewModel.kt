package com.cr_d.passwordmanagerapp.ui.screens.accounts.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

import com.cr_d.passwordmanagerapp.domain.use_cases.account_use_cases.AccountParseToUiUseCase
import com.cr_d.passwordmanagerapp.ui.model.AccountUiState

class AccountDetailViewModel (
    private val accountId: Long,
    private val parser: AccountParseToUiUseCase
) : ViewModel(){
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    data class UiState (
        val account: AccountUiState? = null
    )

    init {
        onRefresh()
    }

    fun onRefresh(){
        viewModelScope.launch {
            loadAccount(accountId)
        }
    }

    suspend fun loadAccount(accountId: Long) {
        val account = parser(accountId)
        _uiState.update {
            it.copy(
                account = account
            )
        }
    }
}