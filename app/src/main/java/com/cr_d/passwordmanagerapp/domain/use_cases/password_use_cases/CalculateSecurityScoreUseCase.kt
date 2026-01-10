package com.cr_d.passwordmanagerapp.domain.use_cases.password_use_cases

import com.cr_d.passwordmanagerapp.domain.services.SecurityScoreCalculator

class CalculateSecurityScoreUseCase (
    private val securityScoreCalculator: SecurityScoreCalculator
) {
    operator fun invoke(plainPassword: String): Double{
        return securityScoreCalculator.calculate(plainPassword)
    }
}