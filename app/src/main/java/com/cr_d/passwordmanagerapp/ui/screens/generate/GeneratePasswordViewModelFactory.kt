package com.cr_d.passwordmanagerapp.ui.screens.generate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.cr_d.passwordmanagerapp.domain.services.PasswordGenerator
import com.cr_d.passwordmanagerapp.domain.use_cases.password_use_cases.CalculateSecurityScoreUseCase

class GeneratePasswordViewModelFactory(
    private val passwordGenerator: PasswordGenerator,
    private val calculateSecurityScoreUseCase: CalculateSecurityScoreUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return GeneratePasswordViewModel(
            passwordGenerator,
            calculateSecurityScoreUseCase
        ) as T
    }
}
