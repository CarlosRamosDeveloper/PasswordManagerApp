package com.cr_d.passwordmanagerapp.ui.screens.main_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.cr_d.passwordmanagerapp.application.interfaces.IAccountRepository
import com.cr_d.passwordmanagerapp.application.interfaces.IPasswordRepository
import com.cr_d.passwordmanagerapp.ui.screens.main_screen.viewmodel_components.MainDialogManagerComponent

class MainScreenViewModelFactory(
    val passwordRepository: IPasswordRepository,
    val accountRepository: IAccountRepository,
    val dialogManager: MainDialogManagerComponent,
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainScreenViewModel(
            passwordRepository,
            accountRepository,
            dialogManager
            ) as T
    }
}
