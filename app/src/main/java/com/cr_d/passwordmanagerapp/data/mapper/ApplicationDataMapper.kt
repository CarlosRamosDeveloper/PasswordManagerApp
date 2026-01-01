package com.cr_d.passwordmanagerapp.data.mapper

import com.cr_d.passwordmanagerapp.data.entities.ApplicationEntity
import com.cr_d.passwordmanagerapp.domain.value_objects.ApplicationData

fun ApplicationData.toEntity(): ApplicationEntity{
    return ApplicationEntity(
        id = id,
        appName = appName,
        appUrl = appUrl,
        cipheredNotes = cipheredNotes.encryptedText,
        notesIv = cipheredNotes.iv
    )
}
