package com.cr_d.passwordmanagerapp.ui.screens.main_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.cr_d.passwordmanagerapp.application.interfaces.IPasswordRepository

class MainScreenViewModelFactory(
    val repository: IPasswordRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainScreenViewModel(repository) as T
    }
}
