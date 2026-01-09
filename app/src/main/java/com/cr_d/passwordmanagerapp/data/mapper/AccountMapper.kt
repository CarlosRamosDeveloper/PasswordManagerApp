package com.cr_d.passwordmanagerapp.data.mapper

import com.cr_d.passwordmanagerapp.data.dto.AccountDetail
import com.cr_d.passwordmanagerapp.data.entities.AccountEntity
import com.cr_d.passwordmanagerapp.domain.entities.Account

fun Account.toEntity(): AccountEntity = AccountEntity (
    id = id,
    cipheredAccount = cipheredAccount.encryptedText,
    accountIv = cipheredAccount.iv,
    cipheredNotes = cipheredNotes.encryptedText,
    notesIv = cipheredNotes.iv
)

fun Account.toDomain(): AccountDetail = AccountDetail(
    id = id,
    cipheredAccount = cipheredAccount,
    cipheredNotes = cipheredNotes
)