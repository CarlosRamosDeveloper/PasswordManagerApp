package com.cr_d.passwordmanagerapp.data.crypto

data class EncryptedPayload(
    val encryptedText: ByteArray,
    val iv: ByteArray
)