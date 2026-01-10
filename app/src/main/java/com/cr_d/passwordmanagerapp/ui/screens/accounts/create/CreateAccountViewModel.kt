package com.cr_d.passwordmanagerapp.ui.screens.accounts.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

import com.cr_d.passwordmanagerapp.data.dto.AccountCreationData
import com.cr_d.passwordmanagerapp.domain.use_cases.account_use_cases.SaveAccountUseCase
import com.cr_d.passwordmanagerapp.ui.model.AccountUiState

class CreateAccountViewModel (
    private val save: SaveAccountUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()
    val isSaveEnabled: StateFlow<Boolean> = _uiState.map {
        it.account.account.isNotBlank()
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(1_000),
        false
    )

    data class UiState(
        val account: AccountUiState = AccountUiState(),
        val isSaveDialogShown: Boolean = false,
    )

    fun onAccountNameChange(value: String){
        _uiState.update {
            it.copy(
                account = it.account.copy(
                    account = value
                )
            )
        }
    }

    fun onAccountNotesChange(value: String){
        _uiState.update {
            it.copy(
                account = it.account.copy(
                    notes = value
                )
            )
        }
    }

    fun onEnableSaveDialog(){
        _uiState.update {
            it.copy(
                isSaveDialogShown = true
            )
        }
    }

    fun onDisableSaveDialog(){
        _uiState.update {
            it.copy(
                isSaveDialogShown = false
            )
        }
    }

    fun onSaveAccount(){
        viewModelScope.launch {
            val acc = _uiState.value.account
            val data = AccountCreationData(
                account = acc.account,
                notes = acc.notes
            )
            save(data)
            onDisableSaveDialog()
            resetStatus()
        }
    }

    fun resetStatus(){
        _uiState.update {
            it.copy(
                account = AccountUiState()
            )
        }
    }
}