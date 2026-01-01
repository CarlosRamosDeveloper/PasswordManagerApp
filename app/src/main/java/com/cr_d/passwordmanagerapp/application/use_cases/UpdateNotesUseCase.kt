package com.cr_d.passwordmanagerapp.application.use_cases

import android.util.Log
import java.time.LocalDate

import com.cr_d.passwordmanagerapp.application.interfaces.IPasswordRepository
import com.cr_d.passwordmanagerapp.domain.value_objects.DateInfo
import com.cr_d.passwordmanagerapp.domain.value_objects.PasswordData

class UpdateNotesUseCase (
    private val repository: IPasswordRepository,
    private val encrypt: EncryptStringUseCase
) {
    suspend operator fun invoke(id: Long, newNotes: String) : PasswordData{
        val existing = repository.findById(id)
            ?: throw IllegalArgumentException("Password not found")

        val dateInfo = DateInfo(
            creationDate = existing.dateInfo.creationDate,
            lastUpdate = LocalDate.now()
        )
        val encryptedNotes = encrypt(newNotes)
        val updatedPassword = PasswordData(
            id = existing.id,
            cipheredPassword = existing.cipheredPassword,
            appInfo = existing.appInfo,
            dateInfo = dateInfo,
            score = existing.score,
            metadata = existing.metadata,
            cipheredNotes = encryptedNotes
        )

        repository.update(updatedPassword)

        Log.d("NotesChange",
            "Update notes->\n$newNotes"
        )
        return updatedPassword
    }
}