package com.cr_d.passwordmanagerapp.data.repository.interfaces

import com.cr_d.passwordmanagerapp.data.dto.AccountDetail
import com.cr_d.passwordmanagerapp.domain.entities.Account

interface IAccountRepository {
    suspend fun findAll(): List<AccountDetail>
    suspend fun findById(id: Long): Account?
    suspend fun save(account: Account)
    suspend fun massSave(accounts: List<Account>)
    suspend fun update(account: Account)
    suspend fun delete(id: Long)
    suspend fun massDelete()
}