package com.cr_d.passwordmanagerapp.ui.screens.accounts.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AccountListViewModelFactory(

) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AccountListViewModel(

        ) as T
    }
}