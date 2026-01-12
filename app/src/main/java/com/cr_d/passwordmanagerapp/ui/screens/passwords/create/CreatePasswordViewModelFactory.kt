package com.cr_d.passwordmanagerapp.ui.screens.passwords.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.cr_d.passwordmanagerapp.data.repository.interfaces.IAccountRepository
import com.cr_d.passwordmanagerapp.data.repository.interfaces.IApplicationRepository
import com.cr_d.passwordmanagerapp.domain.services.HashService
import com.cr_d.passwordmanagerapp.domain.use_cases.account_use_cases.SaveAccountUseCase
import com.cr_d.passwordmanagerapp.domain.use_cases.application_use_cases.SaveApplicationUseCase
import com.cr_d.passwordmanagerapp.domain.use_cases.password_use_cases.CalculateSecurityScoreUseCase
import com.cr_d.passwordmanagerapp.domain.use_cases.password_use_cases.GeneratePasswordUseCase
import com.cr_d.passwordmanagerapp.domain.use_cases.password_use_cases.SavePasswordUseCase

class CreatePasswordViewModelFactory(
    val generatePasswordUseCase: GeneratePasswordUseCase,
    val scoreCalculator: CalculateSecurityScoreUseCase,
    val savePasswordUseCase: SavePasswordUseCase,
    val saveApplicationUseCase: SaveApplicationUseCase,
    val applicationRepository: IApplicationRepository,
    val accountRepository: IAccountRepository,
    val saveAccountUseCase: SaveAccountUseCase,
    val hashService: HashService
) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CreatePasswordViewModel(
            generatePasswordUseCase,
            scoreCalculator,
            savePasswordUseCase,
            saveApplicationUseCase,
            applicationRepository,
            accountRepository,
            saveAccountUseCase,
            hashService
        ) as T
    }
}