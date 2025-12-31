package com.cr_d.passwordmanagerapp.ui.screens.password_detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlin.String
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

import com.cr_d.passwordmanagerapp.application.interfaces.IPasswordRepository
import com.cr_d.passwordmanagerapp.application.use_cases.CalculateSecurityScoreUseCase
import com.cr_d.passwordmanagerapp.application.use_cases.DecryptStringUseCase
import com.cr_d.passwordmanagerapp.application.use_cases.DeletePasswordUseCase
import com.cr_d.passwordmanagerapp.application.use_cases.GeneratePasswordUseCase
import com.cr_d.passwordmanagerapp.application.use_cases.UpdateNotesUseCase
import com.cr_d.passwordmanagerapp.application.use_cases.UpdatePasswordUseCase
import com.cr_d.passwordmanagerapp.data.mapper.toEditUiState
import com.cr_d.passwordmanagerapp.data.mapper.toUiState
import com.cr_d.passwordmanagerapp.domain.value_objects.ApplicationInfo
import com.cr_d.passwordmanagerapp.domain.value_objects.PasswordDataGeneration
import com.cr_d.passwordmanagerapp.domain.value_objects.PlainPassword
import com.cr_d.passwordmanagerapp.ui.models.PasswordDetailUiMode
import com.cr_d.passwordmanagerapp.ui.models.PasswordEditUiState
import com.cr_d.passwordmanagerapp.ui.models.PasswordOption
import com.cr_d.passwordmanagerapp.ui.models.PasswordUiState
import com.cr_d.passwordmanagerapp.ui.screens.password_detail.viewmodel_components.DialogManagerComponent

class PasswordDetailViewModel(
    val repository: IPasswordRepository,
    val passwordId: Long,
    val generatePasswordUseCase: GeneratePasswordUseCase,
    val securityScoreCalculator: CalculateSecurityScoreUseCase,
    val updatePasswordUseCase: UpdatePasswordUseCase,
    val updateNotesUseCase: UpdateNotesUseCase,
    val deletePasswordUseCase: DeletePasswordUseCase,
    val decrypt: DecryptStringUseCase,
    val dialogManager: DialogManagerComponent,
): ViewModel() {
    private val _uiState = MutableStateFlow(UiState())

    val uiState: StateFlow<UiState> = combine(_uiState, dialogManager.uiState) { baseState, dialogState ->
        baseState.copy(
            isDeletePasswordDialogShown = dialogState.isDeletePasswordDialogShown,
            isCopyToDialogShown = dialogState.isCopyToDialogShown,
            isUpdatePasswordDialogShown = dialogState.isUpdatePasswordDialogShown,
            isUpdateNotesDialogShown = dialogState.isUpdateNotesDialogShown,
            isDeleteNotesDialogShown = dialogState.isDeleteNotesDialogShown
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = UiState()
    )

    data class UiState(
        val isPasswordShown: Boolean = false,
        val mode: PasswordDetailUiMode = PasswordDetailUiMode.BASIC_INFO_MODE,
        val password: PasswordUiState? = null,
        val editInfo: PasswordEditUiState = PasswordEditUiState(),
        val isGeneratePasswordEnabled: Boolean = false,
        val errorMessage: String = "",
        val decipheredPassword: String = "",
        val decipheredNotes: String = "",
        val newPassword: PlainPassword = PlainPassword(""),
        val newNotes: String = "",
        val isDeletePasswordDialogShown: Boolean = false,
        val isCopyToDialogShown: Boolean = false,
        val isUpdatePasswordDialogShown: Boolean = false,
        val isUpdateNotesDialogShown: Boolean = false,
        val isDeleteNotesDialogShown: Boolean = false
    )

    init {
        loadPassword()
        Log.d("CreationScreen", "Detail -> $this")
    }

    private fun loadPassword(){
        viewModelScope.launch {
            val password = repository.findById(passwordId) ?: return@launch
            val pwdLength = decrypt(password.cipheredPassword).length
            val decipheredNotes = decrypt(password.cipheredNotes)
            _uiState.update {
                it.copy(
                    password = password.toUiState(),
                    editInfo = password.toEditUiState(pwdLength),
                    newPassword = PlainPassword(decrypt(password.cipheredPassword)),
                    decipheredNotes = decipheredNotes,
                    newNotes = decipheredNotes
                )
            }
        }
    }

    //DialogManager
    fun onEnableDeletePasswordDialog() = dialogManager.onEnableDeletePasswordDialog()
    fun onDisableDeletePasswordDialog() = dialogManager.onDisableDeletePasswordDialog()
    fun onEnableCopyDialog() = dialogManager.onEnableCopyDialog()
    fun onDisableCopyDialog() = dialogManager.onDisableCopyDialog()
    fun onEnableUpdateDialog() = dialogManager.onEnableUpdateDialog()
    fun onDisableUpdateDialog() = dialogManager.onDisableUpdateDialog()
    fun onEnableUpdateNotesDialog() = dialogManager.onEnableUpdateNotesDialog()
    fun onDisableUpdateNotesDialog() = dialogManager.onDisableUpdateNotesDialog()
    fun onEnableDeleteNotesDialog() = dialogManager.onEnableDeleteNotesDialog()
    fun onDisableDeleteNotesDialog() = dialogManager.onDisableDeleteNotesDialog()

    fun onDeleteNotes(){
        _uiState.update {
            it.copy(
                newNotes = "",
                decipheredNotes = ""
            )
        }
    }
    fun checkIfPasswordHasChanged(): Boolean{
        val lastPassword = decipherPassword()
        val newPassword = _uiState.value.newPassword.value
        return (lastPassword != newPassword)
    }

    fun checkIfNotesHasChanged(): Boolean {
        val lastNotes = _uiState.value.decipheredNotes
        val newNotes = _uiState.value.newNotes

        return (lastNotes != newNotes)
    }

    fun decipherPassword(): String{
        return decrypt(_uiState.value.password!!.cipheredPassword)
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

    fun onPasswordVisibilityToggle () {
        if (_uiState.value.isPasswordShown) {
            onDisablePasswordVisibility()
        } else {
            _uiState.update {
                it.copy(
                    isPasswordShown = true,
                    decipheredPassword = decipherPassword(),
                )
            }
        }
    }

    fun onDisablePasswordVisibility(){
        _uiState.update {
            it.copy(
                isPasswordShown = false,
                decipheredPassword = "",
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
                newPassword = PlainPassword(plainPassword)
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
        viewModelScope.launch {
            deletePasswordUseCase.invoke(passwordId)
            dialogManager.onDisableDeletePasswordDialog()
            loadPassword()
        }
    }

    fun onUpdatePassword (){
        viewModelScope.launch {
            val newAppInfo = ApplicationInfo(
                appName = _uiState.value.editInfo.appName,
                appUrl = _uiState.value.editInfo.appUrl,
                appAccount = _uiState.value.editInfo.appAccount
            )

            val updatedPassword = updatePasswordUseCase.invoke(
                id = passwordId,
                newPassword = _uiState.value.newPassword.value,
                appInfo = newAppInfo,
                notes = _uiState.value.newNotes
            )

            val uiStateParsedPassword = updatedPassword.toUiState()

            _uiState.update {
                it.copy(
                    password = uiStateParsedPassword
                )
            }
            loadPassword()
            onEnableFullInfoMode()
        }
    }

    fun onUpdateNotes(){
        val newNotes = _uiState.value.newNotes

        Log.d("NotesChange", newNotes)
        viewModelScope.launch {
            updateNotesUseCase(passwordId, newNotes)
        }
        _uiState.update {
            it.copy(
                decipheredNotes = newNotes
            )
        }
        Log.d("NotesChange", newNotes )
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
                    isPasswordShown = true,
                    errorMessage = "",
                    newPassword = PlainPassword(password),
                    editInfo = it.editInfo.copy(
                        score = securityScoreCalculator(password),
                    )
                )
            }
        } catch (e: Exception){
            _uiState.update {
                it.copy(
                    errorMessage = e.message ?: "Error al generar contraseña",
                    newPassword = PlainPassword(""),
                    editInfo = it.editInfo.copy(
                        score = 0.0,
                    )
                )
            }
        }
    }
}
