package com.cr_d.passwordmanagerapp.domain.use_cases.application_use_cases

import com.cr_d.passwordmanagerapp.data.repository.interfaces.IApplicationRepository
import com.cr_d.passwordmanagerapp.domain.entities.Application
import com.cr_d.passwordmanagerapp.domain.use_cases.security_use_cases.EncryptStringUseCase
import com.cr_d.passwordmanagerapp.ui.model.ApplicationUiState

class SaveApplicationUseCase(
    private val repository: IApplicationRepository,
    private val encrypt: EncryptStringUseCase
) {
    // TODO: Crear dto para la creación de apps
    suspend operator fun invoke(app: ApplicationUiState): Application{
        val encryptedNotes = encrypt(app.notes)
        val newApp = Application(
            id = 0,
            appName = app.applicationName,
            appUrl = app.applicationUrl,
            cipheredNotes = encryptedNotes
        )

        repository.save(newApp)

        return newApp
    }
}