package com.cr_d.passwordmanagerapp.data.mapper

import com.cr_d.passwordmanagerapp.data.entities.AccountEntity
import com.cr_d.passwordmanagerapp.domain.value_objects.AccountData

fun AccountData.toEntity(): AccountEntity = AccountEntity (
    id = id,
    cipheredAccount = cipheredAccount.encryptedText,
    accountIv = cipheredAccount.iv,
    cipheredNotes = cipheredNotes.encryptedText,
    notesIv = cipheredNotes.iv
)