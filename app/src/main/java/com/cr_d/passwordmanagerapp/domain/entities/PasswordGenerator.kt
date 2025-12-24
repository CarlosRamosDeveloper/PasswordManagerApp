package com.cr_d.passwordmanagerapp.domain.entities

import com.cr_d.passwordmanagerapp.domain.value_objects.PasswordDataGeneration

class PasswordGenerator (){
    fun generatePassword(passwordData: PasswordDataGeneration): String {

        if (passwordData.passwordLength < PasswordPolicy.MIN_GENERATED_LENGTH) passwordData.passwordLength = PasswordPolicy.MIN_GENERATED_LENGTH
        if (passwordData.passwordLength > PasswordPolicy.MAX_LENGTH) passwordData.passwordLength = PasswordPolicy.MAX_LENGTH

        val availableSets = buildList {
            if (passwordData.hasLowerCase) add(PasswordPolicy.LOWER_CHARS)
            if (passwordData.hasUpperCase) add(PasswordPolicy.UPPER_CHARS)
            if (passwordData.hasNumbers) add(PasswordPolicy.NUMBER_CHARS)
            if (passwordData.hasSpecials) add(PasswordPolicy.SYMBOL_CHARS)
        }

        require(availableSets.isNotEmpty()) { "At least one character set must be selected" }

        val passwordChars = mutableListOf<Char>()

        if (passwordData.hasLowerCase) passwordChars += PasswordPolicy.LOWER_CHARS.random()
        if (passwordData.hasUpperCase) passwordChars += PasswordPolicy.UPPER_CHARS.random()
        if (passwordData.hasNumbers) passwordChars += PasswordPolicy.NUMBER_CHARS.random()
        if (passwordData.hasSpecials) passwordChars += PasswordPolicy.SYMBOL_CHARS.random()

        while (passwordChars.size < passwordData.passwordLength) {
            val set = availableSets.random()
            passwordChars += set.random()
        }

        passwordChars.shuffle()
        return passwordChars.joinToString("")
    }
}