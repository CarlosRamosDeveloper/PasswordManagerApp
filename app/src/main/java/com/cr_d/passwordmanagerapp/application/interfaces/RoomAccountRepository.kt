package com.cr_d.passwordmanagerapp.application.interfaces

import com.cr_d.passwordmanagerapp.data.daos.AccountDao
import com.cr_d.passwordmanagerapp.data.mapper.toDomain
import com.cr_d.passwordmanagerapp.data.mapper.toEntity
import com.cr_d.passwordmanagerapp.domain.value_objects.AccountData

class RoomAccountRepository (private val dao: AccountDao): IAccountRepository {
    override suspend fun findAll(): List<AccountData> {
        return dao.getAll().map { it.toDomain() }
    }

    override suspend fun findById(id: Long): AccountData? {
        return dao.getAccountById(id)?.toDomain()
    }

    override suspend fun save(accountData: AccountData) {
        dao.insertAccount(accountData.toEntity())
    }

    override suspend fun massSave(accounts: List<AccountData>) {
        accounts.forEach { acc->
            dao.insertAccount(acc.toEntity())
        }
    }

    override suspend fun update(accountData: AccountData) {
        dao.updateAccount(accountData.toEntity())
    }

    override suspend fun delete(id: Long) {
        dao.deleteAccount(id)
    }

    override suspend fun massDelete() {
        dao.deleteAll()
    }
}