package com.cr_d.passwordmanagerapp.domain.use_cases.account_use_cases

import com.cr_d.passwordmanagerapp.data.repository.interfaces.IAccountRepository

class DeleteAccountUseCase(
    private val repository: IAccountRepository
) {
    suspend operator fun invoke(accountId: Long) {
        repository.delete(accountId)
    }
}