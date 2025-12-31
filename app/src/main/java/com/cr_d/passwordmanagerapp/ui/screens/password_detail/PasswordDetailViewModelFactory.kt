package com.cr_d.passwordmanagerapp.ui.screens.password_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.cr_d.passwordmanagerapp.application.interfaces.IPasswordRepository
import com.cr_d.passwordmanagerapp.application.use_cases.CalculateSecurityScoreUseCase
import com.cr_d.passwordmanagerapp.application.use_cases.DecryptStringUseCase
import com.cr_d.passwordmanagerapp.application.use_cases.DeletePasswordUseCase
import com.cr_d.passwordmanagerapp.application.use_cases.GeneratePasswordUseCase
import com.cr_d.passwordmanagerapp.application.use_cases.UpdateNotesUseCase
import com.cr_d.passwordmanagerapp.application.use_cases.UpdatePasswordUseCase
import com.cr_d.passwordmanagerapp.ui.screens.password_detail.viewmodel_components.DialogManagerComponent

class PasswordDetailViewModelFactory(
    val repository: IPasswordRepository,
    val passwordId: Long,
    val generatePasswordUseCase: GeneratePasswordUseCase,
    val securityScoreCalculator: CalculateSecurityScoreUseCase,
    val updatePasswordUseCase: UpdatePasswordUseCase,
    val updateNotesUseCase: UpdateNotesUseCase,
    val deletePasswordUseCase: DeletePasswordUseCase,
    val decrypt: DecryptStringUseCase,
    val dialogManager: DialogManagerComponent,
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PasswordDetailViewModel(
            repository,
            passwordId,
            generatePasswordUseCase,
            securityScoreCalculator,
            updatePasswordUseCase,
            updateNotesUseCase,
            deletePasswordUseCase,
            decrypt,
            dialogManager
        ) as T
    }
}