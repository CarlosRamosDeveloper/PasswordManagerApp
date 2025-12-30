package com.cr_d.passwordmanagerapp.application.use_cases

import android.util.Log
import java.time.LocalDate

import com.cr_d.passwordmanagerapp.application.interfaces.IPasswordRepository
import com.cr_d.passwordmanagerapp.data.crypto.CryptoService
import com.cr_d.passwordmanagerapp.domain.entities.PasswordAnalyzer
import com.cr_d.passwordmanagerapp.domain.entities.SecurityScoreCalculator
import com.cr_d.passwordmanagerapp.domain.value_objects.DateInfo
import com.cr_d.passwordmanagerapp.domain.value_objects.PasswordData

class UpdateNotesUseCase (
    val repository: IPasswordRepository
) {
    suspend operator fun invoke(id: Long, newNotes: String) : PasswordData{
        val existing = repository.findById(id)
            ?: throw IllegalArgumentException("Password not found")

        val encrypt = EncryptStringUseCase(CryptoService())
        val decrypt = DecryptStringUseCase(CryptoService())
        val decryptedPassword = decrypt(existing.cipheredPassword)
        val dateInfo = DateInfo(
            creationDate = existing.dateInfo.creationDate,
            lastUpdate = LocalDate.now()
        )
        val metadataInfo = PasswordAnalyzer.analyze(decryptedPassword)
        val encryptedNotes = encrypt(newNotes)
        val updatedPassword = PasswordData(
            id = existing.id,
            cipheredPassword = existing.cipheredPassword,
            appInfo = existing.appInfo,
            dateInfo = dateInfo,
            score = SecurityScoreCalculator().calculate(decryptedPassword),
            metadata = metadataInfo,
            cipheredNotes = encryptedNotes
        )

        repository.update(updatedPassword)

        Log.d("NotesChange",
            "Update notes->\n$newNotes"
        )
        return updatedPassword
    }
}