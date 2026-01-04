package com.cr_d.passwordmanagerapp.data.mapper

import java.time.LocalDate

import com.cr_d.passwordmanagerapp.domain.value_objects.EncryptedPayload
import com.cr_d.passwordmanagerapp.data.entities.PasswordEntity
import com.cr_d.passwordmanagerapp.domain.value_objects.DateInfo
import com.cr_d.passwordmanagerapp.domain.entities.Password

fun PasswordEntity.toDomain(): Password {
    val cipheredPassword = EncryptedPayload(
        this@toDomain.cipheredPassword, passwordIv
    )
    val cipheredNotes = EncryptedPayload(
        this.cipheredNotes, this.notesIv
    )

    return Password(
        id = id,
        cipheredPassword = cipheredPassword,
        appId = appId,
        accountId = accountId,
        dateInfo = DateInfo(
            creationDate = LocalDate.parse(creationDate),
            lastUpdate = LocalDate.parse(lastUpdate)
        ),
        cipheredNotes = cipheredNotes
    )
}