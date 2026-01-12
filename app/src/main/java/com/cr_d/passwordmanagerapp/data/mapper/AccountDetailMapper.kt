package com.cr_d.passwordmanagerapp.data.mapper

import com.cr_d.passwordmanagerapp.data.dto.AccountDetail
import com.cr_d.passwordmanagerapp.data.dto.AccountDetailInfo
import com.cr_d.passwordmanagerapp.domain.entities.Account
import com.cr_d.passwordmanagerapp.ui.model.AccountUiState

fun AccountDetail.toDomain(): Account = Account(
    id = id,
    cipheredAccount = cipheredAccount,
    cipheredNotes = cipheredNotes,
)

fun AccountDetail.toUiState(extraInfo: AccountDetailInfo): AccountUiState {
    return AccountUiState(
        id = id,
        account = extraInfo.decipheredAccount,
        notes = extraInfo.decipheredNotes,
        passwords = extraInfo.passwords
    )
}

