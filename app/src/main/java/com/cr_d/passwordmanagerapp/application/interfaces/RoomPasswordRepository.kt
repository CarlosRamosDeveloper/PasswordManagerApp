package com.cr_d.passwordmanagerapp.application.interfaces

import com.cr_d.passwordmanagerapp.application.use_cases.ObtainPasswordDetailInfoUseCase
import com.cr_d.passwordmanagerapp.data.daos.PasswordDao
import com.cr_d.passwordmanagerapp.data.mapper.toDetail
import com.cr_d.passwordmanagerapp.data.mapper.toDomain
import com.cr_d.passwordmanagerapp.data.mapper.toEntity
import com.cr_d.passwordmanagerapp.domain.value_objects.Password
import com.cr_d.passwordmanagerapp.domain.value_objects.PasswordDetail

class RoomPasswordRepository (
    private val dao: PasswordDao,
    private val obtainDetail: ObtainPasswordDetailInfoUseCase
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

    override suspend fun findById(id: Long): PasswordDetail? {
        val password = dao.getPasswordById(id)?.toDomain()
        val extraData = obtainDetail.invoke(password!!)

        return password.toDetail(extraData)
        //return dao.getPasswordById(id)?.toDomain()?.toDetail()
    }

    override suspend fun save(password: Password) {
        dao.insertPassword(password.toEntity())
    }

    override suspend fun massSave(passwords: List<Password>) {
        passwords.forEach { pwd ->
            dao.insertPassword(pwd.toEntity())
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