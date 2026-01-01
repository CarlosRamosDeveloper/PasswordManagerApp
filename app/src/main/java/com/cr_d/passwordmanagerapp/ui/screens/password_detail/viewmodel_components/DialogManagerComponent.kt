package com.cr_d.passwordmanagerapp.ui.screens.password_detail.viewmodel_components

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

import com.cr_d.passwordmanagerapp.ui.models.PasswordConfirmDialogData

class DialogManagerComponent : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    data class UiState(
        val dialogData: PasswordConfirmDialogData = PasswordConfirmDialogData()
    )

    fun getData(): PasswordConfirmDialogData{
        val isDeletePasswordShown = _uiState.value.dialogData.isDeletePasswordDialogShown
        val isCopyToDialogShown = _uiState.value.dialogData.isCopyToDialogShown
        val isUpdatePasswordDialogShown = _uiState.value.dialogData.isUpdatePasswordDialogShown
        val isUpdateNotesDialogShown = _uiState.value.dialogData.isUpdateNotesDialogShown
        val isDeleteNotesDialogShown = _uiState.value.dialogData.isDeleteNotesDialogShown

        return PasswordConfirmDialogData(
            isDeletePasswordShown,
            isCopyToDialogShown,
            isUpdatePasswordDialogShown,
            isUpdateNotesDialogShown,
            isDeleteNotesDialogShown
        )
    }

    fun onEnableDeletePasswordDialog(){
        _uiState.update {
            it.copy(
                dialogData = it.dialogData.copy(
                    isDeletePasswordDialogShown = true
                )
            )
        }
    }

    fun onDisableDeletePasswordDialog(){
        _uiState.update {
            it.copy(
                dialogData = it.dialogData.copy(
                    isDeletePasswordDialogShown = false
                )
            )
        }
    }

    fun onEnableCopyDialog(){
        _uiState.update {
            it.copy(
                dialogData = it.dialogData.copy(
                    isCopyToDialogShown = true
                )
            )
        }
    }

    fun onDisableCopyDialog(){
        _uiState.update {
            it.copy(
                dialogData = it.dialogData.copy(
                    isCopyToDialogShown = false
                )
            )
        }
    }

    fun onEnableUpdateDialog(){
        _uiState.update {
            it.copy(
                dialogData = it.dialogData.copy(
                    isUpdatePasswordDialogShown = true
                )
            )
        }
    }

    fun onDisableUpdateDialog(){
        _uiState.update {
            it.copy(
                dialogData = it.dialogData.copy(
                    isUpdatePasswordDialogShown = false
                )
            )
        }
    }

    fun onEnableUpdateNotesDialog(){
        _uiState.update {
            it.copy(
                dialogData = it.dialogData.copy(
                    isUpdateNotesDialogShown = true
                )
            )
        }
    }

    fun onDisableUpdateNotesDialog(){
        _uiState.update {
            it.copy(
                dialogData = it.dialogData.copy(
                    isUpdateNotesDialogShown = false
                )
            )
        }
    }

    fun onEnableDeleteNotesDialog(){
        _uiState.update {
            it.copy(
                dialogData = it.dialogData.copy(
                    isDeleteNotesDialogShown = true
                )
            )
        }
    }

    fun onDisableDeleteNotesDialog(){
        _uiState.update {
            it.copy(
                dialogData = it.dialogData.copy(
                    isDeleteNotesDialogShown = false
                )
            )
        }
    }
}