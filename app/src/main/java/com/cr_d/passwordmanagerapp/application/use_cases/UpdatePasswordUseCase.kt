package com.cr_d.passwordmanagerapp.application.use_cases

import java.time.LocalDate

import com.cr_d.passwordmanagerapp.application.interfaces.IPasswordRepository
import com.cr_d.passwordmanagerapp.data.crypto.CryptoService
import com.cr_d.passwordmanagerapp.domain.entities.PasswordAnalyzer
import com.cr_d.passwordmanagerapp.domain.entities.SecurityScoreCalculator
import com.cr_d.passwordmanagerapp.domain.value_objects.ApplicationInfo
import com.cr_d.passwordmanagerapp.domain.value_objects.DateInfo
import com.cr_d.passwordmanagerapp.domain.value_objects.PasswordData

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

        val encrypt = EncryptStringUseCase(CryptoService())
        val dateInfo = DateInfo(
            creationDate = existing.dateInfo.creationDate,
            lastUpdate = LocalDate.now()
        )
        val metadataInfo = PasswordAnalyzer.analyze(newPassword)
        val encryptedPassword = encrypt(newPassword)
        val encryptedNotes = encrypt("")

        val updatedPassword = PasswordData(
            id = existing.id,
            cipheredPassword = encryptedPassword,
            appInfo = appInfo,
            dateInfo = dateInfo,
            score = SecurityScoreCalculator().calculate(newPassword),
            metadata = metadataInfo,
            cipheredNotes = encryptedNotes
        )

        repository.update(updatedPassword)

        return updatedPassword
    }
}