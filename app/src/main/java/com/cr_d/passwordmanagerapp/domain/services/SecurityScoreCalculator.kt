package com.cr_d.passwordmanagerapp.domain.services

import kotlin.math.log2

import com.cr_d.passwordmanagerapp.domain.policy.PasswordPolicy
import com.cr_d.passwordmanagerapp.domain.use_cases.AnalyzePasswordUseCase

class SecurityScoreCalculator(
    val analyzer: AnalyzePasswordUseCase
) {
    fun calculate(password: String): Double {
        val info = analyzer(password)
        var score = 0.0
        val length = password.length

        if (length < 8) return 0.0

        val lengthScore = (length.toDouble() / 20).coerceAtMost(1.0) * 4.0
        score += lengthScore

        val charsetSize = buildSet {
            if (info.hasLowerCase) addAll(PasswordPolicy.LOWER_CHARS)
            if (info.hasUpperCase) addAll(PasswordPolicy.UPPER_CHARS)
            if (info.hasNumbers) addAll(PasswordPolicy.NUMBER_CHARS)
            if (info.hasSpecials) addAll(PasswordPolicy.SYMBOL_CHARS)
        }.size

        val entropy = password.length * log2(charsetSize.toDouble())
        val entropyScore = (entropy / 80).coerceAtMost(1.0) * 3.0

        score += entropyScore

        val typeCount = listOf(
            info.hasLowerCase,
            info.hasUpperCase,
            info.hasNumbers,
            info.hasSpecials
        ).count()

        score += when (typeCount) {
            2 -> 1.0
            3 -> 2.0
            4 -> 3.0
            else -> 0.0
        }

        if (length < 12) score -= 2.5

        if (Regex("(.)\\1{2,}").containsMatchIn(password)) score -= 1.0
        if (Regex(PasswordPolicy.REGEX_COMMON_PATTERNS, RegexOption.IGNORE_CASE).containsMatchIn(password)) score -= 3.0

        val final = score.coerceIn(0.0, 10.0)

        return final
    }
}