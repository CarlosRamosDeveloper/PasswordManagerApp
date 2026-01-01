package com.cr_d.passwordmanagerapp.ui.screens.password_detail.viewmodel_components

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

import com.cr_d.passwordmanagerapp.ui.models.PasswordDetailUiMode

class UiManagerComponent(

): ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    data class UiState(
        val isPasswordShown: Boolean = false,
        val mode: PasswordDetailUiMode = PasswordDetailUiMode.BASIC_INFO_MODE,
        val isGeneratePasswordEnabled: Boolean = false,
        val errorMessage: String = ""
    )

    fun onLoad(){

    }

    fun onEnablePasswordVisibility(){
        _uiState.update {
            it.copy(
                isPasswordShown = true,
            )
        }
    }

    fun onDisablePasswordVisibility(){
        _uiState.update {
            it.copy(
                isPasswordShown = false,
            )
        }
    }

    fun onGeneratePasswordSectionToggle () {
        _uiState.update {
            it.copy(
                isGeneratePasswordEnabled = !uiState.value.isGeneratePasswordEnabled
            )
        }
    }

    fun onEnableEditMode() {
        _uiState.update {
            it.copy(
                mode = PasswordDetailUiMode.EDIT_MODE,
                isGeneratePasswordEnabled = false,
                isPasswordShown = false
            )
        }
    }

    fun onEnableBasicInfoMode(){
        _uiState.update {
            it.copy(
                mode = PasswordDetailUiMode.BASIC_INFO_MODE,
                isGeneratePasswordEnabled = false,
                isPasswordShown = false
            )
        }
    }

    fun onEnableFullInfoMode(){
        _uiState.update {
            it.copy(
                mode = PasswordDetailUiMode.FULL_INFO_MODE,
                isGeneratePasswordEnabled = false,
                isPasswordShown = false
            )
        }
    }

    fun onNewErrorMessage(error:String){
        _uiState.update {
            it.copy(
                errorMessage = error
            )
        }
    }

    fun onCleanError(){
        _uiState.update {
            it.copy(
                errorMessage = ""
            )
        }
    }
}

