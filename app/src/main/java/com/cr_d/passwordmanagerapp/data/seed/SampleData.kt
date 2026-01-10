package com.cr_d.passwordmanagerapp.data.seed

import java.time.LocalDate

import com.cr_d.passwordmanagerapp.data.crypto.CryptoService
import com.cr_d.passwordmanagerapp.data.entities.AccountEntity
import com.cr_d.passwordmanagerapp.data.entities.ApplicationEntity
import com.cr_d.passwordmanagerapp.data.entities.PasswordEntity
import com.cr_d.passwordmanagerapp.data.mapper.toDomain
import com.cr_d.passwordmanagerapp.domain.use_cases.security_use_cases.EncryptStringUseCase

val encrypt = EncryptStringUseCase(CryptoService())

//TODO: FIX
private fun fakePassword(
    plainPassword: String,
    appId: Long,
    accountId: Long,
    creationDate: String,
    lastUpdate: String,
    notes: String = ""
): PasswordEntity {
    val pwd = encrypt(plainPassword)
    val notes = encrypt(notes)

    return PasswordEntity(
        cipheredPassword = pwd.encryptedText,
        passwordIv = pwd.iv,
        appId = appId,
        accountId = accountId,
        creationDate = creationDate,
        lastUpdate = lastUpdate,
        cipheredNotes = notes.encryptedText,
        notesIv = notes.iv
    )
}

private fun fakeAccount(
    account: String,
    notes: String = ""
) : AccountEntity {
    val account = encrypt(account)
    val notes = encrypt(notes)

    return AccountEntity(
        cipheredAccount = account.encryptedText,
        accountIv = account.iv,
        cipheredNotes = notes.encryptedText,
        notesIv = notes.iv
    )
}

private fun fakeApplication(
    appName: String,
    appUrl: String = "",
    notes: String = ""
) : ApplicationEntity {
    val notes = encrypt(notes)

    return ApplicationEntity(
        appName = appName,
        appUrl = appUrl,
        cipheredNotes = notes.encryptedText,
        notesIv = notes.iv
    )
}

object SampleData {
    val passwords = mutableListOf(
        fakePassword(
            plainPassword = "Abcdef123!",
            appId = 1,
            accountId = 1,
            creationDate = LocalDate.of(2023, 1, 10).toString(),
            lastUpdate = LocalDate.of(2023, 12, 1).toString(),
            notes = "contraseñaSegura!"
        ).toDomain(),
        fakePassword(
            plainPassword = "contraseñaSegura!!",
            appId = 2,
            accountId = 2,
            creationDate = LocalDate.of(2021, 11, 1).toString(),
            lastUpdate = LocalDate.of(2021, 11, 1).toString(),
        ).toDomain(),
        fakePassword(
            plainPassword = "onlylowercase",
            appId = 2,
            accountId = 5,
            creationDate = LocalDate.of(2020, 2, 20).toString(),
            lastUpdate = LocalDate.of(2022, 2, 20).toString(),
        ).toDomain(),
        fakePassword(
            plainPassword = "MARIA2020",
            appId = 3,
            accountId = 5,
            creationDate = LocalDate.of(2021, 8, 8).toString(),
            lastUpdate = LocalDate.of(2024, 1, 10).toString(),
            notes = "La app no acepta símbolos"
        ).toDomain(),
        fakePassword(
            plainPassword = "SuperS4f3AtW0rK!",
            appId = 4,
            accountId = 4,
            creationDate = LocalDate.of(2023, 3, 30).toString(),
            lastUpdate = LocalDate.of(2023, 9, 5).toString(),
        ).toDomain(),
        fakePassword(
            plainPassword = "JavA_Dev#2024",
            appId = 5,
            accountId = 5,
            creationDate = LocalDate.of(2024, 1, 15).toString(),
            lastUpdate = LocalDate.of(2024, 2, 2).toString(),
            notes = "Cuenta usada para pruebas internas"
        ).toDomain(),
        fakePassword(
            plainPassword = "PizzaLover99",
            appId = 4,
            accountId = 6,
            creationDate = LocalDate.of(2022, 6, 12).toString(),
            lastUpdate = LocalDate.of(2023, 4, 1).toString(),
            notes = "La app no acepta símbolos"
        ).toDomain(),
        fakePassword(
            plainPassword = "F1_RedBull@Max",
            appId = 5,
            accountId = 7,
            creationDate = LocalDate.of(2020, 9, 5).toString(),
            lastUpdate = LocalDate.of(2023, 11, 30).toString(),
            notes = "Renovar suscripción en marzo"
        ).toDomain(),
        fakePassword(
            plainPassword = "Sunset*Photo_2023",
            appId = 6,
            accountId = 8,
            creationDate = LocalDate.of(2023, 2, 1).toString(),
            lastUpdate = LocalDate.of(2023, 2, 1).toString(),
            notes = "Fotografiar el año al amanecer"
        ).toDomain(),
        fakePassword(
            plainPassword = "NoNumbers!!!",
            appId = 7,
            accountId = 9,
            creationDate = LocalDate.of(2021, 12, 25).toString(),
            lastUpdate = LocalDate.of(2024, 1, 5).toString(),
            notes = "Grupos de meditación"
        ).toDomain(),
    )
    val accounts = mutableListOf(
        fakeAccount(
            account = "juan.perez@gmail.com",
        ).toDomain(),
        fakeAccount(
            account = "ana@empresa.com",
            notes = "No usar en otra aplicación"
        ).toDomain(),
        fakeAccount(
            account = "usuario123",
            notes = "Cuenta comprometida, cambiar cuanto antes"
        ).toDomain(),
        fakeAccount(
            account = "maria98"
        ).toDomain(),
        fakeAccount(
            account = "Jav4De5"
        ).toDomain(),
        fakeAccount(
            account = "mario_Testing"
        ).toDomain(),
        fakeAccount(
            account = "mario_racing"
        ).toDomain(),
        fakeAccount(
            account = "lucia.photo"
        ).toDomain(),
        fakeAccount(
            account = "brainwave"
        ).toDomain(),

    )

    val applications = mutableListOf(
        fakeApplication(
            appName = "Amazon.es",
            appUrl = "www.amazon.es"
        ).toDomain(),
        fakeApplication(
            appName = "Netflix",
            appUrl = "www.netflix.com"
        ).toDomain(),
        fakeApplication(
            appName = "Google",
            appUrl = "www.google.com"
        ).toDomain(),
        fakeApplication(
            appName = "ChatGPT",
            appUrl = "www.chatgpt.com"
        ).toDomain(),
        fakeApplication(
            appName = "Github",
            appUrl = "www.github.com"
        ).toDomain(),
        fakeApplication(
            appName = "Spotify",
            appUrl = "https://open.spotify.com/intl-es"
        ).toDomain(),
        fakeApplication(
            appName = "LinkedIn.es",
            appUrl = "https://www.linkedin.com"
        ).toDomain(),
    )
}
