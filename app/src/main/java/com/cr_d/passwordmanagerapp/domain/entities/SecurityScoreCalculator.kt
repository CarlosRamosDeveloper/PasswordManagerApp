package com.cr_d.passwordmanagerapp.domain.entities

import com.cr_d.passwordmanagerapp.domain.value_objects.PasswordDataAnalysis
import kotlin.math.min

class SecurityScoreCalculator(
    val passwordInfo: PasswordDataAnalysis
) {
    fun calculate(): Double {
        var score = 0.0
        val length = passwordInfo.password.length

        score += min(length*4, 40)

        val typeCount = listOf(
            passwordInfo.hasLowerCase,
            passwordInfo.hasUpperCase,
            passwordInfo.hasNumbers,
            passwordInfo.hasSpecials
        ).count()

        score += when (typeCount) {
            2 -> 1.0
            3 -> 2.0
            4 -> 3.0
            else -> 0.0
        }

        if (length < 8) score -= 5.0
        else if (length < 12) score -= 2.5

        if (Regex("(.)\\1{2,}").containsMatchIn(passwordInfo.password)) score -= 1.0
        if (Regex("123|abc|qwe|password|admin", RegexOption.IGNORE_CASE).containsMatchIn(passwordInfo.password)) score -= 3.0

        val final = score.coerceIn(0.0, 10.0)

        return final
    }
}