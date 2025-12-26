package com.cr_d.passwordmanagerapp.application.use_cases

import java.time.LocalDate

import com.cr_d.passwordmanagerapp.application.interfaces.IPasswordRepository
import com.cr_d.passwordmanagerapp.domain.entities.PasswordAnalyzer
import com.cr_d.passwordmanagerapp.domain.value_objects.ApplicationInfo
import com.cr_d.passwordmanagerapp.domain.value_objects.PasswordData
import com.cr_d.passwordmanagerapp.domain.value_objects.PlainPassword

class UpdatePasswordUseCase (
    private val repository: IPasswordRepository,
    private val securityScoreUseCase: CalculateSecurityScoreUseCase
){
    operator fun invoke(
        id: Int,
        newPassword: String,
        appInfo: ApplicationInfo,
    ): PasswordData {
        val existing = repository.findById(id)
            ?: throw IllegalArgumentException("Password not found")

        val analyzedData = PasswordAnalyzer.analyze(newPassword)
        val updateScore = securityScoreUseCase(newPassword)
        val updatedMetadata = existing.metadata.copy(
            hasLowerCase = analyzedData.hasLowerCase,
            hasUpperCase = analyzedData.hasUpperCase,
            hasNumbers = analyzedData.hasNumbers,
            hasSpecials = analyzedData.hasSpecials,
        )
        val dateInfo = existing.dateInfo.copy(
            lastUpdate = LocalDate.now()
        )
        val updated = existing.copy(
            plainPassword = PlainPassword(newPassword),
            appInfo = appInfo,
            metadata = updatedMetadata,
            dateInfo = dateInfo,
            score = updateScore
        )

        repository.update(updated)

        return updated
    }
}