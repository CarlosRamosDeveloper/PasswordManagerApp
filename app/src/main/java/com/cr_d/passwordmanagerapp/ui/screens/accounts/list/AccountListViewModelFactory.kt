package com.cr_d.passwordmanagerapp.ui.screens.accounts.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cr_d.passwordmanagerapp.domain.use_cases.security_use_cases.DecryptStringUseCase
import com.cr_d.passwordmanagerapp.domain.use_cases.account_use_cases.GetAllAccountsUseCase

class AccountListViewModelFactory(
    private val getAllAccountsUseCase: GetAllAccountsUseCase,
    private val decrypt: DecryptStringUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AccountListViewModel(
            getAllAccountsUseCase,
            decrypt
        ) as T
    }
}