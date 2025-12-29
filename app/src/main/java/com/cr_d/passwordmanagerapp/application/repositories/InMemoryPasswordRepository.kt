package com.cr_d.passwordmanagerapp.application.repositories

import java.time.LocalDate

import com.cr_d.passwordmanagerapp.application.interfaces.IPasswordRepository
import com.cr_d.passwordmanagerapp.application.use_cases.EncryptStringUseCase
import com.cr_d.passwordmanagerapp.data.crypto.CryptoService
import com.cr_d.passwordmanagerapp.data.entities.PasswordEntity
import com.cr_d.passwordmanagerapp.data.mapper.toDomainCalculated
import com.cr_d.passwordmanagerapp.data.mapper.toEntity
import com.cr_d.passwordmanagerapp.domain.value_objects.PasswordData

class InMemoryPasswordRepository : IPasswordRepository {
    private val encrypt = EncryptStringUseCase(CryptoService())
    private val pw1 = encrypt("Abcdef123!")
    private val pw2 = encrypt("contraseñaSegura!!")
    private val pw3 = encrypt("onlylowercase")
    private val pw4 = encrypt("MARIA2020")
    private val pw5 = encrypt("Ch@t_User2021")
    private val pw6 = encrypt("JavA_Dev#2024")
    private val pw7 = encrypt("PizzaLover99")
    private val pw8 = encrypt("F1_RedBull@Max")
    private val pw9 = encrypt("Sunset*Photo_2023")
    private val pw10 = encrypt("NoNumbers!!")
    private val note1 = encrypt("contraseñaSegura!")
    private val note2 = encrypt("")
    private val note3 = encrypt("")
    private val note4 = encrypt("La app no acepta símbolos")
    private val note5 = encrypt("")
    private val note6 = encrypt("Cuenta usada para pruebas internas")
    private val note7 = encrypt("La app no acepta símbolos")
    private val note8 = encrypt("Renovar suscripción en marzo")
    private val note9 = encrypt("Fotografiar el año al amanecer")
    private val note10 = encrypt("Grupos de meditación")
    private val passwords =
        mutableListOf(
            PasswordEntity(
                id = 1,
                cipheredPassword = pw1.encryptedText,
                passwordIv = pw1.iv,
                appName = "Gmail",
                appUrl = "https://mail.google.com",
                account = "juan.perez@gmail.com",
                creationDate = LocalDate.of(2023,1,10).toString(),
                lastUpdate = LocalDate.of(2023, 12, 1).toString(),
                cipheredNotes = note1.encryptedText,
                notesIv = note1.iv
            ),
            PasswordEntity(
                id = 2,
                cipheredPassword = pw2.encryptedText,
                passwordIv = pw2.iv,
                appName = "Work Mail",
                appUrl = "https://mail.empresa.com",
                account = "ana@empresa.com",
                creationDate = LocalDate.of(2021, 11, 1).toString(),
                lastUpdate = LocalDate.of(2021, 11, 1).toString(),
                cipheredNotes = note2.encryptedText,
                notesIv = note2.iv
            ),
            PasswordEntity(
                id = 3,
                cipheredPassword = pw3.encryptedText,
                passwordIv = pw3.iv,
                appName = "LocalApp",
                appUrl = "http://localhost:8080",
                account = "usuario123",
                creationDate = LocalDate.of(2020, 2, 20).toString(),
                lastUpdate = LocalDate.of(2022, 2, 20).toString(),
                cipheredNotes = note3.encryptedText,
                notesIv = note3.iv
            ),
            PasswordEntity(
                id = 4,
                cipheredPassword = pw4.encryptedText,
                passwordIv = pw4.iv,
                appName = "BankPortal",
                appUrl = "https://bank.example.com",
                account = "maria98",
                creationDate = LocalDate.of(2021, 8, 8).toString(),
                lastUpdate = LocalDate.of(2024, 1, 10).toString(),
                cipheredNotes = note4.encryptedText,
                notesIv = note4.iv
            ),
            PasswordEntity(
                id = 5,
                cipheredPassword = pw5.encryptedText,
                passwordIv = pw5.iv,
                appName = "ChatApp",
                appUrl = "https://chat.example.com",
                account = "pedro@chat.com",
                creationDate = LocalDate.of(2023, 3, 30).toString(),
                lastUpdate = LocalDate.of(2023, 9, 5).toString(),
                cipheredNotes = note5.encryptedText,
                notesIv = note5.iv
            ),
            PasswordEntity(
                id = 6,
                cipheredPassword = pw6.encryptedText,
                passwordIv = pw6.iv,
                appName = "DevForge",
                appUrl = "https://devforge.io",
                account = "carlos.dev",
                creationDate = LocalDate.of(2024, 1, 15).toString(),
                lastUpdate = LocalDate.of(2024, 2, 2).toString(),
                cipheredNotes = note6.encryptedText,
                notesIv = note6.iv
            ),
            PasswordEntity(
                id = 7,
                cipheredPassword = pw7.encryptedText,
                passwordIv = pw7.iv,
                appName = "FoodRush",
                appUrl = "https://foodrush.es",
                account = "nacho.pizza",
                creationDate = LocalDate.of(2022, 6, 12).toString(),
                lastUpdate = LocalDate.of(2023, 4, 1).toString(),
                cipheredNotes = note7.encryptedText,
                notesIv = note7.iv
            ),
            PasswordEntity(
                id = 8,
                cipheredPassword = pw8.encryptedText,
                passwordIv = pw8.iv,
                appName = "SportsLive",
                appUrl = "https://sportslive.net",
                account = "mario_racing",
                creationDate = LocalDate.of(2020, 9, 5).toString(),
                lastUpdate = LocalDate.of(2023, 11, 30).toString(),
                cipheredNotes = note8.encryptedText,
                notesIv = note8.iv
            ),
            PasswordEntity(
                id = 9,
                cipheredPassword = pw9.encryptedText,
                passwordIv = pw9.iv,
                appName = "PicNest",
                appUrl = "https://picnest.app",
                account = "lucia.photo",
                creationDate = LocalDate.of(2023, 2, 1).toString(),
                lastUpdate = LocalDate.of(2023, 2, 1).toString(),
                cipheredNotes = note9.encryptedText,
                notesIv = note9.iv
            ),
            PasswordEntity(
                id = 10,
                cipheredPassword = pw10.encryptedText,
                passwordIv = pw10.iv,
                appName = "MindControl",
                appUrl = "https://mind-control.ai",
                account = "brainwave",
                creationDate = LocalDate.of(2021, 12, 25).toString(),
                lastUpdate = LocalDate.of(2024, 1, 5).toString(),
                cipheredNotes = note10.encryptedText,
                notesIv = note10.iv
            )
        )

    private var lastId = 10L

    private fun parseData(): List<PasswordData> {
        return passwords.map { it.toDomainCalculated() }
    }

    override suspend fun findAll(): List<PasswordData> {
        return parseData()
    }

    override suspend fun findByApplication(app: String): List<PasswordData> {
        return parseData().filter { it.appInfo.appName == app }
    }

    override suspend fun findByAccount(account: String): List<PasswordData> {
        return parseData().filter { it.appInfo.appAccount == account }
    }

    override suspend fun findById(id: Long): PasswordData? {
        return parseData().find { it.id == id }
    }

    override suspend fun save(passwordData: PasswordData) {
        val passwordWithId = passwordData.copy(id = ++lastId)
        passwords.add(passwordWithId.toEntity())
    }

    override suspend fun update(passwordData: PasswordData) {
        val index = passwords.indexOfFirst { it.id == passwordData.id }
        if (index != -1) passwords[index] = passwordData.toEntity()
    }

    override suspend fun delete(id: Long) {
        passwords.removeIf { it.id==id }
    }
}