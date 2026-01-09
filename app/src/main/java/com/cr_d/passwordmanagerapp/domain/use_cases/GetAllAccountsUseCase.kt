package com.cr_d.passwordmanagerapp.domain.use_cases

import com.cr_d.passwordmanagerapp.data.dto.AccountDetail
import com.cr_d.passwordmanagerapp.data.repository.interfaces.IAccountRepository

class GetAllAccountsUseCase(
    private val repository: IAccountRepository
) {
    suspend operator fun invoke(): List<AccountDetail>{
        return repository.findAll()
    }
}