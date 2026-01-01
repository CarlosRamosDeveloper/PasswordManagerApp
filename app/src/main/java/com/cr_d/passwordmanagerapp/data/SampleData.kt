package com.cr_d.passwordmanagerapp.data

import java.time.LocalDate

import com.cr_d.passwordmanagerapp.application.use_cases.EncryptStringUseCase
import com.cr_d.passwordmanagerapp.data.crypto.CryptoService
import com.cr_d.passwordmanagerapp.data.entities.AccountEntity
import com.cr_d.passwordmanagerapp.data.entities.PasswordEntity
import com.cr_d.passwordmanagerapp.data.mapper.toDomainCalculated

val encrypt = EncryptStringUseCase(CryptoService())

private fun fakePassword(
    plainPassword: String,
    appName: String,
    appUrl: String,
    account: String,
    creationDate: String,
    lastUpdate: String,
    notes: String = ""
): PasswordEntity {
    val pwd = encrypt(plainPassword)
    val notes = encrypt(notes)

    return PasswordEntity(
        cipheredPassword = pwd.encryptedText,
        passwordIv = pwd.iv,
        appName = appName,
        appUrl = appUrl,
        account = account,
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

object SampleData {
    val passwords = mutableListOf(
        fakePassword(
            plainPassword = "Abcdef123!",
            appName = "Gmail",
            appUrl = "https://mail.google.com",
            account = "juan.perez@gmail.com",
            creationDate = LocalDate.of(2023, 1, 10).toString(),
            lastUpdate = LocalDate.of(2023, 12, 1).toString(),
            notes = "contraseñaSegura!"
        ).toDomainCalculated(),
        fakePassword(
            plainPassword = "contraseñaSegura!!",
            appName = "Work Mail",
            appUrl = "https://mail.empresa.com",
            account = "ana@empresa.com",
            creationDate = LocalDate.of(2021, 11, 1).toString(),
            lastUpdate = LocalDate.of(2021, 11, 1).toString(),
        ).toDomainCalculated(),
        fakePassword(
            plainPassword = "onlylowercase",
            appName = "LocalApp",
            appUrl = "http://localhost:8080",
            account = "usuario123",
            creationDate = LocalDate.of(2020, 2, 20).toString(),
            lastUpdate = LocalDate.of(2022, 2, 20).toString(),
        ).toDomainCalculated(),
        fakePassword(
            plainPassword = "MARIA2020",
            appName = "BankPortal",
            appUrl = "https://bank.example.com",
            account = "maria98",
            creationDate = LocalDate.of(2021, 8, 8).toString(),
            lastUpdate = LocalDate.of(2024, 1, 10).toString(),
            notes = "La app no acepta símbolos"
        ).toDomainCalculated(),
        fakePassword(
            plainPassword = "SuperS4f3AtW0rK!",
            appName = "ChatApp",
            appUrl = "https://chat.example.com",
            account = "pedro@chat.com",
            creationDate = LocalDate.of(2023, 3, 30).toString(),
            lastUpdate = LocalDate.of(2023, 9, 5).toString(),
        ).toDomainCalculated(),
        fakePassword(
            plainPassword = "JavA_Dev#2024",
            appName = "DevForge",
            appUrl = "https://devforge.io",
            account = "carlos.dev",
            creationDate = LocalDate.of(2024, 1, 15).toString(),
            lastUpdate = LocalDate.of(2024, 2, 2).toString(),
            notes = "Cuenta usada para pruebas internas"
        ).toDomainCalculated(),
        fakePassword(
            plainPassword = "PizzaLover99",
            appName = "FoodRush",
            appUrl = "https://foodrush.es",
            account = "nacho.pizza",
            creationDate = LocalDate.of(2022, 6, 12).toString(),
            lastUpdate = LocalDate.of(2023, 4, 1).toString(),
            notes = "La app no acepta símbolos"
        ).toDomainCalculated(),
        fakePassword(
            plainPassword = "F1_RedBull@Max",
            appName = "SportsLive",
            appUrl = "https://sportslive.net",
            account = "mario_racing",
            creationDate = LocalDate.of(2020, 9, 5).toString(),
            lastUpdate = LocalDate.of(2023, 11, 30).toString(),
            notes = "Renovar suscripción en marzo"
        ).toDomainCalculated(),
        fakePassword(
            plainPassword = "Sunset*Photo_2023",
            appName = "PicNest",
            appUrl = "https://picnest.app",
            account = "lucia.photo",
            creationDate = LocalDate.of(2023, 2, 1).toString(),
            lastUpdate = LocalDate.of(2023, 2, 1).toString(),
            notes = "Fotografiar el año al amanecer"
        ).toDomainCalculated(),
        fakePassword(
            plainPassword = "NoNumbers!!!",
            appName = "MindControl",
            appUrl = "https://mind-control.ai",
            account = "brainwave",
            creationDate = LocalDate.of(2021, 12, 25).toString(),
            lastUpdate = LocalDate.of(2024, 1, 5).toString(),
            notes = "Grupos de meditación"
        ).toDomainCalculated(),
    )
    val accounts = mutableListOf(
        fakeAccount(
            account = "juan.perez@gmail.com",

        ),
        fakeAccount(
            account = "ana@empresa.com",
            notes = "No usar en otra aplicación"
        ),
        fakeAccount(
            account = "usuario123",
            notes = "Cuenta comprometida, cambiar cuanto antes"
        ),
        fakeAccount(
            account = "maria98"
        )
    )
}
