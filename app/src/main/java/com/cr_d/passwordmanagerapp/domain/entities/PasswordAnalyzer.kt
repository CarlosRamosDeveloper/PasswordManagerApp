package com.cr_d.passwordmanagerapp.domain.entities

import com.cr_d.passwordmanagerapp.domain.value_objects.PasswordDataAnalysis
import com.cr_d.passwordmanagerapp.domain.value_objects.PlainPassword

object PasswordAnalyzer {
    fun analyze(password: String): PasswordDataAnalysis {
        val hasLower = password.any { it.isLowerCase() }
        val hasUpper = password.any { it.isUpperCase() }
        val hasNumber = password.any { it.isDigit() }
        val hasSpecial = password.any { it in PasswordPolicy.SYMBOL_CHARS }

        return PasswordDataAnalysis(
            hasLowerCase = hasLower,
            hasUpperCase = hasUpper,
            hasNumbers = hasNumber,
            hasSpecials = hasSpecial
        )
    }
}