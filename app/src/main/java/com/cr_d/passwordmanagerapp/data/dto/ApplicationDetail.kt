package com.cr_d.passwordmanagerapp.data.dto

import com.cr_d.passwordmanagerapp.domain.value_objects.EncryptedPayload

data class ApplicationDetail (
    val id: Long,
    val appName: String,
    val appUrl: String = "",
    val cipheredNotes: EncryptedPayload
)