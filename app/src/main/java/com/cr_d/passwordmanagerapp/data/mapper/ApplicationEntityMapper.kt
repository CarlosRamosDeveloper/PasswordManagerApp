package com.cr_d.passwordmanagerapp.data.mapper

import com.cr_d.passwordmanagerapp.data.crypto.EncryptedPayload
import com.cr_d.passwordmanagerapp.data.entities.ApplicationEntity
import com.cr_d.passwordmanagerapp.domain.value_objects.ApplicationData

fun ApplicationEntity.toDomain(): ApplicationData {
    val cipheredNotes = EncryptedPayload(
        this.cipheredNotes, this.notesIv
    )

    return ApplicationData(
        id = id,
        appName = appName,
        appUrl = appUrl,
        cipheredNotes = cipheredNotes
    )
}