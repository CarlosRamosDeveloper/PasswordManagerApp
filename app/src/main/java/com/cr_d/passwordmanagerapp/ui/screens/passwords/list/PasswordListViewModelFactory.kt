package com.cr_d.passwordmanagerapp.ui.screens.passwords.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.cr_d.passwordmanagerapp.domain.use_cases.GetAllPasswordDetailUseCase

class PasswordListViewModelFactory (
    private val getAllPasswordsUseCase: GetAllPasswordDetailUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PasswordListViewModel(
            getAllPasswordsUseCase
        ) as T
    }
}