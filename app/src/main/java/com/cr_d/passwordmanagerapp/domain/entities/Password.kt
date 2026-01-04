package com.cr_d.passwordmanagerapp.domain.entities

import com.cr_d.passwordmanagerapp.domain.value_objects.DateInfo
import com.cr_d.passwordmanagerapp.domain.value_objects.EncryptedPayload

data class Password (
    val id: Long,
    val cipheredPassword : EncryptedPayload,
    val appId: Long,
    val accountId: Long,
    val dateInfo: DateInfo,
    val cipheredNotes: EncryptedPayload
)