package com.cr_d.passwordmanagerapp.domain.entities

import com.cr_d.passwordmanagerapp.domain.services.PasswordAnalyzer
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class PasswordAnalyzerTest {
    @Test
    fun analyze_should_detect_character_types_correctly() {
        var analysis = PasswordAnalyzer.analyze("abc")
        Assertions.assertTrue(analysis.hasLowerCase)
        Assertions.assertFalse(analysis.hasUpperCase)
        Assertions.assertFalse(analysis.hasNumbers)
        Assertions.assertFalse(analysis.hasSpecials)

        analysis = PasswordAnalyzer.analyze("aB3!")
        Assertions.assertTrue(analysis.hasLowerCase)
        Assertions.assertTrue(analysis.hasUpperCase)
        Assertions.assertTrue(analysis.hasNumbers)
        Assertions.assertTrue(analysis.hasSpecials)
    }

    @Test
    fun analyze_should_detect_specials_only() {
        val analysis = PasswordAnalyzer.analyze("!!!!!!")
        Assertions.assertFalse(analysis.hasLowerCase)
        Assertions.assertFalse(analysis.hasUpperCase)
        Assertions.assertFalse(analysis.hasNumbers)
        Assertions.assertTrue(analysis.hasSpecials)
    }
}