package com.cr_d.passwordmanagerapp.data.dto

data class PasswordCreationData (
    val password: String,
    val appId: Long,
    val accId: Long,
    val notes: String
)
