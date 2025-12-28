package com.cr_d.passwordmanagerapp.domain.value_objects

data class PasswordData (
    val id : Long,
    val plainPassword : PlainPassword,
    val appInfo: ApplicationInfo,
    val metadata: PasswordMetadata,
    val dateInfo: DateInfo,
    val score : Double,
    val notes: String = ""
)