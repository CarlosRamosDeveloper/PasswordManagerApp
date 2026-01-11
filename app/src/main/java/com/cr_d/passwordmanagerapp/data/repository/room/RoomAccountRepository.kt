package com.cr_d.passwordmanagerapp.data.repository.room

import com.cr_d.passwordmanagerapp.data.daos.AccountDao
import com.cr_d.passwordmanagerapp.data.mapper.toDomain
import com.cr_d.passwordmanagerapp.data.mapper.toEntity
import com.cr_d.passwordmanagerapp.data.repository.interfaces.IAccountRepository
import com.cr_d.passwordmanagerapp.domain.entities.Account
import com.cr_d.passwordmanagerapp.domain.services.HashService
import com.cr_d.passwordmanagerapp.domain.use_cases.security_use_cases.DecryptStringUseCase

class RoomAccountRepository (
    private val dao: AccountDao,
    private val hash: HashService,
    private val decrypt: DecryptStringUseCase
): IAccountRepository {
    override suspend fun findAll(): List<Account> {
        return dao.getAll().map { it.toDomain() }
        }

    override suspend fun findById(id: Long): Account? {
        return dao.getAccountById(id)?.toDomain()
    }

    override suspend fun findByHash(hash: String): Account? {
        return dao.findByHash(hash)?.toDomain()
    }

    override suspend fun save(account: Account) {
        val accountName = decrypt(account.cipheredAccount)
        val hashData = hash.convertToSha256(accountName)
        dao.insertAccount(account.toEntity(hashData))
    }

    override suspend fun massSave(accounts: List<Account>) {
        accounts.forEach { acc->
            val accountName = decrypt(acc.cipheredAccount)
            val hashData = hash.convertToSha256(accountName)
            dao.insertAccount(acc.toEntity(hashData))
        }
    }

    override suspend fun update(account: Account) {
        val accountName = decrypt(account.cipheredAccount)
        val hashData = hash.convertToSha256(accountName)
        dao.updateAccount(account.toEntity(hashData))
    }

    override suspend fun delete(id: Long) {
        dao.deleteAccount(id)
    }

    override suspend fun massDelete() {
        dao.deleteAll()
    }
}