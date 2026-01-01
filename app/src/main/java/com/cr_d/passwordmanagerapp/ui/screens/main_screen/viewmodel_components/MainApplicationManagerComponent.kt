package com.cr_d.passwordmanagerapp.ui.screens.main_screen.viewmodel_components

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainApplicationManagerComponent(): ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    data class UiState(
        val totalApps: Int = 0
    )

    fun onTotalAppsChange(){}
}