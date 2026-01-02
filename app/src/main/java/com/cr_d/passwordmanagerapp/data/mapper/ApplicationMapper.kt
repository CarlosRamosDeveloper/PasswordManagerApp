package com.cr_d.passwordmanagerapp.data.mapper

import com.cr_d.passwordmanagerapp.data.entities.ApplicationEntity
import com.cr_d.passwordmanagerapp.domain.value_objects.Application

fun Application.toEntity(): ApplicationEntity{
    return ApplicationEntity(
        id = id,
        appName = appName,
        appUrl = appUrl,
        cipheredNotes = cipheredNotes.encryptedText,
        notesIv = cipheredNotes.iv
    )
}
