package com.cr_d.passwordmanagerapp.domain.use_cases.password_use_cases

import java.time.LocalDate

import com.cr_d.passwordmanagerapp.data.dto.PasswordCreationData
import com.cr_d.passwordmanagerapp.data.repository.interfaces.IPasswordRepository
import com.cr_d.passwordmanagerapp.domain.entities.Password
import com.cr_d.passwordmanagerapp.domain.use_cases.security_use_cases.EncryptStringUseCase
import com.cr_d.passwordmanagerapp.domain.value_objects.DateInfo

class SavePasswordUseCase (
    private val repository: IPasswordRepository,
    private val encrypt: EncryptStringUseCase,
){
    suspend operator fun invoke(
        data : PasswordCreationData
    ): Password {
        val creationDate = LocalDate.now()
        val dateInfo = DateInfo(
            creationDate = creationDate,
            lastUpdate = creationDate
        )
        val encryptedPassword = encrypt(data.password)
        val encryptedNotes = encrypt(data.notes)

        //TODO: FIX
        val newPassword = Password(
            id = 0,
            cipheredPassword = encryptedPassword,
            appId = data.appId,
            accountId = data.accId,
            dateInfo = dateInfo,
            cipheredNotes = encryptedNotes
        )

        repository.save(newPassword)

        return newPassword
    }
}