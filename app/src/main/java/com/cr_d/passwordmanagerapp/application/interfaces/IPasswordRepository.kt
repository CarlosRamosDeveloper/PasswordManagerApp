package com.cr_d.passwordmanagerapp.application.interfaces

import com.cr_d.passwordmanagerapp.data.entities.PasswordEntity
import com.cr_d.passwordmanagerapp.domain.value_objects.PasswordData

interface IPasswordRepository {
    suspend fun findAll(): List<PasswordData>
    fun findByApplication(app: String): List<PasswordData>
    fun findByAccount(account: String): List<PasswordData>
    fun findById(id: Long): PasswordData?
    fun save(passwordData: PasswordData)
    fun update(passwordData: PasswordData)
    fun delete(id: Long)
}