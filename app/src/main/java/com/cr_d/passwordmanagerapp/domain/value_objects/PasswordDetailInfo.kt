package com.cr_d.passwordmanagerapp.domain.value_objects

import com.cr_d.passwordmanagerapp.ui.dto.PasswordAccountInfoDto
import com.cr_d.passwordmanagerapp.ui.dto.PasswordAppInfoDto

data class PasswordDetailInfo(
    val appData: PasswordAppInfoDto,
    val accountData: PasswordAccountInfoDto,
    val metadata: PasswordMetadata,
    val score: Double
)
