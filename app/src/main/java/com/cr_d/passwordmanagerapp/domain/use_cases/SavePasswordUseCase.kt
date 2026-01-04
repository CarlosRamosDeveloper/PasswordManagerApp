package com.cr_d.passwordmanagerapp.domain.use_cases

import java.time.LocalDate

import com.cr_d.passwordmanagerapp.data.repository.IPasswordRepository
import com.cr_d.passwordmanagerapp.data.mapper.toDomain
import com.cr_d.passwordmanagerapp.domain.entities.PasswordAnalyzer
import com.cr_d.passwordmanagerapp.domain.entities.SecurityScoreCalculator
import com.cr_d.passwordmanagerapp.domain.value_objects.ApplicationInfo
import com.cr_d.passwordmanagerapp.domain.value_objects.DateInfo
import com.cr_d.passwordmanagerapp.domain.value_objects.Password
import com.cr_d.passwordmanagerapp.domain.value_objects.PasswordDetail
import com.cr_d.passwordmanagerapp.ui.dto.PasswordAccountInfoDto
import com.cr_d.passwordmanagerapp.ui.dto.PasswordAppInfoDto

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