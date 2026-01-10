package com.cr_d.passwordmanagerapp.ui.screens.accounts.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.cr_d.passwordmanagerapp.domain.use_cases.account_use_cases.SaveAccountUseCase

class CreateAccountViewModelFactory(
    private val save: SaveAccountUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CreateAccountViewModel(
            save
        ) as T
    }
}
