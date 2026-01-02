package com.cr_d.passwordmanagerapp.data.mapper

import com.cr_d.passwordmanagerapp.application.use_cases.DecryptStringUseCase
import com.cr_d.passwordmanagerapp.data.crypto.CryptoService
import com.cr_d.passwordmanagerapp.data.entities.PasswordEntity
import com.cr_d.passwordmanagerapp.domain.entities.PasswordAnalyzer
import com.cr_d.passwordmanagerapp.domain.entities.SecurityScoreCalculator
import com.cr_d.passwordmanagerapp.domain.value_objects.Password
import com.cr_d.passwordmanagerapp.domain.value_objects.PasswordDetail
import com.cr_d.passwordmanagerapp.domain.value_objects.PasswordDetailInfo
import com.cr_d.passwordmanagerapp.ui.dto.PasswordAccountInfoDto
import com.cr_d.passwordmanagerapp.ui.dto.PasswordAppInfoDto

fun Password.toEntity(): PasswordEntity {
    return PasswordEntity(
        id = id,
        cipheredPassword = cipheredPassword.encryptedText,
        passwordIv = cipheredPassword.iv,
        appId = appId,
        accountId = accountId,
        creationDate = dateInfo.creationDate.toString(),
        lastUpdate = dateInfo.lastUpdate.toString(),
        cipheredNotes = cipheredNotes.encryptedText,
        notesIv = cipheredNotes.iv
    )
}

fun Password.toDetail(): PasswordDetail {
    //TODO: Fix
    val decrypt = DecryptStringUseCase(CryptoService())
    val decryptedPassword = decrypt(cipheredPassword)
    val extraInfo = PasswordDetailInfo(
        appData = PasswordAppInfoDto(
            appName = "Test",
            appUrl = "Test.com"
        ),
        accountData = PasswordAccountInfoDto(
            account = "asd"
        ),
        metadata = PasswordAnalyzer.analyze(decryptedPassword),
        score = 1.0
    )
    return PasswordDetail(
        id = id,
        cipheredPassword = cipheredPassword,
        appData = extraInfo.appData,
        accountData = extraInfo.accountData,
        metadata = extraInfo.metadata,
        dateInfo = dateInfo,
        score = extraInfo.score,
        cipheredNotes = cipheredNotes,
    )
}