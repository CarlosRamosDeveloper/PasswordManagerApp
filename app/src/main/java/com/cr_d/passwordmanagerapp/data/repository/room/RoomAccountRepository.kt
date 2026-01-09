package com.cr_d.passwordmanagerapp.data.repository.room

import com.cr_d.passwordmanagerapp.data.daos.AccountDao
import com.cr_d.passwordmanagerapp.data.mapper.toDomain
import com.cr_d.passwordmanagerapp.data.mapper.toEntity
import com.cr_d.passwordmanagerapp.data.repository.interfaces.IAccountRepository
import com.cr_d.passwordmanagerapp.domain.entities.Account
import com.cr_d.passwordmanagerapp.domain.use_cases.account_use_cases.ObtainAccountDetailInfoUseCase

class RoomAccountRepository (
    private val dao: AccountDao,
): IAccountRepository {
    override suspend fun findAll(): List<Account> {
        return dao.getAll().map { it.toDomain() }
        }

    override suspend fun findById(id: Long): Account? {
        return dao.getAccountById(id)?.toDomain()
    }

    override suspend fun save(account: Account) {
        dao.insertAccount(account.toEntity())
    }

    override suspend fun massSave(accounts: List<Account>) {
        accounts.forEach { acc->
            dao.insertAccount(acc.toEntity())
        }
    }

    override suspend fun update(account: Account) {
        dao.updateAccount(account.toEntity())
    }

    override suspend fun delete(id: Long) {
        dao.deleteAccount(id)
    }

    override suspend fun massDelete() {
        dao.deleteAll()
    }
}