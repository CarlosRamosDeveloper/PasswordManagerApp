package com.cr_d.passwordmanagerapp.application.use_cases

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

import com.cr_d.passwordmanagerapp.domain.value_objects.PasswordDataGeneration
import com.cr_d.passwordmanagerapp.domain.services.PasswordAnalyzer
import com.cr_d.passwordmanagerapp.domain.services.PasswordGenerator
import com.cr_d.passwordmanagerapp.domain.use_cases.password_use_cases.GeneratePasswordUseCase

class GeneratePasswordUseCaseTest {
    @Test
    fun should_generate_password_with_expected_length() {
        val passwordData = PasswordDataGeneration(
            true, true, true, true, 16
        )
        val useCase = GeneratePasswordUseCase(PasswordGenerator())

        val password = useCase(passwordData)

        Assertions.assertEquals(16, password.length)
    }

    @Test
    fun should_generate_password_with_expected_flags() {
        val passwordData = PasswordDataGeneration(
            true, false, false, true, 16
        )
        val useCase = GeneratePasswordUseCase(PasswordGenerator())
        val password = useCase(passwordData)

        val analyzedPassword = PasswordAnalyzer.analyze(password)

        Assertions.assertTrue(analyzedPassword.hasLowerCase)
        Assertions.assertFalse(analyzedPassword.hasUpperCase)
        Assertions.assertFalse(analyzedPassword.hasNumbers)
        Assertions.assertTrue(analyzedPassword.hasSpecials)
    }
}