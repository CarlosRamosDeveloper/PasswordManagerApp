package com.cr_d.passwordmanagerapp.application.use_cases

import java.time.LocalDate

import com.cr_d.passwordmanagerapp.application.interfaces.IPasswordRepository
import com.cr_d.passwordmanagerapp.domain.entities.PasswordAnalyzer
import com.cr_d.passwordmanagerapp.domain.entities.SecurityScoreCalculator
import com.cr_d.passwordmanagerapp.domain.value_objects.ApplicationInfo
import com.cr_d.passwordmanagerapp.domain.value_objects.DateInfo
import com.cr_d.passwordmanagerapp.domain.value_objects.PasswordData
import com.cr_d.passwordmanagerapp.domain.value_objects.PlainPassword

class UpdatePasswordUseCase (
    private val repository: IPasswordRepository,
){
    suspend operator fun invoke(
        id: Long,
        newPassword: String,
        appInfo: ApplicationInfo,
    ): PasswordData {
        val existing = repository.findById(id)
            ?: throw IllegalArgumentException("Password not found")

        val dateInfo = DateInfo(
            creationDate = existing.dateInfo.creationDate,
            lastUpdate = LocalDate.now()
        )
        val metadataInfo = PasswordAnalyzer.analyze(newPassword)

        val updatedPassword = PasswordData(
            id = existing.id,
            plainPassword = PlainPassword(newPassword),
            appInfo = appInfo,
            dateInfo = dateInfo,
            score = SecurityScoreCalculator().calculate(newPassword),
            metadata = metadataInfo,
            notes = ""
        )

        repository.update(updatedPassword)

        return updatedPassword
    }
}