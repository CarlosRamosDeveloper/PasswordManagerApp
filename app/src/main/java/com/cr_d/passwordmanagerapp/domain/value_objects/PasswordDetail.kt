package com.cr_d.passwordmanagerapp.domain.value_objects

import com.cr_d.passwordmanagerapp.domain.value_objects.EncryptedPayload
import com.cr_d.passwordmanagerapp.ui.dto.PasswordAccountInfoDto
import com.cr_d.passwordmanagerapp.ui.dto.PasswordAppInfoDto

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