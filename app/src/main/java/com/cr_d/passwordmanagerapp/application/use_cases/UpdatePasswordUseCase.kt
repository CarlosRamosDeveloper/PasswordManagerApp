package com.cr_d.passwordmanagerapp.application.use_cases

import java.time.LocalDate

import com.cr_d.passwordmanagerapp.application.interfaces.IPasswordRepository
import com.cr_d.passwordmanagerapp.data.PasswordEntity
import com.cr_d.passwordmanagerapp.domain.value_objects.ApplicationInfo

class UpdatePasswordUseCase (
    private val repository: IPasswordRepository,
){
    operator fun invoke(
        id: Int,
        newPassword: String,
        appInfo: ApplicationInfo,
    ): PasswordEntity {
        val existing = repository.findById(id)
            ?: throw IllegalArgumentException("Password not found")

        val creationDate = LocalDate.now()
        val updatedPassword = PasswordEntity(
            id = existing.id,
            plainPassword = newPassword,
            appName = appInfo.appName,
            appUrl = appInfo.appUrl,
            account = appInfo.appAccount,
            creationDate = existing.dateInfo.creationDate,
            lastUpdate = creationDate,
            notes = ""
        )

        repository.update(updatedPassword)

        return updatedPassword
    }
}