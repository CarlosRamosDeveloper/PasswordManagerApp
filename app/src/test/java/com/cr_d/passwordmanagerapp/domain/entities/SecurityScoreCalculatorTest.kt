package com.cr_d.passwordmanagerapp.domain.entities

import com.cr_d.passwordmanagerapp.domain.services.SecurityScoreCalculator
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class SecurityScoreCalculatorTest {

    @Test
    fun should_give_low_score_for_short_weak_password(){
        val score = SecurityScoreCalculator().calculate("abc")

        Assertions.assertTrue(score == 0.0)
    }

    @Test
    fun should_penalize_password_containing_common_patter(){
        val score = SecurityScoreCalculator().calculate("password123")

        Assertions.assertTrue(score < 2.0)
    }

    @Test
    fun should_penalize_repeated_characters(){
        val score = SecurityScoreCalculator().calculate("aaaBBB111")

        Assertions.assertTrue(score < 4.0)
    }

    @Test
    fun should_give_higher_score_for_long_mixed_password(){
        val score = SecurityScoreCalculator().calculate("G7v!k9R#pTq2")

        Assertions.assertTrue(score > 6.0)
    }

    @Test
    fun should_give_maximum_score_for_very_strong_password(){
        val score = SecurityScoreCalculator().calculate("Yq4!mR7#bT2%hZ9&kLp6wX")

        Assertions.assertEquals(10.0, score)
    }

    @Test
    fun should_penalize_all_numeric_passwords(){
        val score = SecurityScoreCalculator().calculate("1234567890")

        Assertions.assertTrue(score < 4.0)
    }

    @Test
    fun should_handle_passwords_shorter_than_8_correctly(){
        val score = SecurityScoreCalculator().calculate("Ab1!")

        Assertions.assertTrue(score == 0.0)
    }
}