package com.cr_d.passwordmanagerapp.data.mapper

import java.time.LocalDate

import com.cr_d.passwordmanagerapp.data.entities.PasswordEntity
import com.cr_d.passwordmanagerapp.domain.entities.PasswordAnalyzer
import com.cr_d.passwordmanagerapp.domain.entities.SecurityScoreCalculator
import com.cr_d.passwordmanagerapp.domain.value_objects.ApplicationInfo
import com.cr_d.passwordmanagerapp.domain.value_objects.DateInfo
import com.cr_d.passwordmanagerapp.domain.value_objects.PasswordData
import com.cr_d.passwordmanagerapp.domain.value_objects.PasswordMetadata
import com.cr_d.passwordmanagerapp.domain.value_objects.PlainPassword

fun PasswordEntity.toDomainCalculated(): PasswordData =
    this.toDomain(
        metadata = PasswordAnalyzer.analyze(plainPassword),
        score = SecurityScoreCalculator().calculate(plainPassword)
    )

fun PasswordEntity.toDomain(metadata: PasswordMetadata, score: Double): PasswordData = PasswordData(
    id = id,
    plainPassword = PlainPassword(plainPassword),
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
    notes = notes
)