package com.cr_d.passwordmanagerapp.application.repositories

import java.time.LocalDate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

import com.cr_d.passwordmanagerapp.application.interfaces.IPasswordRepository
import com.cr_d.passwordmanagerapp.application.use_cases.EncryptStringUseCase
import com.cr_d.passwordmanagerapp.data.crypto.CryptoService
import com.cr_d.passwordmanagerapp.data.entities.PasswordEntity
import com.cr_d.passwordmanagerapp.data.mapper.toDomainCalculated
import com.cr_d.passwordmanagerapp.domain.value_objects.PasswordData

class InMemoryPasswordRepository : IPasswordRepository {
    private val encrypt = EncryptStringUseCase(CryptoService())
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    private var lastId = 0L

    private fun fake(
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
    private val passwords = mutableListOf<PasswordData>()

    suspend fun seedDatabase(){
        if (passwords.isEmpty()) {
            save(fake(
                plainPassword = "Abcdef123!",
                appName = "Gmail",
                appUrl = "https://mail.google.com",
                account = "juan.perez@gmail.com",
                creationDate = LocalDate.of(2023,1,10).toString(),
                lastUpdate = LocalDate.of(2023, 12, 1).toString(),
                notes = "contraseñaSegura!"
            ).toDomainCalculated())
            save(fake(
                plainPassword = "contraseñaSegura!!",
                appName = "Work Mail",
                appUrl = "https://mail.empresa.com",
                account = "ana@empresa.com",
                creationDate = LocalDate.of(2021, 11, 1).toString(),
                lastUpdate = LocalDate.of(2021, 11, 1).toString(),
            ).toDomainCalculated())
            save(fake(
                plainPassword = "onlylowercase",
                appName = "LocalApp",
                appUrl = "http://localhost:8080",
                account = "usuario123",
                creationDate = LocalDate.of(2020, 2, 20).toString(),
                lastUpdate = LocalDate.of(2022, 2, 20).toString(),
            ).toDomainCalculated())
            save(fake(
                plainPassword = "MARIA2020",
                appName = "BankPortal",
                appUrl = "https://bank.example.com",
                account = "maria98",
                creationDate = LocalDate.of(2021, 8, 8).toString(),
                lastUpdate = LocalDate.of(2024, 1, 10).toString(),
                notes = "La app no acepta símbolos"
            ).toDomainCalculated())
            save(fake(
                plainPassword = "SuperS4f3AtW0rK!",
                appName = "ChatApp",
                appUrl = "https://chat.example.com",
                account = "pedro@chat.com",
                creationDate = LocalDate.of(2023, 3, 30).toString(),
                lastUpdate = LocalDate.of(2023, 9, 5).toString(),
            ).toDomainCalculated())
            save(fake(
                plainPassword = "JavA_Dev#2024",
                appName = "DevForge",
                appUrl = "https://devforge.io",
                account = "carlos.dev",
                creationDate = LocalDate.of(2024, 1, 15).toString(),
                lastUpdate = LocalDate.of(2024, 2, 2).toString(),
                notes = "Cuenta usada para pruebas internas"
            ).toDomainCalculated())
            save(fake(
                plainPassword = "PizzaLover99",
                appName = "FoodRush",
                appUrl = "https://foodrush.es",
                account = "nacho.pizza",
                creationDate = LocalDate.of(2022, 6, 12).toString(),
                lastUpdate = LocalDate.of(2023, 4, 1).toString(),
                notes = "La app no acepta símbolos"
            ).toDomainCalculated())
            save(fake(
                plainPassword = "F1_RedBull@Max",
                appName = "SportsLive",
                appUrl = "https://sportslive.net",
                account = "mario_racing",
                creationDate = LocalDate.of(2020, 9, 5).toString(),
                lastUpdate = LocalDate.of(2023, 11, 30).toString(),
                notes = "Renovar suscripción en marzo"
            ).toDomainCalculated())
            save(fake(
                plainPassword = "Sunset*Photo_2023",
                appName = "PicNest",
                appUrl = "https://picnest.app",
                account = "lucia.photo",
                creationDate = LocalDate.of(2023, 2, 1).toString(),
                lastUpdate = LocalDate.of(2023, 2, 1).toString(),
                notes = "Fotografiar el año al amanecer"
            ).toDomainCalculated())
            save(fake(
                plainPassword = "NoNumbers!!!",
                appName = "MindControl",
                appUrl = "https://mind-control.ai",
                account = "brainwave",
                creationDate = LocalDate.of(2021, 12, 25).toString(),
                lastUpdate = LocalDate.of(2024, 1, 5).toString(),
                notes = "Grupos de meditación"
            ).toDomainCalculated())
        }
    }

    init {
        scope.launch {
            seedDatabase()
        }
    }

    override suspend fun findAll(): List<PasswordData> {
        return passwords
    }

    override suspend fun findByApplication(app: String): List<PasswordData> {
        return findAll().filter { it.appInfo.appName == app }
    }

    override suspend fun findByAccount(account: String): List<PasswordData> {
        return findAll().filter { it.appInfo.appAccount == account }
    }

    override suspend fun findById(id: Long): PasswordData? {
        return findAll().find { it.id == id }
    }

    override suspend fun save(passwordData: PasswordData) {
        val passwordWithId = passwordData.copy(id = ++lastId)
        passwords.add(passwordWithId)
    }

    override suspend fun update(passwordData: PasswordData) {
        val index = passwords.indexOfFirst { it.id == passwordData.id }
        if (index != -1) passwords[index] = passwordData
    }

    override suspend fun delete(id: Long) {
        passwords.removeIf { it.id==id }
    }
}