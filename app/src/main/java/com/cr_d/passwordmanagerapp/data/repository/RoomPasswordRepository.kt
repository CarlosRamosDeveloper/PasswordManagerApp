package com.cr_d.passwordmanagerapp.data.repository

import com.cr_d.passwordmanagerapp.data.daos.PasswordDao
import com.cr_d.passwordmanagerapp.data.dto.PasswordDetail
import com.cr_d.passwordmanagerapp.data.mapper.toDetail
import com.cr_d.passwordmanagerapp.data.mapper.toDomain
import com.cr_d.passwordmanagerapp.data.mapper.toEntity
import com.cr_d.passwordmanagerapp.domain.entities.Password
import com.cr_d.passwordmanagerapp.domain.use_cases.ObtainPasswordDetailInfoUseCase

class RoomPasswordRepository (
    private val dao: PasswordDao,
    private val obtainDetail: ObtainPasswordDetailInfoUseCase,
): IPasswordRepository {
    //TODO: Check all methods
    override suspend fun findAll(): List<PasswordDetail> {
        val list = dao.getAll().map { it.toDomain() }.map {
            val extraData = obtainDetail.invoke(it)
                it.toDetail(extraData)
        }

        return list
    }

    override suspend fun findByApplication(app: String): List<PasswordDetail> {
        return findAll().filter { it.appData.appName == app }
    }

    override suspend fun findByAccount(account: String): List<PasswordDetail> {
        return findAll().filter { it.accountData.account == account }
    }

    override suspend fun findById(id: Long): PasswordDetail {
        val password = dao.getPasswordById(id)?.toDomain()
        val extraData = obtainDetail.invoke(password!!)

        return password.toDetail(extraData)
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