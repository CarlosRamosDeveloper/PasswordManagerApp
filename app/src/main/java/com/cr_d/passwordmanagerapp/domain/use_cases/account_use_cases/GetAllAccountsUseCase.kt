package com.cr_d.passwordmanagerapp.domain.use_cases.account_use_cases

import com.cr_d.passwordmanagerapp.data.dto.AccountDetail
import com.cr_d.passwordmanagerapp.data.mapper.toDetail
import com.cr_d.passwordmanagerapp.data.repository.interfaces.IAccountRepository
import com.cr_d.passwordmanagerapp.domain.use_cases.account_use_cases.ObtainAccountDetailInfoUseCase

class GetAllAccountsUseCase(
    private val repository: IAccountRepository,
    private val getInfo: ObtainAccountDetailInfoUseCase
) {
    suspend operator fun invoke(): List<AccountDetail>{

        return repository.findAll().map {
            val extra = getInfo.invoke(it)
            it.toDetail(extra)
        }
    }
}