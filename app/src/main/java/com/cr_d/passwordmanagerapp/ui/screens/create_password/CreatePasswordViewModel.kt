package com.cr_d.passwordmanagerapp.ui.screens.create_password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import com.cr_d.passwordmanagerapp.ui.models.PasswordUiState
import kotlinx.coroutines.launch

class CreatePasswordViewModel(
    val generatePasswordUseCase: GeneratePasswordUseCase,
    val scoreCalculator: CalculateSecurityScoreUseCase,
    val savePasswordUseCase: SavePasswordUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    data class UiState(
        val password: PasswordUiState = PasswordUiState(),
        val passwordLength: Int = PasswordPolicy.MIN_GENERATED_LENGTH,
        val passwordError: String = "",
        val generatedPassword: String = "",
    )

    fun onOptionChanged(option: PasswordOption, value: Boolean) {
        _uiState.update {
            when (option) {
                PasswordOption.LOWERCASE -> {
                    it.copy(
                        password = it.password.copy(
                            metadata = it.password.metadata.copy(
                                hasLowerCase = value
                            ),
                        )
                    )
                }
                PasswordOption.UPPERCASE -> {
                    it.copy(
                        password = it.password.copy(
                            metadata = it.password.metadata.copy(
                                hasUpperCase = value
                            ),
                        )
                    )
                }
                PasswordOption.NUMBERS -> {
                    it.copy(
                        password = it.password.copy(
                            metadata = it.password.metadata.copy(
                                hasNumbers = value
                            ),
                        )
                    )
                }
                PasswordOption.SPECIALS -> {
                    it.copy(
                        password = it.password.copy(
                            metadata = it.password.metadata.copy(
                                hasSpecials = value
                            ),
                        )
                    )
                }
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
            it.copy(
                password = it.password.copy(
                    appInfo = it.password.appInfo.copy(
                        appName = value
                    )
                )
            )
        }
    }

    fun onAppUrlChanged(value: String){
        _uiState.update {
            it.copy(
                password = it.password.copy(
                    appInfo = it.password.appInfo.copy(
                        appUrl = value
                    )
                )
            )
        }
    }

    fun onAccountChanged(value: String){
        _uiState.update {
            it.copy(
                password = it.password.copy(
                    appInfo = it.password.appInfo.copy(
                        appAccount = value
                    )
                )
            )
        }
    }

    fun generatePassword() {
        val passwordDataGeneration = PasswordDataGeneration(
            _uiState.value.password.metadata.hasLowerCase,
            _uiState.value.password.metadata.hasUpperCase,
            _uiState.value.password.metadata.hasNumbers,
            _uiState.value.password.metadata.hasSpecials,
            _uiState.value.passwordLength
        )
        try {
            val password = generatePasswordUseCase(passwordDataGeneration)

            _uiState.update {
                it.copy(
                    generatedPassword = password,
                    passwordError = "",
                    password = it.password.copy(
                        score = scoreCalculator(password)
                    )
                )
            }
        } catch (e: Exception){
            _uiState.update {
                it.copy(
                    generatedPassword = "",
                    passwordError = e.message ?: "Error al generar contraseña",
                    password = it.password.copy(
                        score = 0.0
                    )
                )
            }
        }
    }

    fun clearPassword(){
        resetStatus()
    }

    fun savePassword(password: String){
        viewModelScope.launch {
            val passwordData = _uiState.value.password
            val appInfo = ApplicationInfo(
                passwordData.appInfo.appName,
                passwordData.appInfo.appUrl,
                passwordData.appInfo.appAccount
            )
            try {
                savePasswordUseCase.invoke(password, appInfo, passwordData.score)
                resetStatus()
            } catch (e: Exception){
                _uiState.update {
                    it.copy(
                        passwordError = e.message.toString()
                    )
                }
            }
        }
    }

    fun resetStatus(){
        _uiState.update {
            it.copy(
                password = PasswordUiState(),
                passwordLength = PasswordPolicy.MIN_GENERATED_LENGTH,
                generatedPassword = "",
                passwordError = "",
            )
        }
    }
}