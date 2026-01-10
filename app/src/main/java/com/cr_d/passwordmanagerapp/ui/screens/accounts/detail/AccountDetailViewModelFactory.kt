package com.cr_d.passwordmanagerapp.ui.screens.accounts.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cr_d.passwordmanagerapp.data.repository.interfaces.IAccountRepository

class AccountDetailViewModelFactory(
    val accountId: Long,
    val repository: IAccountRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AccountDetailViewModel(
            accountId,
            repository
        ) as T
    }
}