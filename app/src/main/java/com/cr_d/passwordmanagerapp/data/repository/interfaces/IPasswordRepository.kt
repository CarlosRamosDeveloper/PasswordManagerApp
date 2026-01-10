package com.cr_d.passwordmanagerapp.data.repository.interfaces

import com.cr_d.passwordmanagerapp.domain.entities.Password
import com.cr_d.passwordmanagerapp.data.dto.PasswordDetail
import com.cr_d.passwordmanagerapp.data.mapper.toDomain

interface IPasswordRepository {
    suspend fun findAll(): List<Password>
    //suspend fun findByApplication(app: String): List<Password>
    //suspend fun findByAccount(account: String): List<PasswordDetail>
    suspend fun findByAccountId(id: Long): List<Password>
    suspend fun findById(id: Long): Password?
    suspend fun save(password: Password)
    suspend fun massSave(passwords: List<Password>)
    suspend fun update(password: Password)
    suspend fun delete(id: Long)
    suspend fun massDelete()
}
