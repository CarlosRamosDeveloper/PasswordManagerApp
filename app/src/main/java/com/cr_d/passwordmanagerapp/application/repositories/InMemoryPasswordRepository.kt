package com.cr_d.passwordmanagerapp.application.repositories

import com.cr_d.passwordmanagerapp.application.interfaces.IPasswordRepository
import com.cr_d.passwordmanagerapp.domain.value_objects.ApplicationInfo
import com.cr_d.passwordmanagerapp.domain.value_objects.PasswordData
import com.cr_d.passwordmanagerapp.domain.value_objects.PasswordMetadata
import com.cr_d.passwordmanagerapp.domain.value_objects.PlainPassword

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
                creationDate = "2023-01-10",
                lastUpdate = "2024-05-02"
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
                creationDate = "2022-06-15",
                lastUpdate = "2023-12-01"
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
                creationDate = "2021-11-01",
                lastUpdate = "2021-11-01"
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
                creationDate = "2020-02-20",
                lastUpdate = "2022-02-20"
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
                creationDate = "2021-08-08",
                lastUpdate = "2024-01-10"
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
                creationDate = "2023-03-30",
                lastUpdate = "2023-09-05"
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
                creationDate = "2022-10-10",
                lastUpdate = "2022-10-10"
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
                creationDate = "2019-05-01",
                lastUpdate = "2024-04-01"
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
                creationDate = "2024-02-14",
                lastUpdate = "2024-02-14"
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
                creationDate = "2022-07-07",
                lastUpdate = "2024-06-20"
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
                creationDate = "2018-12-12",
                lastUpdate = "2020-12-12"
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
                creationDate = "2020-09-09",
                lastUpdate = "2023-11-11"
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
                creationDate = "2024-01-01",
                lastUpdate = "2024-01-01"
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
                creationDate = "2023-04-04",
                lastUpdate = "2024-03-03"
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
                creationDate = "2021-01-15",
                lastUpdate = "2022-01-15"
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
                creationDate = "2017-07-07",
                lastUpdate = "2018-07-07"
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
                creationDate = "2022-12-12",
                lastUpdate = "2023-12-12"
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
                creationDate = "2019-03-03",
                lastUpdate = "2021-03-03"
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
                creationDate = "2016-06-06",
                lastUpdate = "2024-07-07"
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
                creationDate = "2025-01-10",
                lastUpdate = "2025-01-10"
            ),
            securityScore = 5.5
        )
    )

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
        passwords.add(passwordData)
    }

    override fun update(passwordData: PasswordData) {
        val index = passwords.indexOfFirst { it.id == passwordData.id }
        if (index != -1) passwords[index] = passwordData
    }

    override fun delete(id: Int) {
        passwords.removeIf { it.id==id }
    }
}