package com.cr_d.passwordmanagerapp.ui.screens.accounts.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cr_d.passwordmanagerapp.data.repository.interfaces.IAccountRepository
import com.cr_d.passwordmanagerapp.domain.use_cases.account_use_cases.ObtainAccountDetailInfoUseCase
import com.cr_d.passwordmanagerapp.domain.use_cases.security_use_cases.DecryptStringUseCase

class AccountDetailViewModelFactory(
    val accountId: Long,
    val repository: IAccountRepository,
    val obtainAccountDetailUseCase: ObtainAccountDetailInfoUseCase,
    val decryptStringUseCase: DecryptStringUseCase
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AccountDetailViewModel(
            accountId,
            repository,
            obtainAccountDetailUseCase,
            decryptStringUseCase
        ) as T
    }
}