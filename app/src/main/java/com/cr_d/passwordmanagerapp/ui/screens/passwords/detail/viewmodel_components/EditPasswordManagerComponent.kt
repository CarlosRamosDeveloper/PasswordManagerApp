package com.cr_d.passwordmanagerapp.ui.screens.passwords.detail.viewmodel_components

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

import com.cr_d.passwordmanagerapp.domain.use_cases.security_use_cases.DecryptStringUseCase
import com.cr_d.passwordmanagerapp.data.mapper.toEditUiState
import com.cr_d.passwordmanagerapp.ui.model.ApplicationInfo
import com.cr_d.passwordmanagerapp.data.mapper.toDetail
import com.cr_d.passwordmanagerapp.domain.entities.Password
import com.cr_d.passwordmanagerapp.domain.use_cases.password_use_cases.ObtainPasswordDetailInfoUseCase
import com.cr_d.passwordmanagerapp.domain.value_objects.PasswordDataGeneration
import com.cr_d.passwordmanagerapp.domain.value_objects.PlainPassword
import com.cr_d.passwordmanagerapp.ui.model.PasswordEditUiState
import com.cr_d.passwordmanagerapp.ui.model.PasswordOption

class EditPasswordManagerComponent(
    private val decrypt: DecryptStringUseCase,
    private val obtainData: ObtainPasswordDetailInfoUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    data class UiState(
        val editInfo: PasswordEditUiState = PasswordEditUiState(),
        val newPassword: PlainPassword = PlainPassword(""),
        val newNotes: String = ""
    )

    suspend fun loadEditPassword(password: Password, passwordLength: Int){
        val extraData = obtainData.invoke(password)
        val newPassword = decrypt(password.cipheredPassword)
        val newNotes = decrypt(password.cipheredNotes)

        val parsedPassword = password.toDetail(extraData).toEditUiState(passwordLength)

        _uiState.update {
            it.copy(
                editInfo = parsedPassword,
                newPassword = PlainPassword(newPassword),
                newNotes = newNotes
            )
        }
    }

    fun onDeleteNotes(){
        _uiState.update {
            it.copy(
                newNotes = "",
            )
        }
    }

    fun getNewNotes(): String{
        return _uiState.value.newNotes
    }

    fun getNewPassword(): String{
        return _uiState.value.newPassword.value
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

    fun onPlainPasswordChange (plainPassword: String){
        _uiState.update {
            it.copy(
                newPassword = PlainPassword(plainPassword)
            )
        }
    }

    fun onPlainPasswordClear(){
        _uiState.update {
            it.copy(
                newPassword = PlainPassword("")
            )
        }
    }

    fun onNotesHasChanged(notes: String){
        _uiState.update {
            it.copy(
                newNotes = notes
            )
        }
    }

    fun getNewAppInfo(): ApplicationInfo{
        val appInfo = ApplicationInfo(
            appName = _uiState.value.editInfo.appName,
            appUrl = _uiState.value.editInfo.appUrl,
            appAccount = _uiState.value.editInfo.appAccount
        )
        return appInfo
    }

    fun getMetadataInfo(): PasswordDataGeneration{
        return PasswordDataGeneration(
            hasLowerCase = _uiState.value.editInfo.hasLowerCase,
            hasUpperCase = _uiState.value.editInfo.hasUpperCase,
            hasNumbers = _uiState.value.editInfo.hasNumbers,
            hasSpecials = _uiState.value.editInfo.hasSpecials,
            passwordLength = _uiState.value.editInfo.passwordLength
        )
    }

    fun onUpdateScore(score: Double) {
        _uiState.update {
            it.copy(
                editInfo = it.editInfo.copy(
                    score = score,
                )
            )
        }
    }

    fun onCleanScore(){
        _uiState.update {
            it.copy(
                editInfo = it.editInfo.copy(
                    score = 0.0,
                )
            )
        }
    }
}