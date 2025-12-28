package com.cr_d.passwordmanagerapp.data.mapper

import com.cr_d.passwordmanagerapp.application.use_cases.DecryptStringUseCase
import com.cr_d.passwordmanagerapp.data.crypto.CryptoService
import com.cr_d.passwordmanagerapp.data.crypto.EncryptedPayload
import java.time.LocalDate

import com.cr_d.passwordmanagerapp.data.entities.PasswordEntity
import com.cr_d.passwordmanagerapp.domain.entities.PasswordAnalyzer
import com.cr_d.passwordmanagerapp.domain.entities.SecurityScoreCalculator
import com.cr_d.passwordmanagerapp.domain.value_objects.ApplicationInfo
import com.cr_d.passwordmanagerapp.domain.value_objects.DateInfo
import com.cr_d.passwordmanagerapp.domain.value_objects.PasswordData
import com.cr_d.passwordmanagerapp.domain.value_objects.PasswordMetadata

fun PasswordEntity.toDomainCalculated(): PasswordData {
    val decrypt = DecryptStringUseCase(CryptoService())
    val encryptedData = EncryptedPayload(cipheredPassword, passwordIv)

    return this.toDomain(
        metadata = PasswordAnalyzer.analyze(decrypt(encryptedData)),
        score = SecurityScoreCalculator().calculate(decrypt(encryptedData))
    )
}

fun PasswordEntity.toDomain(metadata: PasswordMetadata, score: Double): PasswordData {
    val cipheredPassword = EncryptedPayload(
        this@toDomain.cipheredPassword, passwordIv
    )
    val cipheredNotes = EncryptedPayload(
        this.cipheredNotes, this.notesIv
    )

    return PasswordData(
        id = id,
        cipheredPassword = cipheredPassword,
        appInfo = ApplicationInfo(
            appName = appName,
            appUrl = appUrl,
            appAccount = account
        ),
        metadata = metadata,
        dateInfo = DateInfo(
            creationDate = LocalDate.parse(creationDate),
            lastUpdate = LocalDate.parse(lastUpdate)
        ),
        score = score,
        cipheredNotes = cipheredNotes
    )
}