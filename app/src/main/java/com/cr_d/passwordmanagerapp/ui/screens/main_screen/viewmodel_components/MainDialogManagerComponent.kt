package com.cr_d.passwordmanagerapp.ui.screens.main_screen.viewmodel_components

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

import com.cr_d.passwordmanagerapp.ui.models.MainConfirmDialogData

class MainDialogManagerComponent : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    data class UiState(
        val dialogData: MainConfirmDialogData = MainConfirmDialogData()
    )

    fun getData(): MainConfirmDialogData {
        val isPasswordMassDeleteDialogShown = _uiState.value.dialogData.isPasswordMassDeleteDialogShown
        val isPasswordPopulateDatabaseDialogShown = _uiState.value.dialogData.isPasswordPopulateDatabaseDialogShown
        val isAccountsMassDeleteDialogShown = _uiState.value.dialogData.isAccountsMassDeleteDialogShown
        val isAccountsPopulateDatabaseDialogShown = _uiState.value.dialogData.isAccountsPopulateDatabaseDialogShown
        val isApplicationMassDeleteDialogShown = _uiState.value.dialogData.isApplicationMassDeleteDialogShown
        val isApplicationPopulateDatabaseDialogShown = _uiState.value.dialogData.isApplicationPopulateDatabaseDialogShown

        return MainConfirmDialogData(
            isPasswordMassDeleteDialogShown,
            isPasswordPopulateDatabaseDialogShown,
            isAccountsMassDeleteDialogShown,
            isAccountsPopulateDatabaseDialogShown,
            isApplicationMassDeleteDialogShown,
            isApplicationPopulateDatabaseDialogShown
        )
    }

    fun onEnableMassDeletePasswordDialog(){
        _uiState.update {
            it.copy(
                dialogData = it.dialogData.copy(
                    isPasswordMassDeleteDialogShown = true
                ),
            )
        }
    }

    fun onDisableMassDeletePasswordDialog(){
        _uiState.update {
            it.copy(
                dialogData = it.dialogData.copy(
                    isPasswordMassDeleteDialogShown = false
                ),
            )
        }
    }

    fun onEnablePopulatePasswordDatabaseDialog(){
        _uiState.update {
            it.copy(
                dialogData = it.dialogData.copy(
                    isPasswordPopulateDatabaseDialogShown = true
                ),
            )
        }
    }

    fun onDisablePopulatePasswordDatabaseDialog(){
        _uiState.update {
            it.copy(
                dialogData = it.dialogData.copy(
                    isPasswordPopulateDatabaseDialogShown = false
                ),
            )
        }
    }

    fun onEnableMassDeleteAccountDialog(){
        _uiState.update {
            it.copy(
                dialogData = it.dialogData.copy(
                    isAccountsMassDeleteDialogShown = true
                ),
            )
        }
    }

    fun onDisableMassDeleteAccountDialog(){
        _uiState.update {
            it.copy(
                dialogData = it.dialogData.copy(
                    isAccountsMassDeleteDialogShown = false
                ),
            )
        }
    }

    fun onEnablePopulateAccountDatabaseDialog(){
        _uiState.update {
            it.copy(
                dialogData = it.dialogData.copy(
                    isAccountsPopulateDatabaseDialogShown = true
                ),
            )
        }
    }

    fun onDisablePopulateAccountDatabaseDialog(){
        _uiState.update {
            it.copy(
                dialogData = it.dialogData.copy(
                    isAccountsPopulateDatabaseDialogShown = false
                ),
            )
        }
    }

    fun onEnableMassDeleteApplicationDialog(){
        _uiState.update {
            it.copy(
                dialogData = it.dialogData.copy(
                    isApplicationMassDeleteDialogShown = true
                ),
            )
        }
    }

    fun onDisableMassDeleteApplicationDialog(){
        _uiState.update {
            it.copy(
                dialogData = it.dialogData.copy(
                    isApplicationMassDeleteDialogShown = false
                ),
            )
        }
    }

    fun onEnablePopulateApplicationDatabaseDialog(){
        _uiState.update {
            it.copy(
                dialogData = it.dialogData.copy(
                    isApplicationPopulateDatabaseDialogShown = true
                ),
            )
        }
    }

    fun onDisablePopulateApplicationDatabaseDialog(){
        _uiState.update {
            it.copy(
                dialogData = it.dialogData.copy(
                    isApplicationPopulateDatabaseDialogShown = false
                ),
            )
        }
    }
}