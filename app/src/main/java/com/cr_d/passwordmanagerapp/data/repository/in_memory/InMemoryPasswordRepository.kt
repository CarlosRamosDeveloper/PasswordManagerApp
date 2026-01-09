package com.cr_d.passwordmanagerapp.data.repository.in_memory

import com.cr_d.passwordmanagerapp.data.dto.PasswordDetail
import com.cr_d.passwordmanagerapp.data.mapper.toDetail
import com.cr_d.passwordmanagerapp.data.repository.interfaces.IPasswordRepository
import com.cr_d.passwordmanagerapp.domain.entities.Password
import com.cr_d.passwordmanagerapp.domain.use_cases.ObtainPasswordDetailInfoUseCase

class InMemoryPasswordRepository (
    private val obtainInfo: ObtainPasswordDetailInfoUseCase
): IPasswordRepository {
    private val passwords = mutableListOf<PasswordDetail>()

    override suspend fun findAll(): List<PasswordDetail> {
        return passwords
    }

    override suspend fun findByApplication(app: String): List<PasswordDetail> {
        return findAll().filter { it.appData.appName == app }
    }

    override suspend fun findByAccount(account: String): List<PasswordDetail> {
        return findAll().filter { it.appData.appName == account }
    }

    override suspend fun findByAccountId(id: Long): List<PasswordDetail> {
        //TODO: implementar
        val list = emptyList<PasswordDetail>()

        return list
    }

    override suspend fun findById(id: Long): PasswordDetail? {
        return findAll().find { it.id == id }
    }

    override suspend fun save(password: Password) {
        // TODO: Fix
        val extraInfo = obtainInfo.invoke(password)
        passwords.add(password.toDetail(extraInfo))
    }

    override suspend fun massSave(passwords: List<Password>) {
        passwords.forEach { pwd ->
            save(pwd)
        }
    }

    override suspend fun update(password: Password) {
        val index = passwords.indexOfFirst { it.id == password.id }
                // TODO: Fix
        val extraInfo = obtainInfo.invoke(password)
        if (index != -1) passwords[index] = password.toDetail(extraInfo)
    }

    override suspend fun delete(id: Long) {
        passwords.removeIf { it.id==id }
    }

    override suspend fun massDelete() {
        passwords.clear()
    }
}
