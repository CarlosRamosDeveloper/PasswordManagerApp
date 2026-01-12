package com.cr_d.passwordmanagerapp.data.seed

import java.time.LocalDate

import com.cr_d.passwordmanagerapp.data.crypto.CryptoService
import com.cr_d.passwordmanagerapp.data.entities.AccountEntity
import com.cr_d.passwordmanagerapp.data.entities.ApplicationEntity
import com.cr_d.passwordmanagerapp.data.mapper.toDomain
import com.cr_d.passwordmanagerapp.domain.services.HashService
import com.cr_d.passwordmanagerapp.domain.use_cases.security_use_cases.EncryptStringUseCase
import com.cr_d.passwordmanagerapp.domain.value_objects.DateInfo
import com.cr_d.passwordmanagerapp.domain.value_objects.PasswordMetadata
import com.cr_d.passwordmanagerapp.ui.model.ApplicationInfo
import com.cr_d.passwordmanagerapp.ui.model.PasswordUiState

val encrypt = EncryptStringUseCase(CryptoService())
val hash = HashService()

//TODO: FIX
private fun fakePassword(
    plainPassword: String,
    appName: String,
    account: String,
    creationDate: String,
    lastUpdate: String,
    notes: String = ""
): PasswordUiState {
    val pwd = encrypt(plainPassword)
    val notes = encrypt(notes)
    val dateInfo = DateInfo(
        creationDate = LocalDate.parse(creationDate),
        lastUpdate = LocalDate.parse(lastUpdate)
    )
    val appInfo = ApplicationInfo(
        appName = appName,
        appUrl = "",
        appAccount = account
    )
    return PasswordUiState (
        id = 0L,
        cipheredPassword = pwd,
        appInfo = appInfo,
        dateInfo = dateInfo,
        cipheredNotes = notes,
        metadata = PasswordMetadata(),
    )
}

//TODO: Pasarlo a uiState
private fun fakeAccount(
    account: String,
    notes: String = ""
) : AccountEntity {
    val accountHash = hash.convertToSha256(account)
    val account = encrypt(account)
    val notes = encrypt(notes)

    return AccountEntity(
        cipheredAccount = account.encryptedText,
        accountIv = account.iv,
        cipheredNotes = notes.encryptedText,
        notesIv = notes.iv,
        accountHash = accountHash
    )
}
//TODO: Pasarlo a uiState
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
    private val _passwords = mutableListOf(
        fakePassword(
            plainPassword = "Abcdef123!",
            appName = "Amazon.es",
            account = "juan.perez@gmail.com",
            creationDate = LocalDate.of(2023, 1, 10).toString(),
            lastUpdate = LocalDate.of(2023, 12, 1).toString(),
        ),
        fakePassword(
            plainPassword = "contraseñaSegura!!",
            appName = "ChatGPT",
            account = "ana@empresa.com",
            creationDate = LocalDate.of(2021, 11, 1).toString(),
            lastUpdate = LocalDate.of(2021, 11, 1).toString(),
        ),
        fakePassword(
            plainPassword = "onlylowercase",
            appName = "Google",
            account = "usuario123",
            creationDate = LocalDate.of(2020, 2, 20).toString(),
            lastUpdate = LocalDate.of(2022, 2, 20).toString(),
        ),
        fakePassword(
            plainPassword = "MARIA2020",
            appName = "Spotify",
            account = "maria98",
            creationDate = LocalDate.of(2021, 8, 8).toString(),
            lastUpdate = LocalDate.of(2024, 1, 10).toString(),
            notes = "La app no acepta símbolos"
        ),
        fakePassword(
            plainPassword = "SuperS4f3AtW0rK!",
            appName = "LinkedIn.es",
            account = "Jav4De5",
            creationDate = LocalDate.of(2023, 3, 30).toString(),
            lastUpdate = LocalDate.of(2023, 9, 5).toString(),
        ),
        fakePassword(
            plainPassword = "JavA_Dev#2024",
            appName = "Github",
            account = "Jav4De5",
            creationDate = LocalDate.of(2024, 1, 15).toString(),
            lastUpdate = LocalDate.of(2024, 2, 2).toString(),
            notes = "Cuenta usada para pruebas internas"
        ),
        fakePassword(
            plainPassword = "PizzaLover99",
            appName = "Amazon.es",
            account = "Jav4De5",
            creationDate = LocalDate.of(2022, 6, 12).toString(),
            lastUpdate = LocalDate.of(2023, 4, 1).toString(),
            notes = "La app no acepta símbolos"
        ),
        fakePassword(
            plainPassword = "F1_RedBull@Max",
            appName = "Spotify",
            account = "mario_racing",
            creationDate = LocalDate.of(2020, 9, 5).toString(),
            lastUpdate = LocalDate.of(2023, 11, 30).toString(),
            notes = "Renovar suscripción en marzo"
        ),
        fakePassword(
            plainPassword = "Sunset*Photo_2023",
            appName = "Amazon.es",
            account = "lucia.photo",
            creationDate = LocalDate.of(2023, 2, 1).toString(),
            lastUpdate = LocalDate.of(2023, 2, 1).toString(),
            notes = "Fotografiar el año al amanecer"
        ),
        fakePassword(
            plainPassword = "NoNumbers!!!",
            appName = "brainwave",
            account = "lucia.photo",
            creationDate = LocalDate.of(2021, 12, 25).toString(),
            lastUpdate = LocalDate.of(2024, 1, 5).toString(),
            notes = "Grupos de meditación"
        ),
    )
    val passwords: MutableList<PasswordUiState>
        get() = _passwords

    // TODO: Pasarlo a lista de UI
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

    // Pasarlo a lista de UI
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
