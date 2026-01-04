package com.cr_d.passwordmanagerapp.ui.screens.passwords.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.cr_d.passwordmanagerapp.application.interfaces.IPasswordRepository
import com.cr_d.passwordmanagerapp.domain.use_cases.CalculateSecurityScoreUseCase
import com.cr_d.passwordmanagerapp.domain.use_cases.GeneratePasswordUseCase
import com.cr_d.passwordmanagerapp.domain.use_cases.UpdateNotesUseCase
import com.cr_d.passwordmanagerapp.domain.use_cases.UpdatePasswordUseCase
import com.cr_d.passwordmanagerapp.ui.screens.passwords.detail.viewmodel_components.DialogManagerComponent
import com.cr_d.passwordmanagerapp.ui.screens.passwords.detail.viewmodel_components.EditPasswordManagerComponent
import com.cr_d.passwordmanagerapp.ui.screens.passwords.detail.viewmodel_components.PasswordManagerComponent
import com.cr_d.passwordmanagerapp.ui.screens.passwords.detail.viewmodel_components.UiManagerComponent

class PasswordDetailViewModelFactory(
    val repository: IPasswordRepository,
    val passwordId: Long,
    val generatePasswordUseCase: GeneratePasswordUseCase,
    val securityScoreCalculator: CalculateSecurityScoreUseCase,
    val updatePasswordUseCase: UpdatePasswordUseCase,
    val updateNotesUseCase: UpdateNotesUseCase,
    val dialogManager: DialogManagerComponent,
    val passwordManager: PasswordManagerComponent,
    val editManager: EditPasswordManagerComponent,
    val uiManager: UiManagerComponent
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PasswordDetailViewModel(
            repository,
            passwordId,
            generatePasswordUseCase,
            securityScoreCalculator,
            updatePasswordUseCase,
            updateNotesUseCase,
            dialogManager,
            passwordManager,
            editManager,
            uiManager
        ) as T
    }
}