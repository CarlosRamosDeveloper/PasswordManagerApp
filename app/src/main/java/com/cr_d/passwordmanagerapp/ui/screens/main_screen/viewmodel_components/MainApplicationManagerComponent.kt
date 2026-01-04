package com.cr_d.passwordmanagerapp.ui.screens.main_screen.viewmodel_components

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

import com.cr_d.passwordmanagerapp.data.repository.IApplicationRepository
import com.cr_d.passwordmanagerapp.data.seed.SampleData

class MainApplicationManagerComponent(
    private val appRepository: IApplicationRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    data class UiState(
        val totalApps: Int = 0
    )

    fun onTotalAppsChange(){
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    totalApps = appRepository.findAll().count()
                )
            }
        }
    }

    fun onPopulateApps(){
        viewModelScope.launch {
            appRepository.massSave(SampleData.applications)
            onTotalAppsChange()
        }
    }

    fun onMassDeleteApps(){
        viewModelScope.launch {
            appRepository.massDelete()
            onTotalAppsChange()
        }
    }
}