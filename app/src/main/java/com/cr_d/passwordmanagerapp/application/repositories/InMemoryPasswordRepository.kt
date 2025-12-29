package com.cr_d.passwordmanagerapp.application.repositories

import com.cr_d.passwordmanagerapp.application.interfaces.IPasswordRepository
import com.cr_d.passwordmanagerapp.domain.value_objects.PasswordData

class InMemoryPasswordRepository : IPasswordRepository {

    private val passwords = mutableListOf<PasswordData>()

    override suspend fun findAll(): List<PasswordData> {
        return passwords
    }

    override suspend fun findByApplication(app: String): List<PasswordData> {
        return findAll().filter { it.appInfo.appName == app }
    }

    override suspend fun findByAccount(account: String): List<PasswordData> {
        return findAll().filter { it.appInfo.appAccount == account }
    }

    override suspend fun findById(id: Long): PasswordData? {
        return findAll().find { it.id == id }
    }

    override suspend fun save(passwordData: PasswordData) {
        passwords.add(passwordData)
    }

    override suspend fun massSave(passwords: List<PasswordData>) {
        passwords.forEach { pwd ->
            save(pwd)
        }
    }

    override suspend fun update(passwordData: PasswordData) {
        val index = passwords.indexOfFirst { it.id == passwordData.id }
        if (index != -1) passwords[index] = passwordData
    }

    override suspend fun delete(id: Long) {
        passwords.removeIf { it.id==id }
    }

    override suspend fun massDelete() {
        passwords.clear()
    }
}