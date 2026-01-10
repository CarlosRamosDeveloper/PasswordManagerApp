package com.cr_d.passwordmanagerapp.ui.screens.applications.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.StateFlow

import com.cr_d.passwordmanagerapp.domain.use_cases.application_use_cases.SaveApplicationUseCase
import com.cr_d.passwordmanagerapp.ui.model.ApplicationUiState
import kotlinx.coroutines.launch

class CreateApplicationViewModel (
    private val save: SaveApplicationUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    data class UiState(
        val application: ApplicationUiState = ApplicationUiState(),
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

    fun onSaveApplication(){
        viewModelScope.launch {
            val app = _uiState.value.application
            val application = ApplicationUiState(
                applicationName = app.applicationName,
                applicationUrl = app.applicationUrl,
                notes = app.notes
            )
            save(application)
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