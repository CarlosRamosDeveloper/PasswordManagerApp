package com.cr_d.passwordmanagerapp.application.use_cases

import com.cr_d.passwordmanagerapp.application.interfaces.IPasswordRepository
import com.cr_d.passwordmanagerapp.domain.entities.PasswordAnalyzer
import com.cr_d.passwordmanagerapp.domain.entities.SecurityScoreCalculator
import com.cr_d.passwordmanagerapp.domain.value_objects.ApplicationInfo
import com.cr_d.passwordmanagerapp.domain.value_objects.PasswordData
import com.cr_d.passwordmanagerapp.domain.value_objects.PasswordMetadata
import com.cr_d.passwordmanagerapp.domain.value_objects.PlainPassword

class SavePasswordUseCase (
    private val repository: IPasswordRepository,
    private val scoreCalculator: SecurityScoreCalculator
){
    operator fun invoke(password: String ): PasswordData {
        val analyzedData = PasswordAnalyzer.analyze(password)
        val score = scoreCalculator.calculate(password)
        val newPassword = PlainPassword(password)
        val newAppInfo = ApplicationInfo(
            "AppName", "www.random.com", "random@randommail.com"
        )
        val metadata = PasswordMetadata(
            hasLowerCase = analyzedData.hasLowerCase,
            hasUpperCase = analyzedData.hasUpperCase,
            hasNumbers = analyzedData.hasNumbers,
            hasSpecials = analyzedData.hasSpecials,
            creationDate = "now",
            lastUpdate = "now"
        )
        val passwordData = PasswordData(
            id= 0,
            appInfo = newAppInfo,
            plainPassword = newPassword,
            metadata = metadata,
            securityScore = score
        )

        repository.save(passwordData)

        return passwordData
    }
}