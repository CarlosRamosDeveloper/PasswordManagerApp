package com.cr_d.passwordmanagerapp.ui.screens.main_screen.viewmodel_components

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

import com.cr_d.passwordmanagerapp.data.repository.interfaces.IPasswordRepository
import com.cr_d.passwordmanagerapp.data.seed.SampleData
import com.cr_d.passwordmanagerapp.domain.use_cases.password_use_cases.MassSavePasswordUseCase

class MainPasswordManagerComponent (
    val passwordRepository: IPasswordRepository,
    val massSave: MassSavePasswordUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    data class UiState(
        val totalPasswords: Int = 0,
        val totalWarnings: Int = 0,
    )

    fun onTotalPasswordsChange(){
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    totalPasswords = passwordRepository.findAll().count()
                )
            }
        }
    }

    suspend fun onPopulatePasswords(){
        massSave(SampleData.passwords)
        onTotalPasswordsChange()
    }

    fun onMassDeletePasswords(){
        viewModelScope.launch {
            passwordRepository.massDelete()
            onTotalPasswordsChange()
        }
    }
}