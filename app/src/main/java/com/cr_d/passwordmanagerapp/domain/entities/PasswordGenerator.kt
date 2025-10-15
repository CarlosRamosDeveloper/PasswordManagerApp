package com.cr_d.passwordmanagerapp.domain.entities

class PasswordGenerator (
    val hasLowerCase: Boolean = true,
    val hasUpperCase: Boolean = false,
    val hasNumbers: Boolean = false,
    val hasSpecials: Boolean = false,
    val minLength: Int= 8,
    val maxLength: Int = 20
    ) {

    fun generatePassword(): String {
        return "ASDF"
    }
}