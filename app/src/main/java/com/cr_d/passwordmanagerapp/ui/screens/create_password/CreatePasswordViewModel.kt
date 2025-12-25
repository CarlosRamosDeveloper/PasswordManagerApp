package com.cr_d.passwordmanagerapp.ui.screens.create_password

import androidx.lifecycle.ViewModel
import com.cr_d.passwordmanagerapp.application.use_cases.CalculateSecurityScoreUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

import com.cr_d.passwordmanagerapp.application.use_cases.SavePasswordUseCase
import com.cr_d.passwordmanagerapp.application.use_cases.GeneratePasswordUseCase
import com.cr_d.passwordmanagerapp.domain.entities.PasswordPolicy
import com.cr_d.passwordmanagerapp.domain.value_objects.ApplicationInfo
import com.cr_d.passwordmanagerapp.domain.value_objects.PasswordDataGeneration
import com.cr_d.passwordmanagerapp.ui.models.PasswordOption

class CreatePasswordViewModel(
    val generatePasswordUseCase: GeneratePasswordUseCase,
    val scoreCalculator: CalculateSecurityScoreUseCase,
    val savePasswordUseCase: SavePasswordUseCase
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
        val generatedPassword: String = "",
        val passwordScore: Double = 0.0,
        val appName: String = "",
        val appUrl: String = "",
        val account: String = ""
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

    fun onAppNameChanged(value: String){
        _uiState.update {
            it.copy(appName = value)
        }
    }

    fun onAppUrlChanged(value: String){
        _uiState.update {
            it.copy(appUrl = value)
        }
    }

    fun onAccountChanged(value: String){
        _uiState.update {
            it.copy(account = value)
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
            val password = generatePasswordUseCase(passwordDataGeneration)

            _uiState.update {
                it.copy(
                    generatedPassword = password,
                    passwordError = "",
                    passwordScore = scoreCalculator(password)
                )
            }
        } catch (e: Exception){
            _uiState.update {
                it.copy(
                    generatedPassword = "",
                    passwordError = e.message ?: "Error al generar contraseña",
                    passwordScore = 0.0
                )
            }
        }
    }

    fun clearPassword(){
        resetStatus()
    }

    fun savePassword(password: String){
        val appInfo = ApplicationInfo(_uiState.value.appName, _uiState.value.appUrl, _uiState.value.account)
        try {
            savePasswordUseCase.invoke(password, appInfo, _uiState.value.passwordScore)
            resetStatus()
        } catch (e: Exception){
            _uiState.update {
                it.copy(
                    passwordError = e.message.toString()
                )
            }
        }
    }

    fun resetStatus(){
        _uiState.update {
            it.copy(
                hasLowerCase = false,
                hasUpperCase = false,
                hasNumbers = false,
                hasSpecials = false,
                passwordLength = PasswordPolicy.MIN_GENERATED_LENGTH,
                generatedPassword = "",
                passwordError = "",
                passwordScore = 0.0,
                appName = "",
                appUrl = "",
                account = ""
            )
        }
    }
}