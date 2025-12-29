package com.cr_d.passwordmanagerapp.ui.screens.main_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cr_d.passwordmanagerapp.application.interfaces.IPasswordRepository
import com.cr_d.passwordmanagerapp.data.SampleData
import com.cr_d.passwordmanagerapp.domain.entities.PasswordPolicy
import com.cr_d.passwordmanagerapp.ui.models.PasswordUiState
import com.cr_d.passwordmanagerapp.ui.screens.create_password.CreatePasswordViewModel
import com.cr_d.passwordmanagerapp.ui.screens.create_password.CreatePasswordViewModel.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainScreenViewModel (
    val repository: IPasswordRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    data class UiState(
        val isDialogShown: Boolean = false,
        val totalPasswords: Int = 0
    )

    init {
        onTotalPasswordsChange()
    }

    fun onTotalPasswordsChange(){
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    totalPasswords = repository.findAll().count()
                )
            }
        }
    }

    fun onEnableDialog(){
        _uiState.update {
            it.copy(
                isDialogShown = true
            )
        }
    }

    fun onDisableDialog(){
        _uiState.update {
            it.copy(
                isDialogShown = false
            )
        }
    }

    fun onPopulate(){
        viewModelScope.launch {
            repository.massSave(SampleData.passwords)
            onTotalPasswordsChange()
        }
    }

    fun onMassDelete(){
        viewModelScope.launch {
            repository.massDelete()
            onTotalPasswordsChange()
        }
    }
}