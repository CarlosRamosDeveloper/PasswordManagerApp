package com.cr_d.passwordmanagerapp.data.mapper

import com.cr_d.passwordmanagerapp.domain.value_objects.EncryptedPayload
import com.cr_d.passwordmanagerapp.data.entities.ApplicationEntity
import com.cr_d.passwordmanagerapp.domain.entities.Application

fun ApplicationEntity.toDomain(): Application {
    val cipheredNotes = EncryptedPayload(
        this.cipheredNotes, this.notesIv
    )

    return Application(
        id = id,
        appName = appName,
        appUrl = appUrl,
        cipheredNotes = cipheredNotes
    )
}