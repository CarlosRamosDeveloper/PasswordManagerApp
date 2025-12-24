package com.cr_d.passwordmanagerapp.domain.value_objects

data class PasswordDataAnalysis(
    val hasLowerCase : Boolean,
    val hasUpperCase : Boolean,
    val hasNumbers : Boolean,
    val hasSpecials : Boolean,
)
