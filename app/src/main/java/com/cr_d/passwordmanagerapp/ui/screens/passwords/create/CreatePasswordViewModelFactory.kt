package com.cr_d.passwordmanagerapp.ui.screens.passwords.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cr_d.passwordmanagerapp.data.repository.interfaces.IAccountRepository
import com.cr_d.passwordmanagerapp.data.repository.interfaces.IApplicationRepository

import com.cr_d.passwordmanagerapp.domain.use_cases.password_use_cases.CalculateSecurityScoreUseCase
import com.cr_d.passwordmanagerapp.domain.use_cases.password_use_cases.GeneratePasswordUseCase
import com.cr_d.passwordmanagerapp.domain.use_cases.password_use_cases.SavePasswordUseCase

class CreatePasswordViewModelFactory(
    val generatePasswordUseCase: GeneratePasswordUseCase,
    val scoreCalculator: CalculateSecurityScoreUseCase,
    val savePasswordUseCase: SavePasswordUseCase,
    val accountRepository: IAccountRepository,
    val appRepository: IApplicationRepository
) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CreatePasswordViewModel(
            generatePasswordUseCase,
            scoreCalculator,
            savePasswordUseCase,
            accountRepository,
            appRepository
        ) as T
    }
}