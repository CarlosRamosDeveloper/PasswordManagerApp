package com.cr_d.passwordmanagerapp.ui.screens.passwords.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlin.String
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

import com.cr_d.passwordmanagerapp.data.repository.interfaces.IPasswordRepository
import com.cr_d.passwordmanagerapp.domain.use_cases.password_use_cases.CalculateSecurityScoreUseCase
import com.cr_d.passwordmanagerapp.domain.use_cases.password_use_cases.GeneratePasswordUseCase
import com.cr_d.passwordmanagerapp.domain.use_cases.password_use_cases.UpdateNotesUseCase
import com.cr_d.passwordmanagerapp.domain.use_cases.password_use_cases.UpdatePasswordUseCase
import com.cr_d.passwordmanagerapp.data.mapper.toUiState
import com.cr_d.passwordmanagerapp.domain.value_objects.PlainPassword
import com.cr_d.passwordmanagerapp.ui.model.PasswordConfirmDialogData
import com.cr_d.passwordmanagerapp.ui.model.PasswordDetailUiMode
import com.cr_d.passwordmanagerapp.ui.model.PasswordEditUiState
import com.cr_d.passwordmanagerapp.ui.model.PasswordOption
import com.cr_d.passwordmanagerapp.ui.model.PasswordUiState
import com.cr_d.passwordmanagerapp.ui.screens.passwords.detail.viewmodel_components.DialogManagerComponent
import com.cr_d.passwordmanagerapp.ui.screens.passwords.detail.viewmodel_components.EditPasswordManagerComponent
import com.cr_d.passwordmanagerapp.ui.screens.passwords.detail.viewmodel_components.PasswordManagerComponent
import com.cr_d.passwordmanagerapp.ui.screens.passwords.detail.viewmodel_components.UiManagerComponent

class PasswordDetailViewModel(
    private val repository: IPasswordRepository,
    private val passwordId: Long,
    private val generatePasswordUseCase: GeneratePasswordUseCase,
    private val securityScoreCalculator: CalculateSecurityScoreUseCase,
    private val updatePasswordUseCase: UpdatePasswordUseCase,
    private val updateNotesUseCase: UpdateNotesUseCase,
    private val dialogManager: DialogManagerComponent,
    private val passwordManager: PasswordManagerComponent,
    private val editPasswordManager: EditPasswordManagerComponent,
    private val uiManager: UiManagerComponent
): ViewModel() {
    private val _uiState = MutableStateFlow(UiState())

    val uiState: StateFlow<UiState> = combine(
        _uiState,
        dialogManager.uiState,
        passwordManager.uiState,
        editPasswordManager.uiState,
        uiManager.uiState
    ) { baseState, dialogState, passwordManager, editManager, uiManager ->
        baseState.copy(
            isDeletePasswordDialogShown = dialogState.dialogData.isDeletePasswordDialogShown,
            isCopyToDialogShown = dialogState.dialogData.isCopyToDialogShown,
            isUpdatePasswordDialogShown = dialogState.dialogData.isUpdatePasswordDialogShown,
            isUpdateNotesDialogShown = dialogState.dialogData.isUpdateNotesDialogShown,
            isDeleteNotesDialogShown = dialogState.dialogData.isDeleteNotesDialogShown,
            password = passwordManager.password,
            decipheredPassword = passwordManager.decipheredPassword,
            decipheredNotes = passwordManager.decipheredNotes,
            editInfo = editManager.editInfo,
            newPassword = editManager.newPassword,
            newNotes = editManager.newNotes,
            isPasswordShown = uiManager.isPasswordShown,
            mode = uiManager.mode,
            isGeneratePasswordEnabled = uiManager.isGeneratePasswordEnabled,
            errorMessage = uiManager.errorMessage
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
        initialize()
        Log.d("CreationScreen", "Detail -> $this")
    }

    private fun initialize(){
        viewModelScope.launch {
            val password = repository.findById(passwordId) ?: return@launch

            passwordManager.loadPassword(password)
            editPasswordManager.loadEditPassword(password, passwordManager.getLength())
            uiManager.onLoad()
        }
    }

    // Ui Methods
    fun onPasswordVisibilityToggle () {
        val state = uiManager.uiState.value.isPasswordShown

        if (state) {
            onDisablePasswordVisibility()
            onResetDecipheredPassword()
        } else {
            onEnablePasswordVisibility()
            updateDecipheredPassword()
        }
    }

    // UI methods
    fun onEnablePasswordVisibility() = uiManager.onEnablePasswordVisibility()
    fun onDisablePasswordVisibility() = uiManager.onDisablePasswordVisibility()
    fun onGeneratePasswordSectionToggle () = uiManager.onGeneratePasswordSectionToggle()

    fun onEnableEditMode() = uiManager.onEnableEditMode()

    fun onEnableBasicInfoMode() = uiManager.onEnableBasicInfoMode()

    fun onEnableFullInfoMode() = uiManager.onEnableFullInfoMode()
    fun onNewErrorMessage(error:String) = uiManager.onNewErrorMessage(error)

    fun onCleanError() = uiManager.onCleanError()

    //DialogManager
    fun getData(): PasswordConfirmDialogData = dialogManager.getData()
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

    // PasswordManager
    fun decipherPassword(): String = passwordManager.decipherPassword()
    fun onUpdateCipheredNotes(newNotes: String) = passwordManager.onUpdateCipheredNotes(newNotes)
    fun updateDecipheredPassword() = passwordManager.updateDecipheredPassword()
    fun onResetDecipheredPassword() = passwordManager.onResetDecipheredPassword()
    fun onUpdatePassword(newPassword: PasswordUiState) = passwordManager.onUpdatePassword(newPassword)

    // Edit manager
    fun onAppNameChanged(value: String) = editPasswordManager.onAppNameChanged(value)
    fun onUrlChanged(value: String) = editPasswordManager.onUrlChanged(value)
    fun onAccountChanged(value: String) = editPasswordManager.onAccountChanged(value)
    fun onOptionChanged(option: PasswordOption, value: Boolean) =
        editPasswordManager.onOptionChanged(option,value)
    fun onPasswordLengthChanged(value: Int) = editPasswordManager.onPasswordLengthChanged(value)
    fun onPlainPasswordChange (plainPassword: String) = editPasswordManager.onPlainPasswordChange(plainPassword)
    fun onPlainPasswordClear() = editPasswordManager.onPlainPasswordClear()
    fun onNotesHasChanged(notes: String) = editPasswordManager.onNotesHasChanged(notes)
    fun onUpdateScore(score: Double) = editPasswordManager.onUpdateScore(score)
    fun onCleanScore() = editPasswordManager.onCleanScore()


    // Orchestra methods
    fun onDeleteNotes(){
        passwordManager.onDeleteNotes()
        editPasswordManager.onDeleteNotes()
    }
    fun checkIfPasswordHasChanged(): Boolean{
        val lastPassword = decipherPassword()
        val newPassword = editPasswordManager.getNewPassword()
        return lastPassword != newPassword
    }

    fun checkIfNotesHasChanged(): Boolean {
        val lastNotes = passwordManager.getNotes()
        val newNotes = editPasswordManager.getNewNotes()

        return (lastNotes != newNotes)
    }

    fun onUpdatePassword (){
        viewModelScope.launch {
            val newAppInfo = editPasswordManager.getNewAppInfo()

            val updatedPassword = updatePasswordUseCase.invoke(
                id = passwordId,
                newPassword = editPasswordManager.getNewPassword(),
                appInfo = newAppInfo,
                notes = editPasswordManager.getNewNotes()
            )

            val uiStateParsedPassword = updatedPassword.toUiState()

            onUpdatePassword(uiStateParsedPassword)
            initialize()
            onEnableFullInfoMode()
        }
    }

    fun onUpdateNotes(){
        val newNotes = editPasswordManager.getNewNotes()

        viewModelScope.launch {
            updateNotesUseCase(passwordId, newNotes)
        }

        onUpdateCipheredNotes(newNotes)
    }

    fun onGeneratePassword(){
        val passwordDataGeneration = editPasswordManager.getMetadataInfo()
        try {
            val password = generatePasswordUseCase(passwordDataGeneration)
            onEnablePasswordVisibility()
            onCleanError()
            onPlainPasswordChange(password)
            onUpdateScore(securityScoreCalculator(password))
        } catch (e: Exception){
            onNewErrorMessage(e.message ?: "Error al generar contraseña")
            onPlainPasswordClear()
            onCleanScore()
        }
    }

    fun onDeletePassword (){
        viewModelScope.launch {
            passwordManager.onDeletePassword(passwordId)
            dialogManager.onDisableDeletePasswordDialog()
        }
    }
}
