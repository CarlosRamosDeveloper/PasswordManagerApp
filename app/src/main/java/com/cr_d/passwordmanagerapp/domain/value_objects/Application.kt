package com.cr_d.passwordmanagerapp.domain.value_objects

import com.cr_d.passwordmanagerapp.domain.value_objects.EncryptedPayload

data class Application(
    val id: Long,
    val appName: String,
    val appUrl: String?,
    val cipheredNotes: EncryptedPayload
)
