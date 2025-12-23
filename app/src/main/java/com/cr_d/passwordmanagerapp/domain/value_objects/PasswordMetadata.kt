package com.cr_d.passwordmanagerapp.domain.value_objects

data class PasswordMetadata(
    val hasLowerCase: Boolean,
    val hasUpperCase: Boolean,
    val hasNumbers: Boolean,
    val hasSpecials: Boolean,
    val creationDate: String,
    val lastUpdate: String,
)
