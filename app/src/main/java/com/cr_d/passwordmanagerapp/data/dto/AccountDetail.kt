package com.cr_d.passwordmanagerapp.data.dto

import com.cr_d.passwordmanagerapp.domain.value_objects.EncryptedPayload

data class AccountDetail(
    val id: Long,
    val cipheredAccount : EncryptedPayload,
    val cipheredNotes: EncryptedPayload,
    val totalApplications: Int
)