package com.cr_d.passwordmanagerapp.ui.screens.applications.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

import com.cr_d.passwordmanagerapp.domain.use_cases.application_use_cases.GetAllApplicationsUseCase
import com.cr_d.passwordmanagerapp.ui.model.ApplicationUiState

class ApplicationListViewModel (
    private val getAllApplications: GetAllApplicationsUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    data class UiState(
        val applications: List<ApplicationUiState> = emptyList(),
    )

    init {
        onRefresh()
    }

    fun onRefresh(){
        viewModelScope.launch {
            loadApplications()
        }
    }

    suspend fun loadApplications(){
        val applications = getAllApplications()

        _uiState.update {
            it.copy(
                applications = applications
            )
        }
    }
}