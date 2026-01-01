package com.cr_d.passwordmanagerapp.ui.screens.main_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

import com.cr_d.passwordmanagerapp.ui.screens.main_screen.viewmodel_components.MainAccountManagerComponent
import com.cr_d.passwordmanagerapp.ui.screens.main_screen.viewmodel_components.MainApplicationManagerComponent
import com.cr_d.passwordmanagerapp.ui.screens.main_screen.viewmodel_components.MainDialogManagerComponent
import com.cr_d.passwordmanagerapp.ui.screens.main_screen.viewmodel_components.MainPasswordManagerComponent

class MainScreenViewModel (
    private val dialogManager: MainDialogManagerComponent,
    private val passwordManager: MainPasswordManagerComponent,
    private val accountManager: MainAccountManagerComponent,
    private val appManager: MainApplicationManagerComponent
) : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())

    val uiState: StateFlow<UiState> = combine(
        _uiState,
        dialogManager.uiState,
        passwordManager.uiState,
        accountManager.uiState,
        appManager.uiState
    ) { baseState, dialogManager, passwordManager, accountManager, appManager ->
        baseState.copy(
            isPasswordMassDeleteDialogShown = dialogManager.dialogData.isPasswordMassDeleteDialogShown,
            isPasswordPopulateDatabaseDialogShown = dialogManager.dialogData.isPasswordPopulateDatabaseDialogShown,
            isAccountsMassDeleteDialogShown = dialogManager.dialogData.isAccountsMassDeleteDialogShown,
            isAccountsPopulateDatabaseDialogShown = dialogManager.dialogData.isAccountsPopulateDatabaseDialogShown,
            totalPasswords = passwordManager.totalPasswords,
            totalWarnings = passwordManager.totalWarnings,
            totalAccounts = accountManager.totalAccounts,
            totalApps = appManager.totalApps
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
        val totalAccounts: Int = 0,
        val totalApps: Int = 0
    )

    init {
        onRefresh()
        Log.d("CreationScreen", "Main -> $this")
    }

    fun onRefresh() {
        onTotalPasswordsChange()
        onTotalAccountsChange()
        getData()
        onTotalAppsChange()
    }

    // Passwords
    fun onTotalPasswordsChange() = passwordManager.onTotalPasswordsChange()
    fun onPopulatePasswords() = passwordManager.onPopulatePasswords()
    fun onMassDeletePasswords() = passwordManager.onMassDeletePasswords()

    // Accounts
    fun onTotalAccountsChange() = accountManager.onTotalAccountsChange()
    fun onPopulateAccounts() = accountManager.onPopulateAccounts()
    fun onMassDeleteAccounts() = accountManager.onMassDeleteAccounts()

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

    // ApplicationManager
    fun onTotalAppsChange() = appManager.onTotalAppsChange()
}