package com.cr_d.passwordmanagerapp.ui.screens.passwords.detail.viewmodel_components

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

import com.cr_d.passwordmanagerapp.data.repository.IPasswordRepository
import com.cr_d.passwordmanagerapp.domain.use_cases.DecryptStringUseCase
import com.cr_d.passwordmanagerapp.domain.use_cases.DeletePasswordUseCase
import com.cr_d.passwordmanagerapp.data.mapper.toUiState
import com.cr_d.passwordmanagerapp.data.dto.PasswordDetail
import com.cr_d.passwordmanagerapp.ui.models.PasswordUiState

// TODO: Check and fix
class PasswordManagerComponent (
    val repository: IPasswordRepository,
    val deletePassword: DeletePasswordUseCase,
    private val decrypt: DecryptStringUseCase,
): ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    data class UiState(
        val password: PasswordUiState? = null,
        val decipheredPassword: String = "",
        val decipheredNotes: String = "",
    )

    fun loadPassword(passwordDetail: PasswordDetail){
        val decipheredNotes = decrypt(passwordDetail.cipheredNotes)

        _uiState.update {
            it.copy(
                password = passwordDetail.toUiState(),
                decipheredPassword = "",
                decipheredNotes = decipheredNotes
            )
        }
    }

    fun decipherPassword(): String{
        return decrypt(_uiState.value.password!!.cipheredPassword)
    }

    fun onDeleteNotes(){
        _uiState.update {
            it.copy(decipheredNotes = "")

        }
    }

    fun onResetDecipheredPassword(){
        _uiState.update {
            it.copy(
                decipheredPassword = "",
            )
        }
    }

    fun getNotes():String{
        return _uiState.value.decipheredNotes
    }

    fun getDecipheredPassword():String{
        return _uiState.value.decipheredPassword
    }

    fun updateDecipheredPassword(){
        _uiState.update {
            it.copy(
                decipheredPassword = decipherPassword()
            )
        }
    }

    fun onDeletePassword (passwordId: Long){
        viewModelScope.launch {
            deletePassword.invoke(passwordId)
        }
    }

    fun onUpdateCipheredNotes(newNotes: String) {
        _uiState.update {
            it.copy(
                decipheredNotes = newNotes
            )
        }
    }

    fun onUpdatePassword(newPassword: PasswordUiState) {
        _uiState.update {
            it.copy(
                password = newPassword
            )
        }
    }

    fun getLength():Int{
        return _uiState.value.decipheredPassword.length
    }
}