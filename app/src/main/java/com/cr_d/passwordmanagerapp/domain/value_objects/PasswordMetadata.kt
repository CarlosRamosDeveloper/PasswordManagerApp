package com.cr_d.passwordmanagerapp.domain.value_objects

data class PasswordMetadata(
    val hasLowerCase: Boolean = false,
    val hasUpperCase: Boolean = false,
    val hasNumbers: Boolean = false,
    val hasSpecials: Boolean = false,
)
