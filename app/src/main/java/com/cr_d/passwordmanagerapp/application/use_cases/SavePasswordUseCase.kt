package com.cr_d.passwordmanagerapp.application.use_cases

import java.time.LocalDate

import com.cr_d.passwordmanagerapp.application.interfaces.IPasswordRepository
import com.cr_d.passwordmanagerapp.data.entities.PasswordEntity
import com.cr_d.passwordmanagerapp.domain.value_objects.ApplicationInfo

class SavePasswordUseCase (
    private val repository: IPasswordRepository,
){
    operator fun invoke(
        password: String,
        appInfo: ApplicationInfo,
        score: Double
    ): PasswordEntity {
        val creationDate = LocalDate.now()
        val newPassword = PasswordEntity(
            id = 0,
            plainPassword = password,
            appName = appInfo.appName,
            appUrl = appInfo.appUrl,
            account = appInfo.appAccount,
            creationDate = creationDate.toString(),
            lastUpdate = creationDate.toString(),
            notes = ""
        )

        repository.save(newPassword)

        return newPassword
    }
}