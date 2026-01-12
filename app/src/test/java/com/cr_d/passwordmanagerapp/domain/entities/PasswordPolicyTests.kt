package com.cr_d.passwordmanagerapp.domain.entities

import com.cr_d.passwordmanagerapp.domain.policy.PasswordPolicy
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class PasswordPolicyTests {

    @Test
    fun common_pattern_regex_should_match_common_weak_passwords() {
        val regex = Regex(PasswordPolicy.REGEX_COMMON_PATTERNS, RegexOption.IGNORE_CASE)
        Assertions.assertTrue(regex.containsMatchIn("password123"))
        Assertions.assertTrue(regex.containsMatchIn("QwErTy"))
        Assertions.assertTrue(regex.containsMatchIn("adminRoot"))
        Assertions.assertTrue(regex.containsMatchIn("iloveyou99"))
    }

    @Test
    fun common_pattern_regex_should_not_match_random_strong_passwords() {
        val regex = Regex(PasswordPolicy.REGEX_COMMON_PATTERNS, RegexOption.IGNORE_CASE)
        Assertions.assertFalse(regex.containsMatchIn("vT7!kP3#xR9qLm2&HzYw4"))
        Assertions.assertFalse(regex.containsMatchIn("G7v!k9R#pTq2"))
    }
}