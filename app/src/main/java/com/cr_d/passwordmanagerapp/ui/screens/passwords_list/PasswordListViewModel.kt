package com.cr_d.passwordmanagerapp.ui.screens.passwords_list

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

import com.cr_d.passwordmanagerapp.application.interfaces.IPasswordRepository
import com.cr_d.passwordmanagerapp.domain.value_objects.PasswordData

class PasswordListViewModel(
    repository: IPasswordRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(
        UiState(passwords = repository.findAll())
    )
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    data class UiState(
        val passwords: List<PasswordData> = emptyList(),
    )
}