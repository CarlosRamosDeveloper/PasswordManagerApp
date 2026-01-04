package com.cr_d.passwordmanagerapp.data.repository

import com.cr_d.passwordmanagerapp.data.daos.ApplicationDao
import com.cr_d.passwordmanagerapp.data.mapper.toDomain
import com.cr_d.passwordmanagerapp.data.mapper.toEntity
import com.cr_d.passwordmanagerapp.domain.entities.Application

class RoomApplicationRepository (private val dao: ApplicationDao): IApplicationRepository {
    override suspend fun findAll(): List<Application> {
        return dao.getAll().map { it.toDomain() }
    }

    override suspend fun findById(id: Long): Application? {
        return dao.getApplicationById(id)?.toDomain()
    }

    override suspend fun save(appData: Application) {
        dao.insertApplication(appData.toEntity())
    }

    override suspend fun massSave(applications: List<Application>) {
        applications.forEach { app ->
            save(app)
        }
    }

    override suspend fun update(appData: Application) {
        dao.updateApplication(appData.toEntity())
    }

    override suspend fun delete(id: Long) {
        dao.deletePassword(id)
    }

    override suspend fun massDelete() {
        dao.deleteAll()
    }
}