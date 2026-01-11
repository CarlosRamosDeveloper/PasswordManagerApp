package com.cr_d.passwordmanagerapp.domain.services

import java.security.MessageDigest

class HashService {
    fun convertToSha256(input: String): String{
        val normalized = input.trim().lowercase()
        val bytes = MessageDigest
            .getInstance("SHA-256")
            .digest(normalized.toByteArray(Charsets.UTF_8))

        return bytes.joinToString("") { "%02x".format(it) }
    }
}
