package com.cr_d.passwordmanagerapp.application.interfaces

import com.cr_d.passwordmanagerapp.domain.value_objects.AccountData

interface IAccountRepository {
    suspend fun findAll(): List<AccountData>
    suspend fun findById(id: Long): AccountData?
    suspend fun save(accountData: AccountData)
    suspend fun massSave(accounts: List<AccountData>)
    suspend fun update(accountData: AccountData)
    suspend fun delete(id: Long)
    suspend fun massDelete()
}