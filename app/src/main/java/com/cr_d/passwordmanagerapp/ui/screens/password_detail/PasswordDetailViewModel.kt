package com.cr_d.passwordmanagerapp.ui.screens.password_detail

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

import com.cr_d.passwordmanagerapp.application.interfaces.IPasswordRepository
import com.cr_d.passwordmanagerapp.application.use_cases.DeletePasswordUseCase
import com.cr_d.passwordmanagerapp.application.use_cases.UpdatePasswordUseCase
import com.cr_d.passwordmanagerapp.domain.entities.PasswordPolicy
import com.cr_d.passwordmanagerapp.domain.value_objects.ApplicationInfo
import com.cr_d.passwordmanagerapp.domain.value_objects.PasswordData
import com.cr_d.passwordmanagerapp.domain.value_objects.PlainPassword
import com.cr_d.passwordmanagerapp.ui.models.PasswordDetailUiMode
import com.cr_d.passwordmanagerapp.ui.models.PasswordOption

class PasswordDetailViewModel(
    val repository: IPasswordRepository,
    val passwordId: Int,
    val updatePasswordUseCase: UpdatePasswordUseCase,
    val deletePasswordUseCase: DeletePasswordUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    data class UiState(
        val isPasswordShown: Boolean = false,
        val mode: PasswordDetailUiMode = PasswordDetailUiMode.BASIC_INFO_MODE,
        val password: PasswordData? = null,
        val isUpdateSectionEnabled: Boolean = false,
        val newAppName: String = "",
        val newUrl: String = "",
        val newAccount: String = "",
        val newLowerCase: Boolean = false,
        val newUpperCase: Boolean = false,
        val newNumbers: Boolean = false,
        val newSpecials: Boolean = false,
        val newPlainPassword: PlainPassword = PlainPassword(""),
        val newSecurityScore: Double = 0.0
    )

    init {
        val password = repository.findById(passwordId)
        _uiState.update {
            it.copy(
                password = password,
                newAppName = password?.appInfo?.applicationName ?: "",
                newUrl = password?.appInfo?.url ?: "",
                newAccount = password?.appInfo?.account ?: "",
                newLowerCase = password?.metadata?.hasLowerCase ?: false,
                newUpperCase =  password?.metadata?.hasUpperCase ?: false,
                newNumbers = password?.metadata?.hasNumbers ?: false,
                newSpecials = password?.metadata?.hasSpecials ?: false,
                newPlainPassword = password?.plainPassword ?: PlainPassword("")
            )
        }
    }

    fun onAppNameChanged(value: String){
        _uiState.update {
            it.copy(
                newAppName = value
            )
        }
    }

    fun onUrlChanged(value: String){
        _uiState.update {
            it.copy(
                newUrl = value
            )
        }
    }

    fun onAccountChanged(value: String){
        _uiState.update {
            it.copy(
                newAccount = value
            )
        }
    }

    fun onOptionChanged(option: PasswordOption, value: Boolean) {
        _uiState.update {
            when (option) {
                PasswordOption.LOWERCASE -> it.copy(newLowerCase = value)
                PasswordOption.UPPERCASE -> it.copy(newUpperCase = value)
                PasswordOption.NUMBERS -> it.copy(newNumbers = value)
                PasswordOption.SPECIALS -> it.copy(newSpecials = value)
            }
        }
    }

    fun onVisibilityToggle () {
        _uiState.update {
            it.copy(
                isPasswordShown = !uiState.value.isPasswordShown
            )
        }
    }

    fun onPlainPasswordChange (plainPassword: String){
        _uiState.update {
            it.copy(
                newPlainPassword = PlainPassword(plainPassword)
            )
        }
    }

    fun onEnableEditMode() {
        _uiState.update {
            it.copy(
                mode = PasswordDetailUiMode.EDIT_MODE
            )
        }
    }

    fun onEnableBasicInfoMode(){
        _uiState.update {
            it.copy(
                mode = PasswordDetailUiMode.BASIC_INFO_MODE
            )
        }
    }

    fun onEnableFullInfoMode(){
        _uiState.update {
            it.copy(
                mode = PasswordDetailUiMode.FULL_INFO_MODE
            )
        }
    }

    fun onDeletePassword (){
        deletePasswordUseCase.invoke(passwordId)
        resetStatus()
    }

    fun onUpdatePassword (){
        val newAppInfo = ApplicationInfo(
            applicationName = _uiState.value.newAppName,
            url = _uiState.value.newUrl,
            account = _uiState.value.newAccount
        )

        val updatedPassword = updatePasswordUseCase.invoke(
            id = _uiState.value.password!!.id,
            newPassword = _uiState.value.newPlainPassword.value,
            appInfo = newAppInfo
        )

        _uiState.update {
            it.copy(
                password = updatedPassword
            )
        }
        resetStatus()
    }

    fun resetStatus(){
        val password = repository.findById(passwordId)

        _uiState.update {
            it.copy(
                newAppName = password?.appInfo?.applicationName ?: "",
                newUrl = password?.appInfo?.url ?: "",
                newAccount = password?.appInfo?.account ?: "",
                newLowerCase = password?.metadata?.hasLowerCase ?: false,
                newUpperCase =  password?.metadata?.hasUpperCase ?: false,
                newNumbers = password?.metadata?.hasNumbers ?: false,
                newSpecials = password?.metadata?.hasSpecials ?: false,
                newPlainPassword = password?.plainPassword ?: PlainPassword("")
            )
        }
    }
}