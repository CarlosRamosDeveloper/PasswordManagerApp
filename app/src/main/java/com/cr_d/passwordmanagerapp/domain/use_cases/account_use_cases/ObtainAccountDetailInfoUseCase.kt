package com.cr_d.passwordmanagerapp.domain.use_cases.account_use_cases

import com.cr_d.passwordmanagerapp.data.dto.AccountDetailInfo
import com.cr_d.passwordmanagerapp.data.mapper.toDetail
import com.cr_d.passwordmanagerapp.data.mapper.toUiState
import com.cr_d.passwordmanagerapp.data.repository.interfaces.IPasswordRepository
import com.cr_d.passwordmanagerapp.domain.entities.Account
import com.cr_d.passwordmanagerapp.domain.use_cases.password_use_cases.ObtainPasswordDetailInfoUseCase
import com.cr_d.passwordmanagerapp.domain.use_cases.security_use_cases.DecryptStringUseCase

class ObtainAccountDetailInfoUseCase (
    private val passwordRepository: IPasswordRepository,
    private val decrypt : DecryptStringUseCase,
    private val obtainData: ObtainPasswordDetailInfoUseCase,
) {
    suspend fun invoke(account: Account) : AccountDetailInfo {
        val passwords = passwordRepository.findByAccountId(account.id).map {
            it.toDetail(obtainData.invoke(it)).toUiState()
        }

        val extraInfo = AccountDetailInfo(
            decipheredAccount = decrypt(account.cipheredAccount),
            decipheredNotes = decrypt(account.cipheredNotes),
            passwords = passwords
        )

        return extraInfo
    }
}