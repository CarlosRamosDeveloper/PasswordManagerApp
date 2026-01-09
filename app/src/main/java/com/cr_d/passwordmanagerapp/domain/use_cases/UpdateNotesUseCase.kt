package com.cr_d.passwordmanagerapp.domain.use_cases

import android.util.Log
import java.time.LocalDate

import com.cr_d.passwordmanagerapp.data.dto.PasswordDetail
import com.cr_d.passwordmanagerapp.data.mapper.toDomain
import com.cr_d.passwordmanagerapp.data.repository.interfaces.IPasswordRepository
import com.cr_d.passwordmanagerapp.domain.value_objects.DateInfo

class UpdateNotesUseCase (
    private val repository: IPasswordRepository,
    private val encrypt: EncryptStringUseCase,
    private val obtainData: ObtainPasswordDetailInfoUseCase
) {
    suspend operator fun invoke(id: Long, newNotes: String) : PasswordDetail{
        val existing = repository.findById(id)
            ?: throw IllegalArgumentException("Password not found")

        val extraData = obtainData.invoke(existing)

        val dateInfo = DateInfo(
            creationDate = existing.dateInfo.creationDate,
            lastUpdate = LocalDate.now()
        )
        val encryptedNotes = encrypt(newNotes)
        val updatedPassword = PasswordDetail(
            id = existing.id,
            cipheredPassword = existing.cipheredPassword,
            appData = extraData.appData,
            accountData = extraData.accountData,
            dateInfo = dateInfo,
            score = extraData.score,
            metadata = extraData.metadata,
            cipheredNotes = encryptedNotes
        )

        //TODO: Check
        repository.update(updatedPassword.toDomain())

        Log.d("NotesChange",
            "Update notes->\n$newNotes"
        )
        return updatedPassword
    }
}