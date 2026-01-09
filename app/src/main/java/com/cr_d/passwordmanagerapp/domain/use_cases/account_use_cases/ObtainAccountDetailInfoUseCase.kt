package com.cr_d.passwordmanagerapp.domain.use_cases.account_use_cases

import com.cr_d.passwordmanagerapp.data.dto.AccountDetailInfo
import com.cr_d.passwordmanagerapp.data.repository.interfaces.IPasswordRepository
import com.cr_d.passwordmanagerapp.domain.entities.Account

class ObtainAccountDetailInfoUseCase (
    private val passwordRepository: IPasswordRepository,
) {
    suspend fun invoke(account: Account) : AccountDetailInfo {
        val totalApplications = passwordRepository.findByAccountId(account.id).count()

        val extraInfo = AccountDetailInfo(
            totalApplications = totalApplications
        )

        return extraInfo
    }
}