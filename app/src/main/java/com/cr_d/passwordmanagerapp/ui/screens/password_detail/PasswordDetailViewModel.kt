package com.cr_d.passwordmanagerapp.ui.screens.password_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

import com.cr_d.passwordmanagerapp.application.interfaces.IPasswordRepository
import com.cr_d.passwordmanagerapp.application.use_cases.CalculateSecurityScoreUseCase
import com.cr_d.passwordmanagerapp.application.use_cases.DeletePasswordUseCase
import com.cr_d.passwordmanagerapp.application.use_cases.GeneratePasswordUseCase
import com.cr_d.passwordmanagerapp.application.use_cases.UpdatePasswordUseCase
import com.cr_d.passwordmanagerapp.data.mapper.toDomain
import com.cr_d.passwordmanagerapp.data.mapper.toUIState
import com.cr_d.passwordmanagerapp.domain.entities.PasswordAnalyzer
import com.cr_d.passwordmanagerapp.domain.value_objects.ApplicationInfo
import com.cr_d.passwordmanagerapp.domain.value_objects.PasswordDataGeneration
import com.cr_d.passwordmanagerapp.domain.value_objects.PlainPassword
import com.cr_d.passwordmanagerapp.ui.models.PasswordDetailUiMode
import com.cr_d.passwordmanagerapp.ui.models.PasswordEditUiState
import com.cr_d.passwordmanagerapp.ui.models.PasswordOption
import com.cr_d.passwordmanagerapp.ui.models.PasswordUiState

class PasswordDetailViewModel(
    val repository: IPasswordRepository,
    val passwordId: Int,
    val generatePasswordUseCase: GeneratePasswordUseCase,
    val securityScoreCalculator: CalculateSecurityScoreUseCase,
    val updatePasswordUseCase: UpdatePasswordUseCase,
    val deletePasswordUseCase: DeletePasswordUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    data class UiState(
        val isPasswordShown: Boolean = false,
        val mode: PasswordDetailUiMode = PasswordDetailUiMode.BASIC_INFO_MODE,
        val password: PasswordUiState? = null,
        val editInfo: PasswordEditUiState = PasswordEditUiState(),
        val isGeneratePasswordEnabled: Boolean = false,
        val errorMessage: String = ""
    )

    init {
        loadPassword()
    }

    private fun loadPassword(){
        viewModelScope.launch {
            val password = repository.findById(passwordId) ?: return@launch
            val parsedPassword = password.toUIState()

            _uiState.update {
                it.copy(

                    password = parsedPassword,
                    editInfo = PasswordEditUiState(
                        appName = password.appInfo.appName,
                        appUrl = password.appInfo.appUrl,
                        appAccount = password.appInfo.appAccount,
                        hasLowerCase = password.metadata.hasLowerCase,
                        hasUpperCase =  password.metadata.hasUpperCase,
                        hasNumbers = password.metadata.hasNumbers,
                        hasSpecials = password.metadata.hasSpecials,
                        passwordLength = password.plainPassword.value.length,
                        plainPassword = password.plainPassword,
                        score = password.score
                    )
                )
            }
        }
    }

    fun onAppNameChanged(value: String){
        _uiState.update {
            it.copy(
                editInfo = it.editInfo.copy(appName = value)
            )
        }
    }

    fun onUrlChanged(value: String){
        _uiState.update {
            it.copy(
                editInfo = it.editInfo.copy(appUrl = value)
            )
        }
    }

    fun onAccountChanged(value: String){
        _uiState.update {
            it.copy(
                editInfo = it.editInfo.copy(appAccount = value)
            )
        }
    }

    fun onOptionChanged(option: PasswordOption, value: Boolean) {
        _uiState.update {
            when (option) {
                PasswordOption.LOWERCASE -> it.copy(editInfo = it.editInfo.copy(hasLowerCase = value))
                PasswordOption.UPPERCASE -> it.copy(editInfo = it.editInfo.copy(hasUpperCase = value))
                PasswordOption.NUMBERS -> it.copy(editInfo = it.editInfo.copy(hasNumbers = value))
                PasswordOption.SPECIALS -> it.copy(editInfo = it.editInfo.copy(hasSpecials = value))
            }
        }
    }

    fun onPasswordLengthChanged(value: Int){
        _uiState.update {
            it.copy(
                editInfo = it.editInfo.copy(passwordLength = value)
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

    fun onGeneratePasswordSectionToggle () {
        _uiState.update {
            it.copy(
                isGeneratePasswordEnabled = !uiState.value.isGeneratePasswordEnabled
            )
        }
    }

    fun onPlainPasswordChange (plainPassword: String){
        _uiState.update {
            it.copy(
                editInfo = it.editInfo.copy(plainPassword = PlainPassword(plainPassword))
            )
        }
    }

    fun onEnableEditMode() {
        _uiState.update {
            it.copy(
                mode = PasswordDetailUiMode.EDIT_MODE,
                isGeneratePasswordEnabled = false,
                isPasswordShown = false
            )
        }
    }

    fun onEnableBasicInfoMode(){
        _uiState.update {
            it.copy(
                mode = PasswordDetailUiMode.BASIC_INFO_MODE,
                isGeneratePasswordEnabled = false,
                isPasswordShown = false
            )
        }
    }

    fun onEnableFullInfoMode(){
        _uiState.update {
            it.copy(
                mode = PasswordDetailUiMode.FULL_INFO_MODE,
                isGeneratePasswordEnabled = false,
                isPasswordShown = false
            )
        }
    }

    fun onDeletePassword (){
        deletePasswordUseCase.invoke(passwordId)
        loadPassword()
    }

    fun onUpdatePassword (){
        val newAppInfo = ApplicationInfo(
            appName = _uiState.value.editInfo.appName,
            appUrl = _uiState.value.editInfo.appUrl,
            appAccount = _uiState.value.editInfo.appAccount
        )

        val updatedPassword = updatePasswordUseCase.invoke(
            id = passwordId,
            newPassword = _uiState.value.editInfo.plainPassword.value,
            appInfo = newAppInfo
        )

        val updatedMetadata = PasswordAnalyzer.analyze(updatedPassword.plainPassword)
        val updatedSecurityScore = securityScoreCalculator(updatedPassword.plainPassword)
        val uiStateParsedPassword = updatedPassword
            .toDomain(
                metadata = updatedMetadata,
                score = updatedSecurityScore
            ).toUIState()

        _uiState.update {
            it.copy(
                password = uiStateParsedPassword
            )
        }
        loadPassword()
        onEnableFullInfoMode()
    }

    fun onGeneratePassword(){
        val passwordDataGeneration = PasswordDataGeneration(
            _uiState.value.editInfo.hasLowerCase,
            _uiState.value.editInfo.hasUpperCase,
            _uiState.value.editInfo.hasNumbers,
            _uiState.value.editInfo.hasSpecials,
            _uiState.value.editInfo.passwordLength
        )
        try {
            val password = generatePasswordUseCase(passwordDataGeneration)

            _uiState.update {
                it.copy(
                    errorMessage = "",
                    editInfo = it.editInfo.copy(
                        score = securityScoreCalculator(password),
                        plainPassword = PlainPassword(password)
                    )
                )
            }
        } catch (e: Exception){
            _uiState.update {
                it.copy(
                    errorMessage = e.message ?: "Error al generar contraseña",
                    editInfo = it.editInfo.copy(
                        score = 0.0,
                        plainPassword = PlainPassword("")
                    )
                )
            }
        }
    }
}