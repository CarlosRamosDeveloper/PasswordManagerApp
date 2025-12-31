package com.cr_d.passwordmanagerapp.ui.screens.password_detail.viewmodel_components

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DialogManagerComponent : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    data class UiState(
        val isDeletePasswordDialogShown: Boolean = false,
        val isCopyToDialogShown: Boolean = false,
        val isUpdatePasswordDialogShown: Boolean = false,
        val isUpdateNotesDialogShown: Boolean = false,
        val isDeleteNotesDialogShown: Boolean = false
    )


    // Dialog Manager
    fun onEnableDeletePasswordDialog(){
        _uiState.update {
            it.copy(
                isDeletePasswordDialogShown = true
            )
        }
    }
    // Dialog Manager
    fun onDisableDeletePasswordDialog(){
        _uiState.update {
            it.copy(
                isDeletePasswordDialogShown = false
            )
        }
    }

    fun onEnableCopyDialog(){
        _uiState.update {
            it.copy(
                isCopyToDialogShown = true
            )
        }
    }

    fun onDisableCopyDialog(){
        _uiState.update {
            it.copy(
                isCopyToDialogShown = false
            )
        }
    }

    fun onEnableUpdateDialog(){
        _uiState.update {
            it.copy(
                isUpdatePasswordDialogShown = true
            )
        }
    }

    fun onDisableUpdateDialog(){
        _uiState.update {
            it.copy(
                isUpdatePasswordDialogShown = false
            )
        }
    }

    fun onEnableUpdateNotesDialog(){
        _uiState.update {
            it.copy(
                isUpdateNotesDialogShown = true
            )
        }
    }

    fun onDisableUpdateNotesDialog(){
        _uiState.update {
            it.copy(
                isUpdateNotesDialogShown = false
            )
        }
    }

    fun onEnableDeleteNotesDialog(){
        _uiState.update {
            it.copy(
                isDeleteNotesDialogShown = true
            )
        }
    }

    fun onDisableDeleteNotesDialog(){
        _uiState.update {
            it.copy(
                isDeleteNotesDialogShown = false
            )
        }
    }
}