package com.cr_d.passwordmanagerapp.domain.value_objects

import java.time.LocalDate

data class PasswordMetadata(
    val hasLowerCase: Boolean,
    val hasUpperCase: Boolean,
    val hasNumbers: Boolean,
    val hasSpecials: Boolean,
    val creationDate: LocalDate,
    val lastUpdate: LocalDate,
)
