package com.cr_d.passwordmanagerapp.domain.entities

import com.cr_d.passwordmanagerapp.domain.value_objects.PasswordDataAnalysis
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class SecurityScoreCalculatorTest {

    @Test
    fun should_generate_security_score() {
        val data = PasswordDataAnalysis(
            password = "wCut;eETPco[q3cBGhd7",
            hasLowerCase = true,
            hasUpperCase = true,
            hasNumbers = true,
            hasSpecials = true,
        )
        val securityScore = SecurityScoreCalculator(data).calculate()

        Assertions.assertEquals(0.0, securityScore)
    }


    @Test
    fun should_give_low_score_for_short_weak_password(){
        val data = PasswordDataAnalysis(
            password = "abc",
            hasLowerCase = true,
            hasUpperCase = false,
            hasNumbers = false,
            hasSpecials = false,
        )
        val score = SecurityScoreCalculator(data).calculate()

        Assertions.assertTrue(score < 3.0)
    }

    @Test
    fun should_penalize_password_containing_common_patter(){
        val data = PasswordDataAnalysis(
            password = "password123",
            hasLowerCase = true,
            hasUpperCase = true,
            hasNumbers = false,
            hasSpecials = false,
        )
        val score = SecurityScoreCalculator(data).calculate()

        Assertions.assertTrue(score < 4.0)
    }

    @Test
    fun should_penalize_repeated_characters(){
        val data = PasswordDataAnalysis(
            password = "aaaBBB111",
            hasLowerCase = true,
            hasUpperCase = true,
            hasNumbers = true,
            hasSpecials = false,
        )
        val score = SecurityScoreCalculator(data).calculate()

        Assertions.assertTrue(score < 5.0)
    }

    @Test
    fun should_give_higher_score_for_long_mixed_password(){
        val data = PasswordDataAnalysis(
            password = "AbcD1234!@#",
            hasLowerCase = true,
            hasUpperCase = true,
            hasNumbers = true,
            hasSpecials = true,
        )
        val score = SecurityScoreCalculator(data).calculate()

        Assertions.assertTrue(score > 6.0)
    }

    @Test
    fun should_give_maximum_score_for_very_strong_password(){
        val data = PasswordDataAnalysis(
            password = "AbC123!@#xYz89PLmn&*qRsTuvW",
            hasLowerCase = true,
            hasUpperCase = true,
            hasNumbers = true,
            hasSpecials = true,
        )
        val score = SecurityScoreCalculator(data).calculate()

        Assertions.assertEquals(10, score)
    }

    @Test
    fun should_penalize_all_numeric_passwords(){
        val data = PasswordDataAnalysis(
            password = "1234567890",
            hasLowerCase = false,
            hasUpperCase = false,
            hasNumbers = true,
            hasSpecials = false,
        )
        val score = SecurityScoreCalculator(data).calculate()

        Assertions.assertTrue(score < 3.0)
    }

    @Test
    fun should_handle_passwords_shorter_than_8_correctly(){
        val data = PasswordDataAnalysis(
            password = "Ab1!",
            hasLowerCase = true,
            hasUpperCase = true,
            hasNumbers = true,
            hasSpecials = true,
        )
        val score = SecurityScoreCalculator(data).calculate()

        Assertions.assertTrue(score < 3.0)
    }
}