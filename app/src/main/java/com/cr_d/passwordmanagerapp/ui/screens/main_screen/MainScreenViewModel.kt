package com.cr_d.passwordmanagerapp.ui.screens.main_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cr_d.passwordmanagerapp.application.interfaces.IAccountRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

import com.cr_d.passwordmanagerapp.application.interfaces.IPasswordRepository
import com.cr_d.passwordmanagerapp.data.SampleData

class MainScreenViewModel (
    private val passwordRepository: IPasswordRepository,
    private val accountRepository: IAccountRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    data class UiState(
        val isPasswordMassDeleteDialogShown: Boolean = false,
        val isPasswordPopulateDatabaseDialogShown: Boolean = false,
        val isAccountsMassDeleteDialogShown: Boolean = false,
        val isAccountsPopulateDatabaseDialogShown: Boolean = false,
        val totalPasswords: Int = 0,
        val totalWarnings: Int = 0,
        val totalAccounts: Int = 0
    )

    // TODO: Convertir a orquestador -> Separar en account, application, passwords y dialog managers

    init {
        onTotalPasswordsChange()
        Log.d("CreationScreen", "Main -> $this")
    }

    fun onRefresh() {
        onTotalPasswordsChange()
        onTotalAccountsChange()
    }

    fun onTotalPasswordsChange(){
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    totalPasswords = passwordRepository.findAll().count()
                )
            }
        }
    }

    fun onEnableMassDeletePasswordDialog(){
        _uiState.update {
            it.copy(
                isPasswordMassDeleteDialogShown = true
            )
        }
    }

    fun onDisableMassDeletePasswordDialog(){
        _uiState.update {
            it.copy(
                isPasswordMassDeleteDialogShown = false
            )
        }
    }

    fun onEnablePopulatePasswordDatabaseDialog(){
        _uiState.update {
            it.copy(
                isPasswordPopulateDatabaseDialogShown = true
            )
        }
    }

    fun onDisablePopulatePasswordDatabaseDialog(){
        _uiState.update {
            it.copy(
                isPasswordPopulateDatabaseDialogShown = false
            )
        }
    }

    fun onPopulatePasswords(){
        viewModelScope.launch {
            passwordRepository.massSave(SampleData.passwords)
            onTotalPasswordsChange()
            onDisablePopulatePasswordDatabaseDialog()
        }
    }

    fun onMassDeletePasswords(){
        viewModelScope.launch {
            passwordRepository.massDelete()
            onTotalPasswordsChange()
            onDisableMassDeletePasswordDialog()
        }
    }

    // Accounts

    fun onTotalAccountsChange(){
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    totalAccounts = accountRepository.findAll().count()
                )
            }
        }
    }

    fun onEnableMassDeleteAccountDialog(){
        _uiState.update {
            it.copy(
                isAccountsMassDeleteDialogShown = true
            )
        }
    }

    fun onDisableMassDeleteAccountDialog(){
        _uiState.update {
            it.copy(
                isAccountsMassDeleteDialogShown = false
            )
        }
    }

    fun onEnablePopulateAccountDatabaseDialog(){
        _uiState.update {
            it.copy(
                isAccountsPopulateDatabaseDialogShown = true
            )
        }
    }

    fun onDisablePopulateAccountDatabaseDialog(){
        _uiState.update {
            it.copy(
                isAccountsPopulateDatabaseDialogShown = false
            )
        }
    }

    fun onPopulateAccounts(){
        viewModelScope.launch {
            accountRepository.massSave(SampleData.accounts)
            onTotalAccountsChange()
            onDisablePopulateAccountDatabaseDialog()
        }
    }

    fun onMassDeleteAccounts(){
        viewModelScope.launch {
            accountRepository.massDelete()
            onTotalAccountsChange()
            onDisableMassDeleteAccountDialog()
        }
    }
}