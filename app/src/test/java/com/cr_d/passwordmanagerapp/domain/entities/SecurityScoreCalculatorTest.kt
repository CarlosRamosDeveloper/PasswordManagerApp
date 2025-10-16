package com.cr_d.passwordmanagerapp.domain.entities

import com.cr_d.passwordmanagerapp.domain.value_objects.PasswordDataAnalysis
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class SecurityScoreCalculatorTest {

    @Test
    fun should_generate_security_score(){
        val data = PasswordDataAnalysis(
            password= "wCut;eETPco[q3cBGhd7",
            hasLowerCase = true,
            hasUpperCase = true,
            hasNumbers = true,
            hasSpecials = true,
        )
        val securityScore = SecurityScoreCalculator(data).calculate()

        Assertions.assertEquals(0.0, securityScore)
    }
}