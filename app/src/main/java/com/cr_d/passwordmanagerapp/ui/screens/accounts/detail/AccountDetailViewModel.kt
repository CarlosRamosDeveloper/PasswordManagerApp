package com.cr_d.passwordmanagerapp.ui.screens.accounts.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

import com.cr_d.passwordmanagerapp.domain.use_cases.account_use_cases.AccountParseToUiUseCase
import com.cr_d.passwordmanagerapp.domain.use_cases.account_use_cases.DeleteAccountUseCase
import com.cr_d.passwordmanagerapp.ui.model.AccountUiState

class AccountDetailViewModel (
    private val accountId: Long,
    private val parser: AccountParseToUiUseCase,
    private val delete: DeleteAccountUseCase
) : ViewModel(){
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    data class UiState (
        val account: AccountUiState? = null,
        val isDeleteDialogShown: Boolean = false,
    )

    init {
        onRefresh()
    }

    fun onRefresh(){
        viewModelScope.launch {
            loadAccount(accountId)
        }
    }

    fun onEnableDeleteDialog(){
        _uiState.update {
            it.copy(
                isDeleteDialogShown = true
            )
        }
    }

    fun onDisableDeleteDialog(){
        _uiState.update {
            it.copy(
                isDeleteDialogShown = false
            )
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

    fun onDeleteAccount(){
        viewModelScope.launch {
            delete(accountId)
            onDisableDeleteDialog()
        }
    }
}