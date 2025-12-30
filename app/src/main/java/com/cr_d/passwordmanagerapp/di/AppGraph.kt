package com.cr_d.passwordmanagerapp.di

import com.cr_d.passwordmanagerapp.application.database.RoomApplication
import com.cr_d.passwordmanagerapp.application.use_cases.CalculateSecurityScoreUseCase
import com.cr_d.passwordmanagerapp.application.use_cases.DecryptStringUseCase
import com.cr_d.passwordmanagerapp.application.use_cases.DeletePasswordUseCase
import com.cr_d.passwordmanagerapp.application.use_cases.EncryptStringUseCase
import com.cr_d.passwordmanagerapp.application.use_cases.GeneratePasswordUseCase
import com.cr_d.passwordmanagerapp.application.use_cases.GetAllPasswordsUseCase
import com.cr_d.passwordmanagerapp.application.use_cases.SavePasswordUseCase
import com.cr_d.passwordmanagerapp.application.use_cases.UpdateNotesUseCase
import com.cr_d.passwordmanagerapp.application.use_cases.UpdatePasswordUseCase
import com.cr_d.passwordmanagerapp.data.crypto.CryptoService
import com.cr_d.passwordmanagerapp.domain.entities.PasswordGenerator
import com.cr_d.passwordmanagerapp.domain.entities.SecurityScoreCalculator
import com.cr_d.passwordmanagerapp.ui.screens.create_password.CreatePasswordViewModelFactory
import com.cr_d.passwordmanagerapp.ui.screens.main_screen.MainScreenViewModelFactory
import com.cr_d.passwordmanagerapp.ui.screens.password_detail.PasswordDetailViewModelFactory
import com.cr_d.passwordmanagerapp.ui.screens.passwords_list.PasswordListViewModelFactory

object AppGraph {
    private val repository by lazy { RoomApplication.getRepository() }
    private val generator by lazy { PasswordGenerator() }
    private val generatePasswordUseCase by lazy { GeneratePasswordUseCase(generator) }
    private val scoreCalculator by lazy { SecurityScoreCalculator() }
    private val calculator by lazy { CalculateSecurityScoreUseCase(scoreCalculator) }
    private val createPasswordUseCase by lazy { SavePasswordUseCase(repository) }
    private val updatePasswordUseCase by lazy { UpdatePasswordUseCase(repository) }
    private val updateNotesUseCase by lazy { UpdateNotesUseCase(repository) }
    private val deletePasswordUseCase by lazy { DeletePasswordUseCase(repository) }
    private val cryptoService by lazy { CryptoService() }
    private val encryptStringUseCase by lazy { EncryptStringUseCase(cryptoService) }
    private val decryptStringUseCase by lazy { DecryptStringUseCase(cryptoService) }

    val mainScreenFactory by lazy {
        MainScreenViewModelFactory(repository)
    }
    val createPasswordFactory by lazy {
        CreatePasswordViewModelFactory(
            generatePasswordUseCase = generatePasswordUseCase,
            scoreCalculator = calculator,
            savePasswordUseCase = createPasswordUseCase
        )
    }
    val listPasswordFactory by lazy {
        PasswordListViewModelFactory(
            getAllPasswordsUseCase = GetAllPasswordsUseCase(repository)
        )
    }
    fun detailPasswordFactory(passwordId: Long) = PasswordDetailViewModelFactory(
        repository = repository,
        passwordId = passwordId,
        generatePasswordUseCase = generatePasswordUseCase,
        securityScoreCalculator = calculator,
        updatePasswordUseCase = updatePasswordUseCase,
        updateNotesUseCase = updateNotesUseCase,
        deletePasswordUseCase = deletePasswordUseCase,
        decrypt = decryptStringUseCase
    )
}