package com.cr_d.passwordmanagerapp.ui.screens.applications.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

import com.cr_d.passwordmanagerapp.domain.use_cases.application_use_cases.SaveApplicationUseCase
import com.cr_d.passwordmanagerapp.ui.model.ApplicationUiState

class CreateApplicationViewModel (
    private val save: SaveApplicationUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()
    val isSaveEnabled: StateFlow<Boolean> = _uiState.map {
        it.application.applicationName.isNotBlank()
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(1_000),
        false
    )

    data class UiState(
        val application: ApplicationUiState = ApplicationUiState(),
        val isSaveDialogShown: Boolean = false,
    )

    fun onAppNameChange(value: String){
        _uiState.update {
            it.copy(
                application = it.application.copy(
                    applicationName = value
                )
            )
        }
    }

    fun onUrlChange(value: String){
        _uiState.update {
            it.copy(
                application = it.application.copy(
                    applicationUrl = value
                )
            )
        }
    }

    fun onNotesChange(value: String) {
        _uiState.update {
            it.copy(
                application = it.application.copy(
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

    fun onSaveApplication(){
        viewModelScope.launch {
            val app = _uiState.value.application
            val application = ApplicationUiState(
                applicationName = app.applicationName,
                applicationUrl = app.applicationUrl,
                notes = app.notes
            )
            save(application)
            onDisableSaveDialog()
            resetStatus()
        }
    }

    fun resetStatus(){
        _uiState.update {
            it.copy(
                application = ApplicationUiState()
            )
        }
    }
}