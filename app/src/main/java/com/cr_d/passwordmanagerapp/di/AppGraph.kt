package com.cr_d.passwordmanagerapp.di

import com.cr_d.passwordmanagerapp.application.database.RoomApplication
import com.cr_d.passwordmanagerapp.application.use_cases.CalculateSecurityScoreUseCase
import com.cr_d.passwordmanagerapp.application.use_cases.DecryptStringUseCase
import com.cr_d.passwordmanagerapp.application.use_cases.DeletePasswordUseCase
import com.cr_d.passwordmanagerapp.application.use_cases.GeneratePasswordUseCase
import com.cr_d.passwordmanagerapp.application.use_cases.GetAllPasswordsUseCase
import com.cr_d.passwordmanagerapp.application.use_cases.SavePasswordUseCase
import com.cr_d.passwordmanagerapp.application.use_cases.UpdatePasswordUseCase
import com.cr_d.passwordmanagerapp.data.crypto.CryptoService
import com.cr_d.passwordmanagerapp.domain.entities.PasswordGenerator
import com.cr_d.passwordmanagerapp.domain.entities.SecurityScoreCalculator
import com.cr_d.passwordmanagerapp.ui.screens.create_password.CreatePasswordViewModelFactory
import com.cr_d.passwordmanagerapp.ui.screens.main_screen.MainScreenViewModelFactory
import com.cr_d.passwordmanagerapp.ui.screens.password_detail.PasswordDetailViewModelFactory
import com.cr_d.passwordmanagerapp.ui.screens.passwords_list.PasswordListViewModelFactory

object AppGraph {
    private var repository = RoomApplication.getRepository()
    private val generator = PasswordGenerator()
    private val generatePasswordUseCase = GeneratePasswordUseCase(generator)
    private val scoreCalculator = SecurityScoreCalculator()
    private val calculator = CalculateSecurityScoreUseCase(scoreCalculator)
    private val createPasswordUseCase = SavePasswordUseCase(repository)

    fun mainScreenFactory() = MainScreenViewModelFactory(repository)
    fun createPasswordFactory() =
        CreatePasswordViewModelFactory(
            generatePasswordUseCase = generatePasswordUseCase,
            scoreCalculator = calculator,
            savePasswordUseCase = createPasswordUseCase)
    fun listPasswordFactory() =
        PasswordListViewModelFactory(
            getAllPasswordsUseCase = GetAllPasswordsUseCase(repository)
        )
    fun detailPasswordFactory(passwordId: Long) = PasswordDetailViewModelFactory(
        repository = repository,
        passwordId = passwordId,
        generatePasswordUseCase = generatePasswordUseCase,
        securityScoreCalculator = calculator,
        updatePasswordUseCase = UpdatePasswordUseCase(repository),
        deletePasswordUseCase = DeletePasswordUseCase(repository),
        decrypt = DecryptStringUseCase(CryptoService())
    )
}