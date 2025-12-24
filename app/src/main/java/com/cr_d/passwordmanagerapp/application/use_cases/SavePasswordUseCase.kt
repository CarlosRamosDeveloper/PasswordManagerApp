package com.cr_d.passwordmanagerapp.application.use_cases

import com.cr_d.passwordmanagerapp.application.interfaces.IPasswordRepository
import com.cr_d.passwordmanagerapp.domain.entities.PasswordAnalyzer
import com.cr_d.passwordmanagerapp.domain.value_objects.ApplicationInfo
import com.cr_d.passwordmanagerapp.domain.value_objects.PasswordData
import com.cr_d.passwordmanagerapp.domain.value_objects.PasswordMetadata
import com.cr_d.passwordmanagerapp.domain.value_objects.PlainPassword
import java.time.LocalDate

class SavePasswordUseCase (
    private val repository: IPasswordRepository,
){
    operator fun invoke(password: String, appInfo: ApplicationInfo, score: Double ): PasswordData {
        val analyzedData = PasswordAnalyzer.analyze(password)
        val newPassword = PlainPassword(password)
        val creationDate = LocalDate.now()
        val metadata = PasswordMetadata(
            hasLowerCase = analyzedData.hasLowerCase,
            hasUpperCase = analyzedData.hasUpperCase,
            hasNumbers = analyzedData.hasNumbers,
            hasSpecials = analyzedData.hasSpecials,
            creationDate = creationDate,
            lastUpdate = creationDate
        )
        val passwordData = PasswordData(
            id= 0,
            appInfo = appInfo,
            plainPassword = newPassword,
            metadata = metadata,
            securityScore = score
        )

        repository.save(passwordData)

        return passwordData
    }
}