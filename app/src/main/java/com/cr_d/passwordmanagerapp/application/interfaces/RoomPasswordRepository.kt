package com.cr_d.passwordmanagerapp.application.interfaces

import com.cr_d.passwordmanagerapp.data.daos.PasswordDao
import com.cr_d.passwordmanagerapp.data.mapper.toDomainCalculated
import com.cr_d.passwordmanagerapp.domain.value_objects.PasswordData

class RoomPasswordRepository (private val dao: PasswordDao): IPasswordRepository {
    override suspend fun findAll(): List<PasswordData> {
        return dao.getAll().map { it.toDomainCalculated() }
    }

    override fun findByApplication(app: String): List<PasswordData> {
        TODO("Not yet implemented")
    }

    override fun findByAccount(account: String): List<PasswordData> {
        TODO("Not yet implemented")
    }

    override fun findById(id: Long): PasswordData? {
        TODO("Not yet implemented")
    }

    override fun save(passwordData: PasswordData) {
        TODO("Not yet implemented")
    }

    override fun update(passwordData: PasswordData) {
        TODO("Not yet implemented")
    }

    override fun delete(id: Long) {
        TODO("Not yet implemented")
    }
}