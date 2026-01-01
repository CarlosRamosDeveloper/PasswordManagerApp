package com.cr_d.passwordmanagerapp.ui.screens.main_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.cr_d.passwordmanagerapp.application.interfaces.IAccountRepository
import com.cr_d.passwordmanagerapp.ui.screens.main_screen.viewmodel_components.MainDialogManagerComponent
import com.cr_d.passwordmanagerapp.ui.screens.main_screen.viewmodel_components.MainPasswordManagerComponent

class MainScreenViewModelFactory(
    val accountRepository: IAccountRepository,
    val dialogManager: MainDialogManagerComponent,
    val passwordManager: MainPasswordManagerComponent
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainScreenViewModel(
            accountRepository,
            dialogManager,
            passwordManager
            ) as T
    }
}
