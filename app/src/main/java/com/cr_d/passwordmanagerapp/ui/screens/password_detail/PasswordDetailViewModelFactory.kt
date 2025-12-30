package com.cr_d.passwordmanagerapp.ui.screens.password_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.cr_d.passwordmanagerapp.application.interfaces.IPasswordRepository
import com.cr_d.passwordmanagerapp.application.use_cases.CalculateSecurityScoreUseCase
import com.cr_d.passwordmanagerapp.application.use_cases.DecryptStringUseCase
import com.cr_d.passwordmanagerapp.application.use_cases.DeletePasswordUseCase
import com.cr_d.passwordmanagerapp.application.use_cases.GeneratePasswordUseCase
import com.cr_d.passwordmanagerapp.application.use_cases.UpdatePasswordUseCase

class PasswordDetailViewModeFactory(
    val repository: IPasswordRepository,
    val passwordId: Long,
    val generatePasswordUseCase: GeneratePasswordUseCase,
    val securityScoreCalculator: CalculateSecurityScoreUseCase,
    val updatePasswordUseCase: UpdatePasswordUseCase,
    val deletePasswordUseCase: DeletePasswordUseCase,
    val decrypt: DecryptStringUseCase
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PasswordDetailViewModel(
            repository,
            passwordId,
            generatePasswordUseCase,
            securityScoreCalculator,
            updatePasswordUseCase,
            deletePasswordUseCase,
            decrypt
        ) as T
    }
}