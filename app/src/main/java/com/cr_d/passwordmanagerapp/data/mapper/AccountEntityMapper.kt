package com.cr_d.passwordmanagerapp.data.mapper

import com.cr_d.passwordmanagerapp.domain.value_objects.EncryptedPayload
import com.cr_d.passwordmanagerapp.data.entities.AccountEntity
import com.cr_d.passwordmanagerapp.domain.entities.Account

fun AccountEntity.toDomain(): Account {
    val cipheredAccount = EncryptedPayload(
        this@toDomain.cipheredAccount, accountIv
    )
    val cipheredNotes = EncryptedPayload(
        this.cipheredNotes, this.notesIv
    )

    return Account(
        id = id,
        cipheredAccount = cipheredAccount,
        cipheredNotes = cipheredNotes
    )
}