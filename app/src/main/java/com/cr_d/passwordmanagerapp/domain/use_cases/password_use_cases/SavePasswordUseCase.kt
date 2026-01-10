package com.cr_d.passwordmanagerapp.domain.use_cases.password_use_cases

import java.time.LocalDate

import com.cr_d.passwordmanagerapp.data.dto.PasswordDetail
import com.cr_d.passwordmanagerapp.data.dto.PasswordAccountInfoDto
import com.cr_d.passwordmanagerapp.data.dto.PasswordAppInfoDto
import com.cr_d.passwordmanagerapp.data.mapper.toDomain
import com.cr_d.passwordmanagerapp.data.repository.interfaces.IPasswordRepository
import com.cr_d.passwordmanagerapp.domain.entities.Password
import com.cr_d.passwordmanagerapp.domain.use_cases.security_use_cases.EncryptStringUseCase
import com.cr_d.passwordmanagerapp.domain.value_objects.DateInfo
import com.cr_d.passwordmanagerapp.ui.model.ApplicationInfo

class SavePasswordUseCase (
    private val repository: IPasswordRepository,
    private val encrypt: EncryptStringUseCase,
    private val analyzer: AnalyzePasswordUseCase,
    private val scoreCalculator: CalculateSecurityScoreUseCase
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
        val metadataInfo = analyzer(password)
        val encryptedPassword = encrypt(password)
        val encryptedNotes = encrypt(notes)
        val parsedAppData = PasswordAppInfoDto(
            appName = appInfo.appName,
            appUrl = appInfo.appUrl
        )
        val parsedAccountData = PasswordAccountInfoDto(
            account = appInfo.appAccount
        )
        val score = scoreCalculator(password)
            //TODO: FIX
        val newPassword = PasswordDetail(
            id = 0,
            cipheredPassword = encryptedPassword,
            appData = parsedAppData,
            accountData = parsedAccountData,
            dateInfo = dateInfo,
            score = score,
            metadata = metadataInfo,
            cipheredNotes = encryptedNotes
        )
                //TODO: FIX
        repository.save(newPassword.toDomain())

        return newPassword.toDomain()
    }
}