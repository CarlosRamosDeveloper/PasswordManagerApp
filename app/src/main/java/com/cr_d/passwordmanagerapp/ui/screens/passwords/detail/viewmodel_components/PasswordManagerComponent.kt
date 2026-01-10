package com.cr_d.passwordmanagerapp.ui.screens.passwords.detail.viewmodel_components

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

import com.cr_d.passwordmanagerapp.data.repository.interfaces.IPasswordRepository
import com.cr_d.passwordmanagerapp.domain.use_cases.security_use_cases.DecryptStringUseCase
import com.cr_d.passwordmanagerapp.domain.use_cases.password_use_cases.DeletePasswordUseCase
import com.cr_d.passwordmanagerapp.data.mapper.toUiState
import com.cr_d.passwordmanagerapp.data.mapper.toDetail
import com.cr_d.passwordmanagerapp.domain.entities.Password
import com.cr_d.passwordmanagerapp.domain.use_cases.password_use_cases.ObtainPasswordDetailInfoUseCase
import com.cr_d.passwordmanagerapp.ui.model.PasswordUiState

// TODO: Check and fix
class PasswordManagerComponent (
    val repository: IPasswordRepository,
    val deletePassword: DeletePasswordUseCase,
    private val decrypt: DecryptStringUseCase,
    private val obtainData: ObtainPasswordDetailInfoUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    data class UiState(
        val password: PasswordUiState? = null,
        val decipheredPassword: String = "",
        val decipheredNotes: String = "",
    )

    suspend fun loadPassword(password: Password){
        val extraData = obtainData.invoke(password)
        val decipheredNotes = decrypt(password.cipheredNotes)
        val parsedPassword = password.toDetail(extraData).toUiState()

        _uiState.update {
            it.copy(
                password = parsedPassword,
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