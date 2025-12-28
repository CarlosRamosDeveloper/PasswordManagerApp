package com.cr_d.passwordmanagerapp.application.use_cases

import java.time.LocalDate

import com.cr_d.passwordmanagerapp.application.interfaces.IPasswordRepository
import com.cr_d.passwordmanagerapp.data.crypto.CryptoService
import com.cr_d.passwordmanagerapp.domain.entities.PasswordAnalyzer
import com.cr_d.passwordmanagerapp.domain.entities.SecurityScoreCalculator
import com.cr_d.passwordmanagerapp.domain.value_objects.ApplicationInfo
import com.cr_d.passwordmanagerapp.domain.value_objects.DateInfo
import com.cr_d.passwordmanagerapp.domain.value_objects.PasswordData

class SavePasswordUseCase (
    private val repository: IPasswordRepository,
){
    suspend operator fun invoke(
        password: String,
        appInfo: ApplicationInfo,
        score: Double
    ): PasswordData {
        //TODO implementar notas
        val encrypt = EncryptStringUseCase(CryptoService())
        val creationDate = LocalDate.now()
        val dateInfo = DateInfo(
            creationDate = creationDate,
            lastUpdate = creationDate
        )
        val metadataInfo = PasswordAnalyzer.analyze(password)
        val encryptedPassword = encrypt(password)
        val encryptedNotes = encrypt("")

        val newPassword = PasswordData(
            id = 0,
            cipheredPassword = encryptedPassword,
            appInfo = appInfo,
            dateInfo = dateInfo,
            score = SecurityScoreCalculator().calculate(password),
            metadata = metadataInfo,
            cipheredNotes = encryptedNotes
        )

        repository.save(newPassword)

        return newPassword
    }
}