package com.cr_d.passwordmanagerapp.ui.screens.create_password

import android.util.Log
import androidx.lifecycle.ViewModel
import com.cr_d.passwordmanagerapp.application.interfaces.IPasswordRepository
import com.cr_d.passwordmanagerapp.application.use_cases.GeneratePasswordUseCase
import com.cr_d.passwordmanagerapp.domain.entities.PasswordGenerator
import com.cr_d.passwordmanagerapp.domain.entities.PasswordPolicy
import com.cr_d.passwordmanagerapp.domain.value_objects.PasswordDataGeneration
import com.cr_d.passwordmanagerapp.ui.models.PasswordOption
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update



class CreatePasswordViewModel(
    val repository: IPasswordRepository,
    val generatePassword: GeneratePasswordUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    data class UiState(
        val hasLowerCase: Boolean = false,
        val hasUpperCase: Boolean = false,
        val hasNumbers: Boolean = false,
        val hasSpecials: Boolean = false,
        val passwordLength: Int = PasswordPolicy.MIN_GENERATED_LENGTH,
        val passwordError: String = "",
        val generatedPassword: String = ""
    )

    fun onOptionChanged(option: PasswordOption, value: Boolean) {
        _uiState.update {
            when (option) {
                PasswordOption.LOWERCASE -> it.copy(hasLowerCase = value)
                PasswordOption.UPPERCASE -> it.copy(hasUpperCase = value)
                PasswordOption.NUMBERS -> it.copy(hasNumbers = value)
                PasswordOption.SPECIALS -> it.copy(hasSpecials = value)
            }
        }
    }

    fun onPasswordLengthChanged(value: Int) {
        _uiState.update {
            it.copy(passwordLength = value)
        }
    }

    fun onErrorChange(error: String){
        _uiState.update {
            it.copy(passwordError = error)
        }
    }

    fun onGeneratedPassword(password: String) {
        _uiState.update {
            it.copy(generatedPassword = password)
        }
    }

    fun generatePassword() {
        val passwordDataGeneration = PasswordDataGeneration(
            _uiState.value.hasLowerCase,
            _uiState.value.hasUpperCase,
            _uiState.value.hasNumbers,
            _uiState.value.hasSpecials,
            _uiState.value.passwordLength
        )
        try {
            val generator = PasswordGenerator(passwordDataGeneration)
            val passwordGeneratorUseCase = GeneratePasswordUseCase(generator)
            val password = passwordGeneratorUseCase()

            _uiState.update {
                it.copy(generatedPassword = password, passwordError = "")
            }
        } catch (e: Exception){
            _uiState.update {
                it.copy(generatedPassword = "", passwordError = e.message ?: "Error al generar contraseña")
            }
        }
    }

    fun clearPassword(){
        try {
            _uiState.update {
                it.copy(generatedPassword = "",
                    passwordError = ""
                )
            }

        } catch (e: Exception) {
            _uiState.update {
                it.copy(
                    generatedPassword = "",
                    passwordError = e.message.toString()
                )
            }
        }
    }
}