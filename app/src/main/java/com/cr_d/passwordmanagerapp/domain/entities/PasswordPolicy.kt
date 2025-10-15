package com.cr_d.passwordmanagerapp.domain.entities

object PasswordPolicy {
    const val MIN_LENGTH = 8
    const val MIN_GENERATED_LENGTH = 16
    const val MAX_LENGTH = 128

    val LOWER_CHARS = ('a'..'z').toList()
    val UPPER_CHARS = ('A'..'Z').toList()
    val NUMBER_CHARS = ('0'..'9').toList()
    val SYMBOL_CHARS = listOf(
        '!', '@', '#', '$', '%', '^', '&', '*', '(', ')',
        '_', '-', '+', '=', '{', '}', '[', ']', '|', '\\',
        ';', ':', '"', '\'', '<', '>', '.', ',', '/', '?'
    )
}