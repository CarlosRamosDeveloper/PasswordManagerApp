package com.cr_d.passwordmanagerapp.ui.screens.generate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlin.String

import com.cr_d.passwordmanagerapp.domain.policy.PasswordPolicy
import com.cr_d.passwordmanagerapp.domain.services.PasswordGenerator
import com.cr_d.passwordmanagerapp.domain.use_cases.password_use_cases.CalculateSecurityScoreUseCase
import com.cr_d.passwordmanagerapp.domain.value_objects.PasswordDataGeneration
import com.cr_d.passwordmanagerapp.domain.value_objects.PasswordMetadata
import com.cr_d.passwordmanagerapp.ui.model.PasswordOption

class GeneratePasswordViewModel (
    private val generator: PasswordGenerator,
    private val scoreCalculator: CalculateSecurityScoreUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()
    val isGeneratePasswordEnabled: StateFlow<Boolean> = _uiState.map {
        //TODO: Cambiar a cualquiera de metadata a true
        it.metadata.hasNumbers
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(1_000),
        false
    )
    data class UiState(
        val metadata: PasswordMetadata = PasswordMetadata(),
        val passwordLength: Int = PasswordPolicy.MIN_GENERATED_LENGTH,
        val passwordError: String = "",
        val generatedPassword: String = "",
        val score: Double = 0.0,
        val isCopyDialogShown: Boolean = false
    )

    fun onOptionChanged(option: PasswordOption, value: Boolean) {
        _uiState.update {
            when (option) {
                PasswordOption.LOWERCASE -> {
                    it.copy(
                        metadata = it.metadata.copy(
                                hasLowerCase = value
                            ),
                    )
                }
                PasswordOption.UPPERCASE -> {
                    it.copy(
                        metadata = it.metadata.copy(
                                hasUpperCase = value
                            ),

                    )
                }
                PasswordOption.NUMBERS -> {
                    it.copy(
                        metadata = it.metadata.copy(
                                hasNumbers = value
                            ),

                    )
                }
                PasswordOption.SPECIALS -> {
                    it.copy(

                            metadata = it.metadata.copy(
                                hasSpecials = value
                            ),
                        )

                }
            }
        }
    }

    fun onPasswordLengthChange(value: Int){
        _uiState.update {
            it.copy(
                passwordLength = value
            )
        }
    }

    fun onEnableCopyDialog(){
        _uiState.update {
            it.copy(
                isCopyDialogShown = true
            )
        }
    }

    fun onDisableCopyDialog(){
        _uiState.update {
            it.copy(
                isCopyDialogShown = false
            )
        }
    }

    fun clearPassword(){
        _uiState.update {
            it.copy(
                passwordError = "",
                generatedPassword = "",
                score = 0.0,
            )
        }
    }

    fun generatePassword(){
        val metadata = _uiState.value.metadata
        val data = PasswordDataGeneration(
            hasLowerCase = metadata.hasLowerCase,
            hasUpperCase = metadata.hasUpperCase,
            hasNumbers = metadata.hasNumbers,
            hasSpecials = metadata.hasSpecials,
            passwordLength = _uiState.value.passwordLength
        )
        val password = generator.generatePassword(data)
        _uiState.update {
            it.copy(
                generatedPassword = password,
                score = scoreCalculator(password)
            )
        }
    }

    fun clearData(){
        _uiState.update {
            it.copy(
                metadata = PasswordMetadata(),
                passwordLength = PasswordPolicy.MIN_GENERATED_LENGTH,
            )
        }
        clearPassword()
    }
}