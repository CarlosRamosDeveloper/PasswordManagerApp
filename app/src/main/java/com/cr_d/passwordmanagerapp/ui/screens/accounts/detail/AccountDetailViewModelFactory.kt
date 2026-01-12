package com.cr_d.passwordmanagerapp.ui.screens.accounts.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.cr_d.passwordmanagerapp.domain.use_cases.account_use_cases.AccountParseToUiUseCase
import com.cr_d.passwordmanagerapp.domain.use_cases.account_use_cases.DeleteAccountUseCase

class AccountDetailViewModelFactory(
    val accountId: Long,
    val accountParseToUiUseCase: AccountParseToUiUseCase,
    val delete: DeleteAccountUseCase
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AccountDetailViewModel(
            accountId,
            accountParseToUiUseCase,
            delete
        ) as T
    }
}