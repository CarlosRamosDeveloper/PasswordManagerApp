package com.cr_d.passwordmanagerapp.ui.screens.password_detail

import android.content.ClipData
import android.content.ClipDescription
import android.content.ClipboardManager
import android.content.Context
import android.os.PersistableBundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

import com.cr_d.passwordmanagerapp.application.interfaces.IPasswordRepository
import com.cr_d.passwordmanagerapp.domain.value_objects.PasswordData
import kotlinx.coroutines.launch

class PasswordDetailViewModel(
    val repository: IPasswordRepository,
    val passwordId: Int
): ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    data class UiState(
        val isPasswordShown: Boolean = false,
        val password: PasswordData? = null
    )

    init {
        _uiState.update {
            it.copy(
                password = repository.findById(passwordId)
            )
        }
    }

    fun onVisibilityToggle () {
        _uiState.update {
            it.copy(
                isPasswordShown = !uiState.value.isPasswordShown
            )
        }
    }
}