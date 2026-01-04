package com.cr_d.passwordmanagerapp.data.dto

import com.cr_d.passwordmanagerapp.domain.value_objects.DateInfo
import com.cr_d.passwordmanagerapp.domain.value_objects.EncryptedPayload
import com.cr_d.passwordmanagerapp.domain.value_objects.PasswordMetadata

data class PasswordDetail(
    val id : Long,
    val cipheredPassword : EncryptedPayload,
    val appData: PasswordAppInfoDto,
    val accountData: PasswordAccountInfoDto,
    val metadata: PasswordMetadata,
    val dateInfo: DateInfo,
    val score : Double,
    val cipheredNotes: EncryptedPayload
)