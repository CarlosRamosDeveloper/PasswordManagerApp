package com.cr_d.passwordmanagerapp.domain.entities

class PasswordGenerator (
    val hasLowerCase: Boolean = true,
    val hasUpperCase: Boolean = false,
    val hasNumbers: Boolean = false,
    val hasSpecials: Boolean = false,
    var passwordLength: Int= PasswordPolicy.MIN_GENERATED_LENGTH,
    ){

    fun generatePassword(): String {

        if (passwordLength < PasswordPolicy.MIN_GENERATED_LENGTH) passwordLength = PasswordPolicy.MIN_GENERATED_LENGTH
        if (passwordLength > PasswordPolicy.MAX_LENGTH) passwordLength = PasswordPolicy.MAX_LENGTH

        val availableSets = buildList {
            if (hasLowerCase) add(PasswordPolicy.LOWER_CHARS)
            if (hasUpperCase) add(PasswordPolicy.UPPER_CHARS)
            if (hasNumbers) add(PasswordPolicy.NUMBER_CHARS)
            if (hasSpecials) add(PasswordPolicy.SYMBOL_CHARS)
        }

        require(availableSets.isNotEmpty()) { "At least one character set must be selected" }

        val passwordChars = mutableListOf<Char>()

        if (hasLowerCase) passwordChars += PasswordPolicy.LOWER_CHARS.random()
        if (hasUpperCase) passwordChars += PasswordPolicy.UPPER_CHARS.random()
        if (hasNumbers) passwordChars += PasswordPolicy.NUMBER_CHARS.random()
        if (hasSpecials) passwordChars += PasswordPolicy.SYMBOL_CHARS.random()

        while (passwordChars.size < passwordLength) {
            val set = availableSets.random()
            passwordChars += set.random()
        }

        passwordChars.shuffle()
        return passwordChars.joinToString("")
    }
}