package com.cr_d.passwordmanagerapp.data.repository.room

import com.cr_d.passwordmanagerapp.data.daos.PasswordDao
import com.cr_d.passwordmanagerapp.data.mapper.toDomain
import com.cr_d.passwordmanagerapp.data.mapper.toEntity
import com.cr_d.passwordmanagerapp.data.repository.interfaces.IPasswordRepository
import com.cr_d.passwordmanagerapp.domain.entities.Password

class RoomPasswordRepository (
    private val dao: PasswordDao,
): IPasswordRepository {
    //TODO: Check all methods
    //TODO: Cambiar todos los métodos a password
    //TODO: Crear un UC para parsear Password a ToDomain
    override suspend fun findAll(): List<Password> {
        return dao.getAll().map { it.toDomain() }
    }

//    override suspend fun findByApplication(app: String): List<Password> {
//        return findAll().filter { it.appData.appName == app }
//    }

//    override suspend fun findByAccount(account: String): List<PasswordDetail> {
//        return findAll().filter { it.accountData.account == account }
//    }

    override suspend fun findByAccountId(id: Long): List<Password> {
        return dao.findByAccountId(id).map { it.toDomain() }
    }

    override suspend fun findById(id: Long): Password? {
        return dao.getPasswordById(id)?.toDomain()
    }

    override suspend fun save(password: Password) {
        val existing = dao.findByAppIdAndAccountId(password.appId, password.accountId)

        if (existing == null) {
            dao.insertPassword(password.toEntity())
        }
    }

    override suspend fun massSave(passwords: List<Password>) {
        passwords.forEach { pwd ->
            save(pwd)
        }
    }

    override suspend fun update(password: Password) {
        dao.updatePassword(password.toEntity())
    }

    override suspend fun delete(id: Long) {
        dao.deletePassword(id)
    }

    override suspend fun massDelete() {
        dao.deleteAll()
    }
}