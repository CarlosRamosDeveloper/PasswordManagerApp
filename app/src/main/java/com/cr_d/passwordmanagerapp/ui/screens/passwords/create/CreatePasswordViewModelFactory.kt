package com.cr_d.passwordmanagerapp.ui.screens.passwords.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.cr_d.passwordmanagerapp.domain.use_cases.CalculateSecurityScoreUseCase
import com.cr_d.passwordmanagerapp.domain.use_cases.GeneratePasswordUseCase
import com.cr_d.passwordmanagerapp.domain.use_cases.SavePasswordUseCase

class CreatePasswordViewModelFactory(
    val generatePasswordUseCase: GeneratePasswordUseCase,
    val scoreCalculator: CalculateSecurityScoreUseCase,
    val savePasswordUseCase: SavePasswordUseCase
) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CreatePasswordViewModel(
            generatePasswordUseCase,
            scoreCalculator,
            savePasswordUseCase
        ) as T
    }
}