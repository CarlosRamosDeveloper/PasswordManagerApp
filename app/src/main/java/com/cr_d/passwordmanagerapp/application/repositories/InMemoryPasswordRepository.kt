package com.cr_d.passwordmanagerapp.application.repositories

import com.cr_d.passwordmanagerapp.application.interfaces.IPasswordRepository
import com.cr_d.passwordmanagerapp.data.mapper.toDetail
import com.cr_d.passwordmanagerapp.di.AppGraph
import com.cr_d.passwordmanagerapp.domain.value_objects.Password
import com.cr_d.passwordmanagerapp.domain.value_objects.PasswordDetail

class InMemoryPasswordRepository : IPasswordRepository {
    private val passwords = mutableListOf<PasswordDetail>()

    override suspend fun findAll(): List<PasswordDetail> {
        return passwords
    }

    override suspend fun findByApplication(app: String): List<PasswordDetail> {
        return findAll().filter { it.appData.appName == app }
    }

    override suspend fun findByAccount(account: String): List<PasswordDetail> {
        //return findAll().filter { it.appInfo.appAccount == account }
        return findAll().filter { it.appData.appName == account }
    }

    override suspend fun findById(id: Long): PasswordDetail? {
        return findAll().find { it.id == id }
    }

    override suspend fun save(password: Password) {
        // TODO: Fix
        val extraInfo = AppGraph.obtainPasswordDetailInfoUseCase.invoke(password)
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
        val extraInfo = AppGraph.obtainPasswordDetailInfoUseCase.invoke(password)
        if (index != -1) passwords[index] = password.toDetail(extraInfo)
    }

    override suspend fun delete(id: Long) {
        passwords.removeIf { it.id==id }
    }

    override suspend fun massDelete() {
        passwords.clear()
    }
}