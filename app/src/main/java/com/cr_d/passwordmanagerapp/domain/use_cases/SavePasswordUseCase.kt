package com.cr_d.passwordmanagerapp.domain.use_cases

import java.time.LocalDate

import com.cr_d.passwordmanagerapp.data.dto.PasswordDetail
import com.cr_d.passwordmanagerapp.data.dto.PasswordAccountInfoDto
import com.cr_d.passwordmanagerapp.data.dto.PasswordAppInfoDto
import com.cr_d.passwordmanagerapp.data.mapper.toDomain
import com.cr_d.passwordmanagerapp.data.repository.interfaces.IPasswordRepository
import com.cr_d.passwordmanagerapp.domain.entities.Password
import com.cr_d.passwordmanagerapp.domain.services.PasswordAnalyzer
import com.cr_d.passwordmanagerapp.domain.services.SecurityScoreCalculator
import com.cr_d.passwordmanagerapp.domain.value_objects.DateInfo
import com.cr_d.passwordmanagerapp.ui.model.ApplicationInfo

class SavePasswordUseCase (
    private val repository: IPasswordRepository,
    private val encrypt: EncryptStringUseCase
){
    suspend operator fun invoke(
        password: String,
        appInfo: ApplicationInfo,
        score: Double,
        notes: String
    ): Password {
        val creationDate = LocalDate.now()
        val dateInfo = DateInfo(
            creationDate = creationDate,
            lastUpdate = creationDate
        )
        val metadataInfo = PasswordAnalyzer.analyze(password)
        val encryptedPassword = encrypt(password)
        val encryptedNotes = encrypt(notes)
        val parsedAppData = PasswordAppInfoDto(
            appName = appInfo.appName,
            appUrl = appInfo.appUrl
        )
        val parsedAccountData = PasswordAccountInfoDto(
            account = appInfo.appAccount
        )
            //TODO: FIX
        val newPassword = PasswordDetail(
            id = 0,
            cipheredPassword = encryptedPassword,
            appData = parsedAppData,
            accountData = parsedAccountData,
            dateInfo = dateInfo,
            score = SecurityScoreCalculator().calculate(password),
            metadata = metadataInfo,
            cipheredNotes = encryptedNotes
        )
                //TODO: FIX
        repository.save(newPassword.toDomain())

        return newPassword.toDomain()
    }
}