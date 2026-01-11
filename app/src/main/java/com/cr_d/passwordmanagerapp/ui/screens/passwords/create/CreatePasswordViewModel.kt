package com.cr_d.passwordmanagerapp.ui.screens.passwords.create

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cr_d.passwordmanagerapp.data.dto.PasswordCreationData
import com.cr_d.passwordmanagerapp.domain.use_cases.password_use_cases.CalculateSecurityScoreUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

import com.cr_d.passwordmanagerapp.domain.use_cases.password_use_cases.SavePasswordUseCase
import com.cr_d.passwordmanagerapp.domain.use_cases.password_use_cases.GeneratePasswordUseCase
import com.cr_d.passwordmanagerapp.domain.policy.PasswordPolicy
import com.cr_d.passwordmanagerapp.domain.value_objects.PasswordDataGeneration
import com.cr_d.passwordmanagerapp.ui.model.PasswordOption
import com.cr_d.passwordmanagerapp.ui.model.PasswordUiState

class CreatePasswordViewModel(
    val generatePasswordUseCase: GeneratePasswordUseCase,
    val scoreCalculator: CalculateSecurityScoreUseCase,
    val savePasswordUseCase: SavePasswordUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    data class UiState(
        val password: PasswordUiState = PasswordUiState(),
        val plainPassword: String = "",
        val passwordLength: Int = PasswordPolicy.MIN_GENERATED_LENGTH,
        val passwordError: String = "",
        val generatedPassword: String = "",
        val isCopyToDialogShown: Boolean = false,
        val isPasswordGenerationEnabled: Boolean = false,
        val isAccountSectionEnabled: Boolean = false,
        val isApplicationSectionEnabled: Boolean = false,
        val passwordScore: Double = 0.0,
        val accountName: String = "",
        val accountNotes: String = ""
    )

    init{
        Log.d("CreationScreen", "Create -> $this")
    }

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

    fun onPlainPasswordChange(value: String) {
        _uiState.update {
            it.copy(
                plainPassword = value,
                passwordScore = scoreCalculator(_uiState.value.plainPassword)
            )
        }
        if (_uiState.value.plainPassword.length >= PasswordPolicy.MIN_LENGTH) {
            onEnableAccountSection()
        } else {
            onDisableAccountSection()
        }
    }

    fun onPlainPasswordClear(){
        _uiState.update {
            it.copy(
                plainPassword = "",
                passwordScore = 0.0
            )
        }
        onDisableAccountSection()
        onDisableApplicationSection()
    }

    fun onEnablePasswordGeneration(){
        _uiState.update {
            it.copy(
                isPasswordGenerationEnabled = true
            )
        }
    }

    fun onDisablePasswordGeneration(){
        _uiState.update {
            it.copy(
                isPasswordGenerationEnabled = false
            )
        }
    }

    fun onPasswordLengthChanged(value: Int) {
        _uiState.update {
            it.copy(passwordLength = value)
        }
    }

    fun onEnableAccountSection(){
        _uiState.update {
            it.copy(
                isAccountSectionEnabled = true
            )
        }
    }

    fun onDisableAccountSection(){
        _uiState.update {
            it.copy(
                isAccountSectionEnabled = false
            )
        }
    }

    fun onEnableApplicationSection(){
        _uiState.update {
            it.copy(
                isApplicationSectionEnabled = true
            )
        }
    }

    fun onDisableApplicationSection(){
        _uiState.update {
            it.copy(
                isApplicationSectionEnabled = false
            )
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

    fun onAccountNameChanged(value: String){
        _uiState.update {
            it.copy(
                accountName = value
            )
        }
        if (_uiState.value.accountName.length >= 3 ) onEnableApplicationSection()
    }

    fun onAccountNotesChanged(value: String){
        _uiState.update {
            it.copy(
                accountNotes = value
            )
        }
    }

    fun onResetAccountData(){
        _uiState.update {
            it.copy(
                accountName = "",
                accountNotes = ""
            )
        }
        onDisableApplicationSection()
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
                    plainPassword = password,
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
            //TODO: Fix
            val data = PasswordCreationData(
                password = password,
                appId = 1,
                accId = 1,
                notes = ""
            )
            try {
                savePasswordUseCase.invoke(data)
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
                plainPassword = "",
                passwordError = "",
            )
        }
    }
}