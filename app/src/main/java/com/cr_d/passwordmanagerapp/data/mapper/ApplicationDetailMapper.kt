package com.cr_d.passwordmanagerapp.data.mapper

import com.cr_d.passwordmanagerapp.data.dto.ApplicationDetail
import com.cr_d.passwordmanagerapp.data.dto.ApplicationDetailInfo
import com.cr_d.passwordmanagerapp.domain.entities.Application
import com.cr_d.passwordmanagerapp.ui.model.ApplicationUiState

fun ApplicationDetail.toDomain(): Application = Application(
    id = id,
    appName = appName,
    appUrl = appUrl,
    cipheredNotes = cipheredNotes
)

fun ApplicationDetail.toUiState(extraData: ApplicationDetailInfo): ApplicationUiState {
    return ApplicationUiState(
        id = id,
        applicationName = appName,
        applicationUrl = appUrl,
        notes = extraData.decipheredNotes,
        passwords = extraData.passwords
    )
}