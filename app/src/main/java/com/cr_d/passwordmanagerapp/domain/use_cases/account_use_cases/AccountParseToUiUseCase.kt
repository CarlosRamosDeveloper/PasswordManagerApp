package com.cr_d.passwordmanagerapp.domain.use_cases.account_use_cases

import com.cr_d.passwordmanagerapp.data.mapper.toDetail
import com.cr_d.passwordmanagerapp.data.mapper.toUiState
import com.cr_d.passwordmanagerapp.data.repository.interfaces.IAccountRepository
import com.cr_d.passwordmanagerapp.ui.model.AccountUiState

class AccountParseToUiUseCase (
    private val repository : IAccountRepository,
    private val obtainData: ObtainAccountDetailInfoUseCase,
) {
    suspend operator fun invoke(accountId: Long): AccountUiState? {
        val account = repository.findById(accountId) ?: return null
        val extraData = obtainData.invoke(account)

        return account.toDetail(extraData).toUiState(extraData)
    }
}