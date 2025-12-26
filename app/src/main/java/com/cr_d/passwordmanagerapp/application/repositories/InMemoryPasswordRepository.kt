package com.cr_d.passwordmanagerapp.application.repositories

import java.time.LocalDate

import com.cr_d.passwordmanagerapp.application.interfaces.IPasswordRepository
import com.cr_d.passwordmanagerapp.domain.value_objects.ApplicationInfo
import com.cr_d.passwordmanagerapp.domain.value_objects.DateInfo
import com.cr_d.passwordmanagerapp.domain.value_objects.PasswordData
import com.cr_d.passwordmanagerapp.domain.value_objects.PasswordMetadata
import com.cr_d.passwordmanagerapp.domain.value_objects.PlainPassword

class InMemoryPasswordRepository : IPasswordRepository {
    private val passwords = mutableListOf(
        PasswordData(
            id = 1,
            plainPassword = PlainPassword("Abcdef123!"),
            appInfo = ApplicationInfo(
                appName = "Gmail",
                appUrl = "https://mail.google.com",
                appAccount = "juan.perez@gmail.com"
            ),
            metadata = PasswordMetadata(
                hasLowerCase = true,
                hasUpperCase = true,
                hasNumbers = true,
                hasSpecials = true,
            ),
            dateInfo = DateInfo(
                creationDate = LocalDate.of(2023,1,10),
                lastUpdate = LocalDate.of(2023, 12, 1)
            ),
            score = 8.50
        ),
        PasswordData(
            id = 2,
            plainPassword = PlainPassword("contraseñaSegura"),
            appInfo = ApplicationInfo(
                appName = "Work Mail",
                appUrl = "https://mail.empresa.com",
                appAccount = "ana@empresa.com"
            ),
            metadata = PasswordMetadata(
                hasLowerCase = true,
                hasUpperCase = true,
                hasNumbers = false,
                hasSpecials = false,
            ),
            dateInfo = DateInfo(
                creationDate = LocalDate.of(2021, 11, 1),
                lastUpdate = LocalDate.of(2021, 11, 1)
            ),
            score = 4.82
        ),
        PasswordData(
            id = 3,
            plainPassword = PlainPassword("onlylowercase"),
            appInfo = ApplicationInfo(
                appName = "LocalApp",
                appUrl = "http://localhost:8080",
                appAccount = "usuario123"
            ),
            metadata = PasswordMetadata(
                hasLowerCase = true,
                hasUpperCase = false,
                hasNumbers = false,
                hasSpecials = false,
            ),
            dateInfo = DateInfo(
                creationDate = LocalDate.of(2020, 2, 20),
                lastUpdate = LocalDate.of(2022, 2, 20)
            ),
            score = 2.94
        ),
        PasswordData(
            id = 4,
            plainPassword = PlainPassword("MARIA2020"),
            appInfo = ApplicationInfo(
                appName = "BankPortal",
                appUrl = "https://bank.example.com",
                appAccount = "maria98"
            ),
            metadata = PasswordMetadata(
                hasLowerCase = false,
                hasUpperCase = true,
                hasNumbers = true,
                hasSpecials = false,
            ),
            dateInfo = DateInfo(
                creationDate = LocalDate.of(2021, 8, 8),
                lastUpdate = LocalDate.of(2024, 1, 10)
            ),
            score = 4.42
        ),
        PasswordData(
            id = 5,
            plainPassword = PlainPassword("Ch@t_User2021"),
            appInfo = ApplicationInfo(
                appName = "ChatApp",
                appUrl = "https://chat.example.com",
                appAccount = "pedro@chat.com"
            ),
            metadata = PasswordMetadata(
                hasLowerCase = true,
                hasUpperCase = true,
                hasNumbers = true,
                hasSpecials = true,
            ),
            dateInfo = DateInfo(
                creationDate = LocalDate.of(2023, 3, 30),
                lastUpdate = LocalDate.of(2023, 9, 5)
            ),
            score = 7.85
        ),
        PasswordData(
            id = 6,
            plainPassword = PlainPassword("Tienda#1"),
            appInfo = ApplicationInfo(
                appName = "ShopAdmin",
                appUrl = "https://admin.tienda.es",
                appAccount = "soporte@tienda.es"
            ),
            metadata = PasswordMetadata(
                hasLowerCase = true,
                hasUpperCase = true,
                hasNumbers = true,
                hasSpecials = true,
            ),
            dateInfo = DateInfo(
                creationDate = LocalDate.of(2022, 10, 10),
                lastUpdate = LocalDate.of(2022, 10, 10)
            ),
            score = 7.21
        ),
        PasswordData(
            id = 7,
            plainPassword = PlainPassword("pass1234"),
            appInfo = ApplicationInfo(
                appName = "Ecommerce",
                appUrl = "https://shop.example.com",
                appAccount = "cliente77"
            ),
            metadata = PasswordMetadata(
                hasLowerCase = true,
                hasUpperCase = false,
                hasNumbers = true,
                hasSpecials = false,
            ),
            dateInfo = DateInfo(
                creationDate = LocalDate.of(2019, 5, 1),
                lastUpdate = LocalDate.of(2024, 4, 1)
            ),
            score = 3.0
        ),
        PasswordData(
            id = 8,
            plainPassword = PlainPassword("DevTeam!"),
            appInfo = ApplicationInfo(
                appName = "CI Server",
                appUrl = "https://ci.example.com",
                appAccount = "devteam"
            ),
            metadata = PasswordMetadata(
                hasLowerCase = true,
                hasUpperCase = true,
                hasNumbers = false,
                hasSpecials = true,
            ),
            dateInfo = DateInfo(
                creationDate = LocalDate.of(2024, 2, 14),
                lastUpdate = LocalDate.of(2024, 2, 14)
            ),
            score = 6.29
        ),
        PasswordData(
            id = 9,
            plainPassword = PlainPassword("A1b2c3d4"),
            appInfo = ApplicationInfo(
                appName = "Notes",
                appUrl = "https://notes.example.com",
                appAccount = "alicia"
            ),
            metadata = PasswordMetadata(
                hasLowerCase = true,
                hasUpperCase = true,
                hasNumbers = true,
                hasSpecials = false,
            ),
            dateInfo = DateInfo(
                creationDate = LocalDate.of(2018, 12, 12),
                lastUpdate = LocalDate.of(2020, 12, 12)
            ),
            score = 6.5
        ),
        PasswordData(
            id = 10,
            plainPassword = PlainPassword("S0f!a#2022"),
            appInfo = ApplicationInfo(
                appName = "SocialNetwork",
                appUrl = "https://social.example.com",
                appAccount = "sofia@red.com"
            ),
            metadata = PasswordMetadata(
                hasLowerCase = true,
                hasUpperCase = true,
                hasNumbers = true,
                hasSpecials = true,
            ),
            dateInfo = DateInfo(
                creationDate = LocalDate.of(2022, 7, 7),
                lastUpdate = LocalDate.of(2024, 6, 20)
            ),
            score = 8.82
        ),
        PasswordData(
            id = 11,
            plainPassword = PlainPassword("backup_server"),
            appInfo = ApplicationInfo(
                appName = "Backup",
                appUrl = "https://backup.example.com",
                appAccount = "backup@server"
            ),
            metadata = PasswordMetadata(
                hasLowerCase = true,
                hasUpperCase = false,
                hasNumbers = false,
                hasSpecials = true,
            ),
            dateInfo = DateInfo(
                creationDate = LocalDate.of(2018, 12, 12),
                lastUpdate = LocalDate.of(2020, 12, 12)
            ),
            score = 3.52
        ),
        PasswordData(
            id = 12,
            plainPassword = PlainPassword("Lu1s-Acc"),
            appInfo = ApplicationInfo(
                appName = "MobileApp",
                appUrl = "https://app.example.com",
                appAccount = "luis-app"
            ),
            metadata = PasswordMetadata(
                hasLowerCase = true,
                hasUpperCase = true,
                hasNumbers = true,
                hasSpecials = true,
            ),
            dateInfo = DateInfo(
                creationDate = LocalDate.of(2020, 9, 9),
                lastUpdate = LocalDate.of(2023, 11, 11)
            ),
            score = 7.0
        ),
        PasswordData(
            id = 13,
            plainPassword = PlainPassword("uni2024"),
            appInfo = ApplicationInfo(
                appName = "UniversityPortal",
                appUrl = "https://universidad.edu",
                appAccount = "info@universidad.edu"
            ),
            metadata = PasswordMetadata(
                hasLowerCase = true,
                hasUpperCase = false,
                hasNumbers = true,
                hasSpecials = false,
            ),
            dateInfo = DateInfo(
                creationDate = LocalDate.of(2024, 1, 1),
                lastUpdate = LocalDate.of(2024, 1, 1)
            ),
            score = 3.50
        ),PasswordData(
            id = 14,
            plainPassword = PlainPassword("M@nager2023!"),
            appInfo = ApplicationInfo(
                appName = "HR System",
                appUrl = "https://hr.example.com",
                appAccount = "manager"
            ),
            metadata = PasswordMetadata(
                hasLowerCase = true,
                hasUpperCase = true,
                hasNumbers = true,
                hasSpecials = true,
            ),
            dateInfo = DateInfo(
                creationDate = LocalDate.of(2023, 4, 4),
                lastUpdate = LocalDate.of(2024, 3, 3)
            ),
            score = 9.79
        ),
        PasswordData(
            id = 15,
            plainPassword = PlainPassword("OPER-ops"),
            appInfo = ApplicationInfo(
                appName = "Ops Dashboard",
                appUrl = "https://ops.example.com",
                appAccount = "operaciones"
            ),
            metadata = PasswordMetadata(
                hasLowerCase = true,
                hasUpperCase = true,
                hasNumbers = false,
                hasSpecials = true,
            ),
            dateInfo = DateInfo(
                creationDate = LocalDate.of(2021, 1, 15),
                lastUpdate = LocalDate.of(2022, 1, 15)
            ),
            score = 50.84
        ),
        PasswordData(
            id = 16,
            plainPassword = PlainPassword("12345678"),
            appInfo = ApplicationInfo(
                appName = "Demo",
                appUrl = "https://demo.example.com",
                appAccount = "test.user"
            ),
            metadata = PasswordMetadata(
                hasLowerCase = false,
                hasUpperCase = false,
                hasNumbers = true,
                hasSpecials = false,
            ),
            dateInfo = DateInfo(
                creationDate = LocalDate.of(2017, 7, 7),
                lastUpdate = LocalDate.of(2018, 7, 7)
            ),
            score = 10.0
        ),
        PasswordData(
            id = 17,
            plainPassword = PlainPassword("Ferno_99"),
            appInfo = ApplicationInfo(
                appName = "Gaming",
                appUrl = "https://gaming.example.com",
                appAccount = "fernando"
            ),
            metadata = PasswordMetadata(
                hasLowerCase = true,
                hasUpperCase = true,
                hasNumbers = true,
                hasSpecials = true,
            ),
            dateInfo = DateInfo(
                creationDate = LocalDate.of(2022, 12, 12),
                lastUpdate = LocalDate.of(2023, 12, 12)
            ),
            score = 7.55
        ),
        PasswordData(
            id = 18,
            plainPassword = PlainPassword("newsLETTER"),
            appInfo = ApplicationInfo(
                appName = "MediaCMS",
                appUrl = "https://cms.media.com",
                appAccount = "newsletter@media.com"
            ),
            metadata = PasswordMetadata(
                hasLowerCase = true,
                hasUpperCase = true,
                hasNumbers = false,
                hasSpecials = false,
            ),
            dateInfo = DateInfo(
                creationDate = LocalDate.of(2019, 3, 3),
                lastUpdate = LocalDate.of(2021, 3, 3)
            ),
            score = 4.2
        ),
        PasswordData(
            id = 19,
            plainPassword = PlainPassword("R00t!#Secure"),
            appInfo = ApplicationInfo(
                appName = "ServerRoot",
                appUrl = "ssh://192.168.0.1",
                appAccount = "root"
            ),
            metadata = PasswordMetadata(
                hasLowerCase = true,
                hasUpperCase = true,
                hasNumbers = true,
                hasSpecials = true,
            ),
            dateInfo = DateInfo(
                creationDate = LocalDate.of(2016, 6, 6),
                lastUpdate = LocalDate.of(2024, 7, 7)
            ),
            score = 9.20
        ),
        PasswordData(
            id = 20,
            plainPassword = PlainPassword("alumno.2025"),
            appInfo = ApplicationInfo(
                appName = "Campus",
                appUrl = "https://campus.univ.edu",
                appAccount = "alumno2025"
            ),
            metadata = PasswordMetadata(
                hasLowerCase = true,
                hasUpperCase = false,
                hasNumbers = true,
                hasSpecials = true,
            ),
            dateInfo = DateInfo(
                creationDate = LocalDate.of(2025, 1, 10),
                lastUpdate = LocalDate.of(2025, 1, 10)
            ),
            score = 5.5
        )
    )
    private var lastId = 20

    override fun findAll(): List<PasswordData> = passwords.toList()

    override fun findByApplication(app: String): List<PasswordData> {
        return passwords.filter { it.appInfo.appName == app }
    }

    override fun findByAccount(account: String): List<PasswordData> {
        return passwords.filter { it.appInfo.appAccount == account }
    }

    override fun findById(id: Int): PasswordData? {
        return passwords.find { it.id == id }
    }

    override fun save(passwordData: PasswordData) {
        val passwordWithId = passwordData.copy(id = ++lastId)
        passwords.add(passwordWithId)
    }

    override fun update(passwordData: PasswordData) {
        val index = passwords.indexOfFirst { it.id == passwordData.id }
        if (index != -1) passwords[index] = passwordData
    }

    override fun delete(id: Int) {
        passwords.removeIf { it.id==id }
    }
}