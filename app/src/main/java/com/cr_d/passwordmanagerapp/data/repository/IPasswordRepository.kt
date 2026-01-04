package com.cr_d.passwordmanagerapp.data.repository

import com.cr_d.passwordmanagerapp.domain.entities.Password
import com.cr_d.passwordmanagerapp.data.dto.PasswordDetail

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