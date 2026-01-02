package com.cr_d.passwordmanagerapp.domain.value_objects

import com.cr_d.passwordmanagerapp.data.crypto.EncryptedPayload

data class Account (
    val id: Long,
    val cipheredAccount: EncryptedPayload,
    val cipheredNotes: EncryptedPayload
)
