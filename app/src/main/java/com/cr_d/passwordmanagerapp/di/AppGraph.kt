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
import com.cr_d.passwordmanagerapp.ui.screens.password_detail.viewmodel_components.DialogManagerComponent
import com.cr_d.passwordmanagerapp.ui.screens.password_detail.viewmodel_components.EditPasswordManagerComponent
import com.cr_d.passwordmanagerapp.ui.screens.password_detail.viewmodel_components.PasswordManagerComponent
import com.cr_d.passwordmanagerapp.ui.screens.password_detail.viewmodel_components.UiManagerComponent
import com.cr_d.passwordmanagerapp.ui.screens.passwords_list.PasswordListViewModelFactory

object AppGraph {
    // Repositories
    private val passwordRepository by lazy { RoomApplication.getPasswordRepository() }
    private val accountRepository by lazy { RoomApplication.getAccountRepository() }
    // Core
    private val generator by lazy { PasswordGenerator() }
    private val scoreCalculator by lazy { SecurityScoreCalculator() }
    private val calculateSecurityScoreUseCase by lazy { CalculateSecurityScoreUseCase(scoreCalculator) }

    // Password
    private val getAllPasswordsUseCase by lazy { GetAllPasswordsUseCase(passwordRepository) }
    private val generatePasswordUseCase by lazy { GeneratePasswordUseCase(generator) }
    private val createPasswordUseCase by lazy { SavePasswordUseCase(passwordRepository, encryptStringUseCase) }
    private val updatePasswordUseCase by lazy { UpdatePasswordUseCase(passwordRepository, encryptStringUseCase) }
    private val updateNotesUseCase by lazy { UpdateNotesUseCase(passwordRepository, encryptStringUseCase) }
    private val deletePasswordUseCase by lazy { DeletePasswordUseCase(passwordRepository) }

    // Crypto
    private val cryptoService by lazy { CryptoService() }
    private val encryptStringUseCase by lazy { EncryptStringUseCase(cryptoService) }
    private val decryptStringUseCase by lazy { DecryptStringUseCase(cryptoService) }

    // ViewmodelComponents
    private val dialogManagerComponent by lazy { DialogManagerComponent() }
    private val passwordManagerComponent by lazy { PasswordManagerComponent(passwordRepository, deletePasswordUseCase, decryptStringUseCase) }
    private val editManagerComponent by lazy { EditPasswordManagerComponent(decryptStringUseCase) }
    private val uiManagerComponent by lazy { UiManagerComponent() }

    val mainScreenFactory by lazy {
        MainScreenViewModelFactory(passwordRepository)
    }
    val createPasswordFactory by lazy {
        CreatePasswordViewModelFactory(
            generatePasswordUseCase = generatePasswordUseCase,
            scoreCalculator = calculateSecurityScoreUseCase,
            savePasswordUseCase = createPasswordUseCase
        )
    }
    val listPasswordFactory by lazy { PasswordListViewModelFactory(getAllPasswordsUseCase) }
    fun detailPasswordFactory(passwordId: Long) = PasswordDetailViewModelFactory(
        repository = passwordRepository,
        passwordId = passwordId,
        generatePasswordUseCase = generatePasswordUseCase,
        securityScoreCalculator = calculateSecurityScoreUseCase,
        updatePasswordUseCase = updatePasswordUseCase,
        updateNotesUseCase = updateNotesUseCase,
        dialogManager = dialogManagerComponent,
        passwordManager = passwordManagerComponent,
        editManager = editManagerComponent,
        uiManager = uiManagerComponent
    )
}