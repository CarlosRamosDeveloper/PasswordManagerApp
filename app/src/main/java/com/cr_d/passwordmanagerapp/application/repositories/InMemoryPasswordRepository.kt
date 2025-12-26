package com.cr_d.passwordmanagerapp.application.repositories

import com.cr_d.passwordmanagerapp.application.interfaces.IPasswordRepository
import com.cr_d.passwordmanagerapp.domain.value_objects.ApplicationInfo
import com.cr_d.passwordmanagerapp.domain.value_objects.DateInfo
import com.cr_d.passwordmanagerapp.domain.value_objects.PasswordData
import com.cr_d.passwordmanagerapp.domain.value_objects.PasswordMetadata
import com.cr_d.passwordmanagerapp.domain.value_objects.PlainPassword
import java.time.LocalDate

class InMemoryPasswordRepository : IPasswordRepository {
    private val passwords = mutableListOf(
        PasswordData(
            id = 1,
            plainPassword = PlainPassword("Abcdef123!"),
            appInfo = ApplicationInfo(
                applicationName = "Gmail",
                url = "https://mail.google.com",
                account = "juan.perez@gmail.com"
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
            securityScore = 8.50
        ),
        PasswordData(
            id = 2,
            plainPassword = PlainPassword("contraseñaSegura"),
            appInfo = ApplicationInfo(
                applicationName = "Work Mail",
                url = "https://mail.empresa.com",
                account = "ana@empresa.com"
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
            securityScore = 4.82
        ),
        PasswordData(
            id = 3,
            plainPassword = PlainPassword("onlylowercase"),
            appInfo = ApplicationInfo(
                applicationName = "LocalApp",
                url = "http://localhost:8080",
                account = "usuario123"
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
            securityScore = 2.94
        ),
        PasswordData(
            id = 4,
            plainPassword = PlainPassword("MARIA2020"),
            appInfo = ApplicationInfo(
                applicationName = "BankPortal",
                url = "https://bank.example.com",
                account = "maria98"
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
            securityScore = 4.42
        ),
        PasswordData(
            id = 5,
            plainPassword = PlainPassword("Ch@t_User2021"),
            appInfo = ApplicationInfo(
                applicationName = "ChatApp",
                url = "https://chat.example.com",
                account = "pedro@chat.com"
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
            securityScore = 7.85
        ),
        PasswordData(
            id = 6,
            plainPassword = PlainPassword("Tienda#1"),
            appInfo = ApplicationInfo(
                applicationName = "ShopAdmin",
                url = "https://admin.tienda.es",
                account = "soporte@tienda.es"
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
            securityScore = 7.21
        ),
        PasswordData(
            id = 7,
            plainPassword = PlainPassword("pass1234"),
            appInfo = ApplicationInfo(
                applicationName = "Ecommerce",
                url = "https://shop.example.com",
                account = "cliente77"
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
            securityScore = 3.0
        ),
        PasswordData(
            id = 8,
            plainPassword = PlainPassword("DevTeam!"),
            appInfo = ApplicationInfo(
                applicationName = "CI Server",
                url = "https://ci.example.com",
                account = "devteam"
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
            securityScore = 6.29
        ),
        PasswordData(
            id = 9,
            plainPassword = PlainPassword("A1b2c3d4"),
            appInfo = ApplicationInfo(
                applicationName = "Notes",
                url = "https://notes.example.com",
                account = "alicia"
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
            securityScore = 6.5
        ),
        PasswordData(
            id = 10,
            plainPassword = PlainPassword("S0f!a#2022"),
            appInfo = ApplicationInfo(
                applicationName = "SocialNetwork",
                url = "https://social.example.com",
                account = "sofia@red.com"
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
            securityScore = 8.82
        ),
        PasswordData(
            id = 11,
            plainPassword = PlainPassword("backup_server"),
            appInfo = ApplicationInfo(
                applicationName = "Backup",
                url = "https://backup.example.com",
                account = "backup@server"
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
            securityScore = 3.52
        ),
        PasswordData(
            id = 12,
            plainPassword = PlainPassword("Lu1s-Acc"),
            appInfo = ApplicationInfo(
                applicationName = "MobileApp",
                url = "https://app.example.com",
                account = "luis-app"
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
            securityScore = 7.0
        ),
        PasswordData(
            id = 13,
            plainPassword = PlainPassword("uni2024"),
            appInfo = ApplicationInfo(
                applicationName = "UniversityPortal",
                url = "https://universidad.edu",
                account = "info@universidad.edu"
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
            securityScore = 3.50
        ),PasswordData(
            id = 14,
            plainPassword = PlainPassword("M@nager2023!"),
            appInfo = ApplicationInfo(
                applicationName = "HR System",
                url = "https://hr.example.com",
                account = "manager"
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
            securityScore = 9.79
        ),
        PasswordData(
            id = 15,
            plainPassword = PlainPassword("OPER-ops"),
            appInfo = ApplicationInfo(
                applicationName = "Ops Dashboard",
                url = "https://ops.example.com",
                account = "operaciones"
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
            securityScore = 50.84
        ),
        PasswordData(
            id = 16,
            plainPassword = PlainPassword("12345678"),
            appInfo = ApplicationInfo(
                applicationName = "Demo",
                url = "https://demo.example.com",
                account = "test.user"
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
            securityScore = 10.0
        ),
        PasswordData(
            id = 17,
            plainPassword = PlainPassword("Ferno_99"),
            appInfo = ApplicationInfo(
                applicationName = "Gaming",
                url = "https://gaming.example.com",
                account = "fernando"
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
            securityScore = 7.55
        ),
        PasswordData(
            id = 18,
            plainPassword = PlainPassword("newsLETTER"),
            appInfo = ApplicationInfo(
                applicationName = "MediaCMS",
                url = "https://cms.media.com",
                account = "newsletter@media.com"
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
            securityScore = 4.2
        ),
        PasswordData(
            id = 19,
            plainPassword = PlainPassword("R00t!#Secure"),
            appInfo = ApplicationInfo(
                applicationName = "ServerRoot",
                url = "ssh://192.168.0.1",
                account = "root"
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
            securityScore = 9.20
        ),
        PasswordData(
            id = 20,
            plainPassword = PlainPassword("alumno.2025"),
            appInfo = ApplicationInfo(
                applicationName = "Campus",
                url = "https://campus.univ.edu",
                account = "alumno2025"
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
            securityScore = 5.5
        )
    )
    private var lastId = 20

    override fun findAll(): List<PasswordData> = passwords.toList()

    override fun findByApplication(app: String): List<PasswordData> {
        return passwords.filter { it.appInfo.applicationName == app }
    }

    override fun findByAccount(account: String): List<PasswordData> {
        return passwords.filter { it.appInfo.account == account }
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