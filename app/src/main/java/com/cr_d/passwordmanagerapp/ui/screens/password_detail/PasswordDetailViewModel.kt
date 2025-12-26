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
import com.cr_d.passwordmanagerapp.domain.value_objects.ApplicationInfo
import com.cr_d.passwordmanagerapp.domain.value_objects.PasswordData
import com.cr_d.passwordmanagerapp.domain.value_objects.PasswordDataGeneration
import com.cr_d.passwordmanagerapp.domain.value_objects.PlainPassword
import com.cr_d.passwordmanagerapp.ui.models.PasswordDetailUiMode
import com.cr_d.passwordmanagerapp.ui.models.PasswordEditUiState
import com.cr_d.passwordmanagerapp.ui.models.PasswordOption

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
        val password: PasswordData? = null,
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

            _uiState.update {
                it.copy(
                    password = password,
                    editInfo = PasswordEditUiState(
                        newAppName = password.appInfo.applicationName,
                        newUrl = password.appInfo.url,
                        newAccount = password.appInfo.account,
                        newLowerCase = password.metadata.hasLowerCase,
                        newUpperCase =  password.metadata.hasUpperCase,
                        newNumbers = password.metadata.hasNumbers,
                        newSpecials = password.metadata.hasSpecials,
                        newPasswordLength = password.plainPassword.value.length,
                        newPlainPassword = password.plainPassword,
                        newSecurityScore = password.securityScore
                    )
                )
            }
        }
    }

    fun onAppNameChanged(value: String){
        _uiState.update {
            it.copy(
                editInfo = it.editInfo.copy(newAppName = value)
            )
        }
    }

    fun onUrlChanged(value: String){
        _uiState.update {
            it.copy(
                editInfo = it.editInfo.copy(newUrl = value)
            )
        }
    }

    fun onAccountChanged(value: String){
        _uiState.update {
            it.copy(
                editInfo = it.editInfo.copy(newAccount = value)
            )
        }
    }

    fun onOptionChanged(option: PasswordOption, value: Boolean) {
        _uiState.update {
            when (option) {
                PasswordOption.LOWERCASE -> it.copy(editInfo = it.editInfo.copy(newLowerCase = value))
                PasswordOption.UPPERCASE -> it.copy(editInfo = it.editInfo.copy(newUpperCase = value))
                PasswordOption.NUMBERS -> it.copy(editInfo = it.editInfo.copy(newNumbers = value))
                PasswordOption.SPECIALS -> it.copy(editInfo = it.editInfo.copy(newSpecials = value))
            }
        }
    }

    fun onPasswordLengthChanged(value: Int){
        _uiState.update {
            it.copy(
                editInfo = it.editInfo.copy(newPasswordLength = value)
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
                editInfo = it.editInfo.copy(newPlainPassword = PlainPassword(plainPassword))
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
            applicationName = _uiState.value.editInfo.newAppName,
            url = _uiState.value.editInfo.newUrl,
            account = _uiState.value.editInfo.newAccount
        )

        val updatedPassword = updatePasswordUseCase.invoke(
            id = _uiState.value.password!!.id,
            newPassword = _uiState.value.editInfo.newPlainPassword.value,
            appInfo = newAppInfo
        )

        _uiState.update {
            it.copy(
                password = updatedPassword
            )
        }
        loadPassword()
        onEnableFullInfoMode()
    }

    fun onGeneratePassword(){
        val passwordDataGeneration = PasswordDataGeneration(
            _uiState.value.editInfo.newLowerCase,
            _uiState.value.editInfo.newUpperCase,
            _uiState.value.editInfo.newNumbers,
            _uiState.value.editInfo.newSpecials,
            _uiState.value.editInfo.newPasswordLength
        )
        try {
            val password = generatePasswordUseCase(passwordDataGeneration)

            _uiState.update {
                it.copy(
                    errorMessage = "",
                    editInfo = it.editInfo.copy(
                        newSecurityScore = securityScoreCalculator(password),
                        newPlainPassword = PlainPassword(password)
                    )
                )
            }
        } catch (e: Exception){
            _uiState.update {
                it.copy(
                    errorMessage = e.message ?: "Error al generar contraseña",
                    editInfo = it.editInfo.copy(
                        newSecurityScore = 0.0,
                        newPlainPassword = PlainPassword("")
                    )
                )
            }
        }
    }
}