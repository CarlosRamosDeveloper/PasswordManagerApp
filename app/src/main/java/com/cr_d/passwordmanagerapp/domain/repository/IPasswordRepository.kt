package com.cr_d.passwordmanagerapp.domain.repository

import com.cr_d.passwordmanagerapp.domain.value_objects.Password
import com.cr_d.passwordmanagerapp.domain.value_objects.PasswordDetail

interface IPasswordRepository {
    suspend fun findAll(): List<PasswordDetail>
    suspend fun findByApplication(app: String): List<PasswordDetail>
    suspend fun findByAccount(account: String): List<PasswordDetail>
    suspend fun findById(id: Long): PasswordDetail?
    suspend fun save(password: Password)
    suspend fun massSave(passwords: List<Password>)
    suspend fun update(password: Password)
    suspend fun delete(id: Long)
    suspend fun massDelete()
}