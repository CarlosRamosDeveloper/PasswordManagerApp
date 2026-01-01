package com.cr_d.passwordmanagerapp.data.mapper

import com.cr_d.passwordmanagerapp.data.crypto.EncryptedPayload
import com.cr_d.passwordmanagerapp.data.entities.AccountEntity
import com.cr_d.passwordmanagerapp.domain.value_objects.AccountData

fun AccountEntity.toDomain(): AccountData {
    val cipheredAccount = EncryptedPayload(
        this@toDomain.cipheredAccount, accountIv
    )
    val cipheredNotes = EncryptedPayload(
        this.cipheredNotes, this.notesIv
    )

    return AccountData(
        id = id,
        cipheredAccount = cipheredAccount,
        cipheredNotes = cipheredNotes
    )
}