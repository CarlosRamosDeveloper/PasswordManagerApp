package com.cr_d.passwordmanagerapp.domain.repository

import com.cr_d.passwordmanagerapp.domain.value_objects.Application

interface IApplicationRepository {
    suspend fun findAll(): List<Application>
    suspend fun findById(id: Long): Application?
    suspend fun save(appData: Application)
    suspend fun massSave(applications: List<Application>)
    suspend fun update(appData: Application)
    suspend fun delete(id: Long)
    suspend fun massDelete()
}