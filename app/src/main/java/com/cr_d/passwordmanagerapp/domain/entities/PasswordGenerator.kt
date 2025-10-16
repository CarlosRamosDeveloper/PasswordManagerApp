package com.cr_d.passwordmanagerapp.domain.entities

import com.cr_d.passwordmanagerapp.domain.value_objects.PasswordDataGeneration

class PasswordGenerator (
    var passwordDataGeneration: PasswordDataGeneration
    ){

    fun generatePassword(): String {

        if (passwordDataGeneration.passwordLength < PasswordPolicy.MIN_GENERATED_LENGTH) passwordDataGeneration.passwordLength = PasswordPolicy.MIN_GENERATED_LENGTH
        if (passwordDataGeneration.passwordLength > PasswordPolicy.MAX_LENGTH) passwordDataGeneration.passwordLength = PasswordPolicy.MAX_LENGTH

        val availableSets = buildList {
            if (passwordDataGeneration.hasLowerCase) add(PasswordPolicy.LOWER_CHARS)
            if (passwordDataGeneration.hasUpperCase) add(PasswordPolicy.UPPER_CHARS)
            if (passwordDataGeneration.hasNumbers) add(PasswordPolicy.NUMBER_CHARS)
            if (passwordDataGeneration.hasSpecials) add(PasswordPolicy.SYMBOL_CHARS)
        }

        require(availableSets.isNotEmpty()) { "At least one character set must be selected" }

        val passwordChars = mutableListOf<Char>()

        if (passwordDataGeneration.hasLowerCase) passwordChars += PasswordPolicy.LOWER_CHARS.random()
        if (passwordDataGeneration.hasUpperCase) passwordChars += PasswordPolicy.UPPER_CHARS.random()
        if (passwordDataGeneration.hasNumbers) passwordChars += PasswordPolicy.NUMBER_CHARS.random()
        if (passwordDataGeneration.hasSpecials) passwordChars += PasswordPolicy.SYMBOL_CHARS.random()

        while (passwordChars.size < passwordDataGeneration.passwordLength) {
            val set = availableSets.random()
            passwordChars += set.random()
        }

        passwordChars.shuffle()
        return passwordChars.joinToString("")
    }
}