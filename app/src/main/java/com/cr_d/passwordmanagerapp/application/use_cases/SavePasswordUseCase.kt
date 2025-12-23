package com.cr_d.passwordmanagerapp.application.use_cases

import com.cr_d.passwordmanagerapp.application.interfaces.IPasswordRepository
import com.cr_d.passwordmanagerapp.domain.entities.PasswordAnalyzer
import com.cr_d.passwordmanagerapp.domain.entities.SecurityScoreCalculator
import com.cr_d.passwordmanagerapp.domain.value_objects.PasswordData
import com.cr_d.passwordmanagerapp.domain.value_objects.PlainPassword

class SavePasswordUseCase (
    private val repository: IPasswordRepository,
    private val scoreCalculator: SecurityScoreCalculator
){
    operator fun invoke(password: String ): PasswordData {
        val analyzedData = PasswordAnalyzer.analyze(password)
        val score = scoreCalculator.calculate(password)
        val newPassword = PlainPassword(password)
        val passwordData = PasswordData(
            id= 0,
            application = "asd",
            url = "asd.asd",
            account = "asdasd",
            plainPassword = newPassword,
            hasLowerCase = analyzedData.hasLowerCase,
            hasUpperCase = analyzedData.hasUpperCase,
            hasNumbers = analyzedData.hasNumbers,
            hasSpecials = analyzedData.hasSpecials,
            creationDate = "now",
            lastUpdate = "now",
            securityScore = score
        )

        repository.save(passwordData)

        return passwordData
    }
}