package com.cr_d.passwordmanagerapp.ui.screens.main_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cr_d.passwordmanagerapp.application.interfaces.IAccountRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

import com.cr_d.passwordmanagerapp.application.interfaces.IPasswordRepository
import com.cr_d.passwordmanagerapp.data.SampleData
import com.cr_d.passwordmanagerapp.ui.screens.main_screen.viewmodel_components.MainDialogManagerComponent

class MainScreenViewModel (
    private val passwordRepository: IPasswordRepository,
    private val accountRepository: IAccountRepository,
    private val dialogManager: MainDialogManagerComponent,
) : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())

    val uiState: StateFlow<UiState> = combine(
        _uiState,
        dialogManager.uiState
    ) { baseState, dialogManager ->
        baseState.copy(
            isPasswordMassDeleteDialogShown = dialogManager.dialogData.isPasswordMassDeleteDialogShown,
            isPasswordPopulateDatabaseDialogShown = dialogManager.dialogData.isPasswordPopulateDatabaseDialogShown,
            isAccountsMassDeleteDialogShown = dialogManager.dialogData.isAccountsMassDeleteDialogShown,
            isAccountsPopulateDatabaseDialogShown = dialogManager.dialogData.isAccountsPopulateDatabaseDialogShown
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = UiState()
    )

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
        getData()
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

    // DialogManager
    fun getData() = dialogManager.getData()
    fun onEnableMassDeletePasswordDialog() = dialogManager.onEnableMassDeletePasswordDialog()

    fun onDisableMassDeletePasswordDialog() = dialogManager.onDisableMassDeletePasswordDialog()

    fun onEnablePopulatePasswordDatabaseDialog() = dialogManager.onEnablePopulatePasswordDatabaseDialog()

    fun onDisablePopulatePasswordDatabaseDialog() = dialogManager.onDisablePopulatePasswordDatabaseDialog()

    fun onEnableMassDeleteAccountDialog() = dialogManager.onEnableMassDeleteAccountDialog()

    fun onDisableMassDeleteAccountDialog() = dialogManager.onDisableMassDeleteAccountDialog()

    fun onEnablePopulateAccountDatabaseDialog() = dialogManager.onEnablePopulateAccountDatabaseDialog()

    fun onDisablePopulateAccountDatabaseDialog() = dialogManager.onDisablePopulateAccountDatabaseDialog()
}