package com.cr_d.passwordmanagerapp.ui.screens.accounts.detail

import com.cr_d.passwordmanagerapp.data.repository.interfaces.IAccountRepository

class AccountDetailViewModel (
    private val accountId: Long,
    private val repository: IAccountRepository,

) {

    data class UiState (
        val a: String = ""
    )
}