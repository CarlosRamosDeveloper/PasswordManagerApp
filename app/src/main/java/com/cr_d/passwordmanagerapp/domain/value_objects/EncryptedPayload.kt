package com.cr_d.passwordmanagerapp.domain.value_objects

data class EncryptedPayload(
    val encryptedText: ByteArray,
    val iv: ByteArray
)