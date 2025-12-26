package com.cr_d.passwordmanagerapp.application.interfaces

import com.cr_d.passwordmanagerapp.data.PasswordEntity
import com.cr_d.passwordmanagerapp.domain.value_objects.PasswordData

interface IPasswordRepository {
    fun findAll(): List<PasswordData>
    fun findByApplication(app: String): List<PasswordData>
    fun findByAccount(account: String): List<PasswordData>
    fun findById(id: Int): PasswordData?
    fun save(passwordData: PasswordEntity)
    fun update(passwordData: PasswordEntity)
    fun delete(id: Int)
}