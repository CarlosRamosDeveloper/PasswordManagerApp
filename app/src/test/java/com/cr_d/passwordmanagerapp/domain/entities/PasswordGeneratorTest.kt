package com.cr_d.passwordmanagerapp.domain.entities

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class PasswordGeneratorTest {

    @Test
    fun `should create a default password`(){
        val testSubject = PasswordGenerator()

        Assertions.assertTrue(testSubject.hasLowerCase)
        Assertions.assertFalse(testSubject.hasUpperCase)
        Assertions.assertFalse(testSubject.hasNumbers)
        Assertions.assertFalse(testSubject.hasSpecials)
        Assertions.assertEquals(16, testSubject.passwordLength)
    }

    @Test
    fun should_generate_password_with_correct_length_when_length_within_limits() {
        val generator = PasswordGenerator(
            hasLowerCase = true,
            hasUpperCase = true,
            hasNumbers = true,
            hasSpecials = true,
            passwordLength = 20
        )
        val password = generator.generatePassword()
        Assertions.assertEquals(20, password.length)
    }

    @Test
    fun should_generate_password_with_min_generated_length_when_length_too_short() {
        val generator = PasswordGenerator(passwordLength = 5)
        val password = generator.generatePassword()
        Assertions.assertTrue(password.length >= PasswordPolicy.MIN_GENERATED_LENGTH)
    }

    @Test
    fun should_generate_password_with_max_length_when_length_too_long() {
        val generator = PasswordGenerator(passwordLength = 200)
        val password = generator.generatePassword()
        Assertions.assertTrue(password.length <= PasswordPolicy.MAX_LENGTH)
    }

    @Test
    fun should_include_lowercase_characters_when_enabled() {
        val generator = PasswordGenerator(hasLowerCase = true)
        val password = generator.generatePassword()
        Assertions.assertTrue(password.any { it.isLowerCase() })
    }

    @Test
    fun should_include_uppercase_characters_when_enabled() {
        val generator = PasswordGenerator(hasUpperCase = true)
        val password = generator.generatePassword()
        Assertions.assertTrue(password.any { it.isUpperCase() })
    }

    @Test
    fun should_include_number_characters_when_enabled() {
        val generator = PasswordGenerator(hasNumbers = true)
        val password = generator.generatePassword()
        Assertions.assertTrue(password.any { it.isDigit() })
    }

    @Test
    fun should_include_symbol_characters_when_enabled() {
        val generator = PasswordGenerator(hasSpecials = true)
        val password = generator.generatePassword()
        Assertions.assertTrue(password.any { it in PasswordPolicy.SYMBOL_CHARS })
    }

    @Test
    fun should_throw_exception_when_no_character_sets_enabled() {
        val generator = PasswordGenerator(
            hasLowerCase = false,
            hasUpperCase = false,
            hasNumbers = false,
            hasSpecials = false
        )

        Assertions.assertThrows(IllegalArgumentException::class.java) {
            generator.generatePassword()
        }
    }

    @Test
    fun should_generate_password_with_all_character_types_when_all_enabled() {
        val generator = PasswordGenerator(
            hasLowerCase = true,
            hasUpperCase = true,
            hasNumbers = true,
            hasSpecials = true,
            passwordLength = 50
        )

        val password = generator.generatePassword()

        Assertions.assertTrue(password.any { it.isLowerCase() })
        Assertions.assertTrue(password.any { it.isUpperCase() })
        Assertions.assertTrue(password.any { it.isDigit() })
        Assertions.assertTrue(password.any { it in PasswordPolicy.SYMBOL_CHARS })
        Assertions.assertEquals(50, password.length)
    }
}