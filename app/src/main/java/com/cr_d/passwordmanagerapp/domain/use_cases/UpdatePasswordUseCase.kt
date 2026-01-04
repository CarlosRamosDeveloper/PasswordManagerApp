package com.cr_d.passwordmanagerapp.domain.use_cases

import java.time.LocalDate

import com.cr_d.passwordmanagerapp.data.dto.PasswordDetail
import com.cr_d.passwordmanagerapp.data.dto.PasswordAccountInfoDto
import com.cr_d.passwordmanagerapp.data.dto.PasswordAppInfoDto
import com.cr_d.passwordmanagerapp.data.mapper.toDomain
import com.cr_d.passwordmanagerapp.data.repository.interfaces.IPasswordRepository
import com.cr_d.passwordmanagerapp.domain.services.PasswordAnalyzer
import com.cr_d.passwordmanagerapp.domain.services.SecurityScoreCalculator
import com.cr_d.passwordmanagerapp.domain.value_objects.DateInfo
import com.cr_d.passwordmanagerapp.ui.model.ApplicationInfo

class UpdatePasswordUseCase (
    private val repository: IPasswordRepository,
    private val encrypt: EncryptStringUseCase
){
    suspend operator fun invoke(
        id: Long,
        newPassword: String,
        appInfo: ApplicationInfo,
        notes: String = ""
    ): PasswordDetail {
        val existing = repository.findById(id)
            ?: throw IllegalArgumentException("Password not found")

        val dateInfo = DateInfo(
            creationDate = existing.dateInfo.creationDate,
            lastUpdate = LocalDate.now()
        )
        val metadataInfo = PasswordAnalyzer.analyze(newPassword)
        val encryptedPassword = encrypt(newPassword)
        val encryptedNotes = encrypt(notes)
        val parsedAppData = PasswordAppInfoDto(
            appName = appInfo.appName,
            appUrl = appInfo.appUrl
        )
        val parsedAccountData = PasswordAccountInfoDto(
            account = appInfo.appAccount
        )

        val updatedPassword = PasswordDetail(
            id = existing.id,
            cipheredPassword = encryptedPassword,
            appData = parsedAppData,
            accountData = parsedAccountData,
            dateInfo = dateInfo,
            score = SecurityScoreCalculator().calculate(newPassword),
            metadata = metadataInfo,
            cipheredNotes = encryptedNotes
        )

        repository.update(updatedPassword.toDomain())

        return updatedPassword
    }
}