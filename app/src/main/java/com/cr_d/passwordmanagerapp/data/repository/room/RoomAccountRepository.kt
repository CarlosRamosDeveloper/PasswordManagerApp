package com.cr_d.passwordmanagerapp.data.repository.room

import com.cr_d.passwordmanagerapp.data.daos.AccountDao
import com.cr_d.passwordmanagerapp.data.dto.AccountDetail
import com.cr_d.passwordmanagerapp.data.mapper.toDetail
import com.cr_d.passwordmanagerapp.data.mapper.toDomain
import com.cr_d.passwordmanagerapp.data.mapper.toEntity
import com.cr_d.passwordmanagerapp.data.repository.interfaces.IAccountRepository
import com.cr_d.passwordmanagerapp.domain.entities.Account

class RoomAccountRepository (
    private val dao: AccountDao
): IAccountRepository {
    override suspend fun findAll(): List<AccountDetail> {
        return dao.getAll().map { it.toDomain() }.map {
            it.toDetail()
        }
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