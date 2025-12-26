package com.cr_d.passwordmanagerapp.data

import com.cr_d.passwordmanagerapp.domain.value_objects.ApplicationInfo
import com.cr_d.passwordmanagerapp.domain.value_objects.DateInfo
import com.cr_d.passwordmanagerapp.domain.value_objects.PasswordData
import com.cr_d.passwordmanagerapp.domain.value_objects.PasswordMetadata
import com.cr_d.passwordmanagerapp.domain.value_objects.PlainPassword
import java.time.LocalDate

data class PasswordEntity(
    val id: Int,
    val plainPassword: String,
    val appName: String,
    val appUrl: String,
    val account: String,
    val creationDate: LocalDate,
    val lastUpdate: LocalDate,
    val notes: String,
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
        creationDate = creationDate,
        lastUpdate = lastUpdate
    ),
    score = score,
    notes = notes
)