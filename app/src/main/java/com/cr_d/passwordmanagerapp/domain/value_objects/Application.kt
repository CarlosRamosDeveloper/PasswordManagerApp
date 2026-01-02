package com.cr_d.passwordmanagerapp.domain.value_objects

import com.cr_d.passwordmanagerapp.data.crypto.EncryptedPayload

data class Application(
    val id: Long,
    val appName: String,
    val appUrl: String?,
    val cipheredNotes: EncryptedPayload
)
