package com.cr_d.passwordmanagerapp.ui.screens.main_screen.viewmodel_components

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cr_d.passwordmanagerapp.data.repository.IAccountRepository
import com.cr_d.passwordmanagerapp.data.seed.SampleData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainAccountManagerComponent(
    val accountRepository: IAccountRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    data class UiState(
        val totalAccounts: Int = 0
    )

    fun onTotalAccountsChange(){
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    totalAccounts = accountRepository.findAll().count()
                )
            }
        }
    }

    fun onPopulateAccounts(){
        viewModelScope.launch {
            accountRepository.massSave(SampleData.accounts)
            onTotalAccountsChange()
        }
    }

    fun onMassDeleteAccounts(){
        viewModelScope.launch {
            accountRepository.massDelete()
            onTotalAccountsChange()
        }
    }
}