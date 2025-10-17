package com.cr_d.passwordmanagerapp.application.use_cases

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

import com.cr_d.passwordmanagerapp.domain.value_objects.PasswordDataGeneration
import com.cr_d.passwordmanagerapp.domain.entities.PasswordAnalyzer
import com.cr_d.passwordmanagerapp.domain.entities.PasswordGenerator

class GeneratePasswordUseCaseTest {
    @Test
    fun should_generate_password_with_expected_length() {
        val passwordData = PasswordDataGeneration(
            true, true, true, true, 16
        )
        val useCase = GeneratePasswordUseCase(PasswordGenerator(passwordData))

        val password = useCase()

        Assertions.assertEquals(16, password.length)
    }

    @Test
    fun should_generate_password_with_expected_flags() {
        val passwordData = PasswordDataGeneration(
            true, false, false, true, 16
        )
        val useCase = GeneratePasswordUseCase(PasswordGenerator(passwordData))
        val password = useCase()

        val analyzedPassword = PasswordAnalyzer.analyze(password)

        Assertions.assertTrue(analyzedPassword.hasLowerCase)
        Assertions.assertFalse(analyzedPassword.hasUpperCase)
        Assertions.assertFalse(analyzedPassword.hasNumbers)
        Assertions.assertTrue(analyzedPassword.hasSpecials)
    }
}