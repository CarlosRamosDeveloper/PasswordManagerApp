package com.cr_d.passwordmanagerapp.ui.screens.passwords_list

import android.util.Log
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

    data class UiState(
        val passwords: List<PasswordUiState> = emptyList(),
    )

    init {
        viewModelScope.launch {
            loadPasswords()
            Log.d("CreationScreen", "List -> $this")
        }
    }

    fun onRefresh(){
        viewModelScope.launch {
            loadPasswords()
        }
    }

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