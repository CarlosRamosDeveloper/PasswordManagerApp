package com.cr_d.passwordmanagerapp.domain.policy

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
    val COMMON_PATTERNS = listOf(
        "1234", "abcd", "qwerty", "asdf", "zxcv", "ñlkj", "poiu", "mnbv",
        "password", "admin", "letmein", "welcome", "login", "pass", "user", "root",
        "1111", "0000", "iloveyou", "dragon", "monkey", "test", "guest"
    )
    val REGEX_COMMON_PATTERNS = "(?i)(${COMMON_PATTERNS.joinToString("|")})"
}