package com.cr_d.passwordmanagerapp.application.interfaces

import com.cr_d.passwordmanagerapp.data.daos.PasswordDao
import com.cr_d.passwordmanagerapp.data.mapper.toDomainCalculated
import com.cr_d.passwordmanagerapp.data.mapper.toEntity
import com.cr_d.passwordmanagerapp.domain.value_objects.PasswordData

class RoomPasswordRepository (private val dao: PasswordDao): IPasswordRepository {
    override suspend fun findAll(): List<PasswordData> {
        return dao.getAll().map { it.toDomainCalculated() }
    }

    override suspend fun findByApplication(app: String): List<PasswordData> {
        return findAll().filter { it.appInfo.appName == app }
    }

    override suspend fun findByAccount(account: String): List<PasswordData> {
        return findAll().filter { it.appInfo.appAccount == account }
    }

    override suspend fun findById(id: Long): PasswordData? {
        return dao.getUserById(id.toInt())?.toDomainCalculated()
    }

    override suspend fun save(passwordData: PasswordData) {
        dao.insertPassword(passwordData.toEntity())
    }

    override suspend fun massSave(passwords: List<PasswordData>) {
        passwords.forEach { pwd ->
            dao.insertPassword(pwd.toEntity())
        }
    }

    override suspend fun update(passwordData: PasswordData) {
        dao.updatePassword(passwordData.toEntity())
    }

    override suspend fun delete(id: Long) {
        dao.deletePassword(id)
    }

    override suspend fun massDelete() {
        TODO("Not yet implemented")
    }
}