package com.cr_d.passwordmanagerapp.domain.value_objects

data class PasswordDataGeneration(
    val hasLowerCase : Boolean,
    val hasUpperCase : Boolean,
    val hasNumbers : Boolean,
    val hasSpecials : Boolean,
    var passwordLength: Int
)