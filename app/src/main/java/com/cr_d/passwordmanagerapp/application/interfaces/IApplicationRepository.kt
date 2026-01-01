package com.cr_d.passwordmanagerapp.application.interfaces

import com.cr_d.passwordmanagerapp.domain.value_objects.ApplicationData

interface IApplicationRepository {
    suspend fun findAll(): List<ApplicationData>
    suspend fun findById(id: Long): ApplicationData?
    suspend fun save(appData: ApplicationData)
    suspend fun massSave(applications: List<ApplicationData>)
    suspend fun update(appData: ApplicationData)
    suspend fun delete(id: Long)
    suspend fun massDelete()
}