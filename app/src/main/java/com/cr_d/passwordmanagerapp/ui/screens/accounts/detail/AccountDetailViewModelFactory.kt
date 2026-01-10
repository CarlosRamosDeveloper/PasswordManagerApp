package com.cr_d.passwordmanagerapp.ui.screens.accounts.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.cr_d.passwordmanagerapp.domain.use_cases.account_use_cases.AccountParseToUiUseCase

class AccountDetailViewModelFactory(
    val accountId: Long,
    val accountParseToUiUseCase: AccountParseToUiUseCase
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AccountDetailViewModel(
            accountId,
            accountParseToUiUseCase
        ) as T
    }
}