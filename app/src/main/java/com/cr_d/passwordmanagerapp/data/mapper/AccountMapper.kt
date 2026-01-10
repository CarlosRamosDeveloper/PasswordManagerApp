package com.cr_d.passwordmanagerapp.data.mapper

import com.cr_d.passwordmanagerapp.data.dto.AccountDetail
import com.cr_d.passwordmanagerapp.data.dto.AccountDetailInfo
import com.cr_d.passwordmanagerapp.data.entities.AccountEntity
import com.cr_d.passwordmanagerapp.domain.entities.Account

fun Account.toEntity(): AccountEntity = AccountEntity (
    id = id,
    cipheredAccount = cipheredAccount.encryptedText,
    accountIv = cipheredAccount.iv,
    cipheredNotes = cipheredNotes.encryptedText,
    notesIv = cipheredNotes.iv
)

fun Account.toDetail(extraInfo: AccountDetailInfo): AccountDetail = AccountDetail(
    id = id,
    cipheredAccount = cipheredAccount,
    cipheredNotes = cipheredNotes,
    passwords = extraInfo.passwords
)