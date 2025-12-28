package com.cr_d.passwordmanagerapp.application.repositories

import java.time.LocalDate

import com.cr_d.passwordmanagerapp.application.interfaces.IPasswordRepository
import com.cr_d.passwordmanagerapp.data.entities.PasswordEntity
import com.cr_d.passwordmanagerapp.data.mapper.toDomainCalculated
import com.cr_d.passwordmanagerapp.domain.value_objects.PasswordData

class InMemoryPasswordRepository : IPasswordRepository {
    private val passwords = mutableListOf(
        PasswordEntity(
            id = 1,
            plainPassword = "Abcdef123!",
            appName = "Gmail",
            appUrl = "https://mail.google.com",
            account = "juan.perez@gmail.com",
            creationDate = LocalDate.of(2023,1,10).toString(),
            lastUpdate = LocalDate.of(2023, 12, 1).toString(),
            notes = ""
        ),
        PasswordEntity(
            id = 2,
            plainPassword = "contraseñaSegura!",
            appName = "Work Mail",
            appUrl = "https://mail.empresa.com",
            account = "ana@empresa.com",
            creationDate = LocalDate.of(2021, 11, 1).toString(),
            lastUpdate = LocalDate.of(2021, 11, 1).toString(),
            notes = ""
        ),
        PasswordEntity(
            id = 3,
            plainPassword = "onlylowercase",
            appName = "LocalApp",
            appUrl = "http://localhost:8080",
            account = "usuario123",
            creationDate = LocalDate.of(2020, 2, 20).toString(),
            lastUpdate = LocalDate.of(2022, 2, 20).toString(),
            notes = ""
        ),
        PasswordEntity(
            id = 4,
            plainPassword = "MARIA2020",
            appName = "BankPortal",
            appUrl = "https://bank.example.com",
            account = "maria98",
            creationDate = LocalDate.of(2021, 8, 8).toString(),
            lastUpdate = LocalDate.of(2024, 1, 10).toString(),
            notes = ""
        ),
        PasswordEntity(
            id = 5,
            plainPassword = "Ch@t_User2021",
            appName = "ChatApp",
            appUrl = "https://chat.example.com",
            account = "pedro@chat.com",
            creationDate = LocalDate.of(2023, 3, 30).toString(),
            lastUpdate = LocalDate.of(2023, 9, 5).toString(),
            notes = ""
        ),
        PasswordEntity(
            id = 6,
            plainPassword = "JavA_Dev#2024",
            appName = "DevForge",
            appUrl = "https://devforge.io",
            account = "carlos.dev",
            creationDate = LocalDate.of(2024, 1, 15).toString(),
            lastUpdate = LocalDate.of(2024, 2, 2).toString(),
            notes = "Cuenta usada para pruebas internas"
        ),
        PasswordEntity(
            id = 7,
            plainPassword = "PizzaLover99",
            appName = "FoodRush",
            appUrl = "https://foodrush.es",
            account = "nacho.pizza",
            creationDate = LocalDate.of(2022, 6, 12).toString(),
            lastUpdate = LocalDate.of(2023, 4, 1).toString(),
            notes = ""
        ),
        PasswordEntity(
            id = 8,
            plainPassword = "F1_RedBull@Max",
            appName = "SportsLive",
            appUrl = "https://sportslive.net",
            account = "mario_racing",
            creationDate = LocalDate.of(2020, 9, 5).toString(),
            lastUpdate = LocalDate.of(2023, 11, 30).toString(),
            notes = "Suscripción anual"
        ),
        PasswordEntity(
            id = 9,
            plainPassword = "Sunset*Photo_2023",
            appName = "PicNest",
            appUrl = "https://picnest.app",
            account = "lucia.photo",
            creationDate = LocalDate.of(2023, 2, 1).toString(),
            lastUpdate = LocalDate.of(2023, 2, 1).toString(),
            notes = ""
        ),
        PasswordEntity(
            id = 10,
            plainPassword = "NoNumbers!!",
            appName = "MindControl",
            appUrl = "https://mind-control.ai",
            account = "brainwave",
            creationDate = LocalDate.of(2021, 12, 25).toString(),
            lastUpdate = LocalDate.of(2024, 1, 5).toString(),
            notes = "Cuenta usada para cursos de meditación"
        )
    )

    private var lastId = 10

    private fun parseData(): List<PasswordData> {
        return passwords.map { it.toDomainCalculated() }
    }

    override fun findAll(): List<PasswordData> {
        return parseData()
    }

    override fun findByApplication(app: String): List<PasswordData> {
        return parseData().filter { it.appInfo.appName == app }
    }

    override fun findByAccount(account: String): List<PasswordData> {
        return parseData().filter { it.appInfo.appAccount == account }
    }

    override fun findById(id: Int): PasswordData? {
        return parseData().find { it.id == id }
    }

    override fun save(passwordData: PasswordEntity) {
        val passwordWithId = passwordData.copy(id = ++lastId)
        passwords.add(passwordWithId)
    }

    override fun update(passwordData: PasswordEntity) {
        val index = passwords.indexOfFirst { it.id == passwordData.id }
        if (index != -1) passwords[index] = passwordData
    }

    override fun delete(id: Int) {
        passwords.removeIf { it.id==id }
    }
}