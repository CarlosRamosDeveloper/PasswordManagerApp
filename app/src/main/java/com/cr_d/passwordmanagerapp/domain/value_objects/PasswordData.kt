package com.cr_d.passwordmanagerapp.domain.value_objects

import com.cr_d.passwordmanagerapp.data.crypto.EncryptedPayload

data class PasswordData (
    val id : Long,
    val cipheredPassword : EncryptedPayload,
    val appInfo: ApplicationInfo,
    val metadata: PasswordMetadata,
    val dateInfo: DateInfo,
    val score : Double,
    val cipheredNotes: EncryptedPayload
)