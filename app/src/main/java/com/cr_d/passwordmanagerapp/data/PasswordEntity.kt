package com.cr_d.passwordmanagerapp.data

import java.time.LocalDate

data class PasswordEntity(
    val plainPassword: String,
    val appName: String,
    val appUrl: String,
    val account: String,
    val creationDate: LocalDate,
    val lastUpdate: LocalDate,
    val notes: String,
)
