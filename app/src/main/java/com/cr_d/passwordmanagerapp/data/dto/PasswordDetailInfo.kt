package com.cr_d.passwordmanagerapp.data.dto

import com.cr_d.passwordmanagerapp.domain.value_objects.PasswordMetadata

data class PasswordDetailInfo(
    val appData: PasswordAppInfoDto,
    val accountData: PasswordAccountInfoDto,
    val metadata: PasswordMetadata,
    val score: Double
)