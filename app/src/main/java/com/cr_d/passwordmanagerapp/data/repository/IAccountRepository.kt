package com.cr_d.passwordmanagerapp.data.repository

import com.cr_d.passwordmanagerapp.domain.value_objects.Account

interface IAccountRepository {
    suspend fun findAll(): List<Account>
    suspend fun findById(id: Long): Account?
    suspend fun save(account: Account)
    suspend fun massSave(accounts: List<Account>)
    suspend fun update(account: Account)
    suspend fun delete(id: Long)
    suspend fun massDelete()
}