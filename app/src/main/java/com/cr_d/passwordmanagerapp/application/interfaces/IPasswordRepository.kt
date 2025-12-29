package com.cr_d.passwordmanagerapp.application.interfaces

import com.cr_d.passwordmanagerapp.domain.value_objects.PasswordData

interface IPasswordRepository {
    suspend fun findAll(): List<PasswordData>
    suspend fun findByApplication(app: String): List<PasswordData>
    suspend fun findByAccount(account: String): List<PasswordData>
    suspend fun findById(id: Long): PasswordData?
    suspend fun save(passwordData: PasswordData)
    suspend fun update(passwordData: PasswordData)
    suspend fun delete(id: Long)
}