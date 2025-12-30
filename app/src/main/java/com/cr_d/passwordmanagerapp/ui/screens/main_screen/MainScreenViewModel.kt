package com.cr_d.passwordmanagerapp.ui.screens.main_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

import com.cr_d.passwordmanagerapp.application.interfaces.IPasswordRepository
import com.cr_d.passwordmanagerapp.data.SampleData

class MainScreenViewModel (
    val repository: IPasswordRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    data class UiState(
        val isMassDeleteDialogShown: Boolean = false,
        val isPopulateDatabaseDialogShown: Boolean = false,
        val totalPasswords: Int = 0,
        val totalWarnings: Int = 0
    )

    init {
        onTotalPasswordsChange()
        Log.d("CreationScreen", "Main -> $this")
    }

    fun onRefresh() {
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

    fun onEnableMassDeleteDialog(){
        _uiState.update {
            it.copy(
                isMassDeleteDialogShown = true
            )
        }
    }

    fun onDisableMassDeleteDialog(){
        _uiState.update {
            it.copy(
                isMassDeleteDialogShown = false
            )
        }
    }

    fun onEnablePopulateDatabaseDialog(){
        _uiState.update {
            it.copy(
                isPopulateDatabaseDialogShown = true
            )
        }
    }

    fun onDisablePopulateDatabaseDialog(){
        _uiState.update {
            it.copy(
                isPopulateDatabaseDialogShown = false
            )
        }
    }

    fun onPopulate(){
        viewModelScope.launch {
            repository.massSave(SampleData.passwords)
            onTotalPasswordsChange()
            onDisablePopulateDatabaseDialog()
        }
    }

    fun onMassDelete(){
        viewModelScope.launch {
            repository.massDelete()
            onTotalPasswordsChange()
            onDisableMassDeleteDialog()
        }
    }
}