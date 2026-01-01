package com.cr_d.passwordmanagerapp.application.interfaces

import com.cr_d.passwordmanagerapp.data.daos.ApplicationDao
import com.cr_d.passwordmanagerapp.data.mapper.toDomain
import com.cr_d.passwordmanagerapp.data.mapper.toEntity
import com.cr_d.passwordmanagerapp.domain.value_objects.ApplicationData

class RoomApplicationRepository (private val dao: ApplicationDao): IApplicationRepository{
    override suspend fun findAll(): List<ApplicationData> {
        return dao.getAll().map { it.toDomain() }
    }

    override suspend fun findById(id: Long): ApplicationData? {
        return dao.getApplicationById(id)?.toDomain()
    }

    override suspend fun save(appData: ApplicationData) {
        dao.insertApplication(appData.toEntity())
    }

    override suspend fun massSave(applications: List<ApplicationData>) {
        applications.forEach { app ->
            save(app)
        }
    }

    override suspend fun update(appData: ApplicationData) {
        dao.updateApplication(appData.toEntity())
    }

    override suspend fun delete(id: Long) {
        dao.deletePassword(id)
    }

    override suspend fun massDelete() {
        dao.deleteAll()
    }
}