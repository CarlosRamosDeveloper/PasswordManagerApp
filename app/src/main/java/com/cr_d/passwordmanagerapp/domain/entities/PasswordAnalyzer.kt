package com.cr_d.passwordmanagerapp.domain.entities

import com.cr_d.passwordmanagerapp.domain.value_objects.PasswordMetadata

object PasswordAnalyzer {
    fun analyze(password: String): PasswordMetadata {
        val hasLower = password.any { it.isLowerCase() }
        val hasUpper = password.any { it.isUpperCase() }
        val hasNumber = password.any { it.isDigit() }
        val hasSpecial = password.any { it in PasswordPolicy.SYMBOL_CHARS }

        return PasswordMetadata(
            hasLowerCase = hasLower,
            hasUpperCase = hasUpper,
            hasNumbers = hasNumber,
            hasSpecials = hasSpecial
        )
    }
}