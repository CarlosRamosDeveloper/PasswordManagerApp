package com.cr_d.passwordmanagerapp.domain.value_objects

data class PasswordData (
    val id : Int,
    val account : String,
    val plainPassword : PlainPassword,
    val hasLowerCase : Boolean,
    val hasUpperCase : Boolean,
    val hasNumbers : Boolean,
    val hasSpecials : Boolean,
    val application : String,
    val url : String,
    val creationDate : String,
    val lastUpdate : String,
    val securityScore : Double
)