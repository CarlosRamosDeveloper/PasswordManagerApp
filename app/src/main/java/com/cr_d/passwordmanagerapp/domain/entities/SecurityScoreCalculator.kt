package com.cr_d.passwordmanagerapp.domain.entities

import com.cr_d.passwordmanagerapp.domain.value_objects.PasswordDataAnalysis

class SecurityScoreCalculator(
    val password: PasswordDataAnalysis
) {
    fun calculate(): Double {
        var score = 0.0

        return score
    }
}