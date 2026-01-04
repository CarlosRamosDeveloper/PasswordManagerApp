package com.cr_d.passwordmanagerapp.domain.value_objects

import com.cr_d.passwordmanagerapp.domain.value_objects.EncryptedPayload

data class Account (
    val id: Long,
    val cipheredAccount: EncryptedPayload,
    val cipheredNotes: EncryptedPayload
)
