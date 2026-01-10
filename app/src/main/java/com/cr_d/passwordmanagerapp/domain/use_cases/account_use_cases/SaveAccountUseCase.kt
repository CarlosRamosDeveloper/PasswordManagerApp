package com.cr_d.passwordmanagerapp.domain.use_cases.account_use_cases

import com.cr_d.passwordmanagerapp.data.dto.AccountCreationData
import com.cr_d.passwordmanagerapp.data.repository.interfaces.IAccountRepository
import com.cr_d.passwordmanagerapp.domain.entities.Account
import com.cr_d.passwordmanagerapp.domain.use_cases.security_use_cases.EncryptStringUseCase

class SaveAccountUseCase (
    private val repository: IAccountRepository,
    private val encrypt: EncryptStringUseCase
) {
    suspend operator fun invoke(data: AccountCreationData){
        val encryptedAccount = encrypt(data.account)
        val encryptedNotes = encrypt(data.notes)
        val account = Account(
            id = 0,
            cipheredAccount = encryptedAccount,
            cipheredNotes = encryptedNotes
        )

        repository.save(account)
    }
}
