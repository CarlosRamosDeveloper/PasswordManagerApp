package com.cr_d.passwordmanagerapp.ui.screens.passwords_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

import com.cr_d.passwordmanagerapp.application.use_cases.GetAllPasswordsUseCase
import com.cr_d.passwordmanagerapp.data.mapper.toUiState
import com.cr_d.passwordmanagerapp.ui.models.PasswordUiState

class PasswordListViewModel(
    private val getAllPasswordsUseCase: GetAllPasswordsUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            loadPasswords()
        }
    }

    data class UiState(
        val passwords: List<PasswordUiState> = emptyList(),
    )

    suspend fun loadPasswords(){
        val parsedPasswords = getAllPasswordsUseCase().map {
            it.toUiState()
        }

        _uiState.update {
            it.copy(
                passwords = parsedPasswords
            )
        }
    }
}