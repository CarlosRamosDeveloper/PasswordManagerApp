package com.cr_d.passwordmanagerapp.di

import com.cr_d.passwordmanagerapp.application.database.RoomApplication
import com.cr_d.passwordmanagerapp.application.use_cases.CalculateSecurityScoreUseCase
import com.cr_d.passwordmanagerapp.application.use_cases.DecryptStringUseCase
import com.cr_d.passwordmanagerapp.application.use_cases.DeletePasswordUseCase
import com.cr_d.passwordmanagerapp.application.use_cases.EncryptStringUseCase
import com.cr_d.passwordmanagerapp.application.use_cases.GeneratePasswordUseCase
import com.cr_d.passwordmanagerapp.application.use_cases.GetAllPasswordsUseCase
import com.cr_d.passwordmanagerapp.application.use_cases.ObtainPasswordDetailInfoUseCase
import com.cr_d.passwordmanagerapp.application.use_cases.SavePasswordUseCase
import com.cr_d.passwordmanagerapp.application.use_cases.UpdateNotesUseCase
import com.cr_d.passwordmanagerapp.application.use_cases.UpdatePasswordUseCase
import com.cr_d.passwordmanagerapp.data.crypto.CryptoService
import com.cr_d.passwordmanagerapp.domain.entities.PasswordGenerator
import com.cr_d.passwordmanagerapp.domain.entities.SecurityScoreCalculator
import com.cr_d.passwordmanagerapp.ui.screens.passwords.create.CreatePasswordViewModelFactory
import com.cr_d.passwordmanagerapp.ui.screens.main_screen.MainScreenViewModelFactory
import com.cr_d.passwordmanagerapp.ui.screens.main_screen.viewmodel_components.MainAccountManagerComponent
import com.cr_d.passwordmanagerapp.ui.screens.main_screen.viewmodel_components.MainApplicationManagerComponent
import com.cr_d.passwordmanagerapp.ui.screens.main_screen.viewmodel_components.MainDialogManagerComponent
import com.cr_d.passwordmanagerapp.ui.screens.main_screen.viewmodel_components.MainPasswordManagerComponent
import com.cr_d.passwordmanagerapp.ui.screens.passwords.detail.PasswordDetailViewModelFactory
import com.cr_d.passwordmanagerapp.ui.screens.passwords.detail.viewmodel_components.DialogManagerComponent
import com.cr_d.passwordmanagerapp.ui.screens.passwords.detail.viewmodel_components.EditPasswordManagerComponent
import com.cr_d.passwordmanagerapp.ui.screens.passwords.detail.viewmodel_components.PasswordManagerComponent
import com.cr_d.passwordmanagerapp.ui.screens.passwords.detail.viewmodel_components.UiManagerComponent
import com.cr_d.passwordmanagerapp.ui.screens.passwords.list.PasswordListViewModelFactory

object AppGraph {
    // Repositories
    private val passwordRepository by lazy { RoomApplication.getPasswordRepository() }
    private val accountRepository by lazy { RoomApplication.getAccountRepository() }
    private val applicationRepository by lazy { RoomApplication.getApplicationRepository() }

    // Core
    private val generator by lazy { PasswordGenerator() }
    private val scoreCalculator by lazy { SecurityScoreCalculator() }
    private val calculateSecurityScoreUseCase by lazy { CalculateSecurityScoreUseCase(scoreCalculator) }
    private val cryptoService by lazy { CryptoService() }

    // Password UseCases
    private val getAllPasswordsUseCase by lazy { GetAllPasswordsUseCase(passwordRepository) }
    private val generatePasswordUseCase by lazy { GeneratePasswordUseCase(generator) }
    private val createPasswordUseCase by lazy { SavePasswordUseCase(passwordRepository, encryptStringUseCase) }
    private val updatePasswordUseCase by lazy { UpdatePasswordUseCase(passwordRepository, encryptStringUseCase) }
    private val updateNotesUseCase by lazy { UpdateNotesUseCase(passwordRepository, encryptStringUseCase) }
    private val deletePasswordUseCase by lazy { DeletePasswordUseCase(passwordRepository) }
    val obtainPasswordDetailInfoUseCase by lazy {
        ObtainPasswordDetailInfoUseCase(
            appRepository = applicationRepository,
            accRepository = accountRepository,
            decrypt = decryptStringUseCase,
            scoreCalculator = calculateSecurityScoreUseCase
        )
    }

    // Crypto UseCases
    private val encryptStringUseCase by lazy { EncryptStringUseCase(cryptoService) }
    private val decryptStringUseCase by lazy { DecryptStringUseCase(cryptoService) }

    // ViewmodelComponents
    private val passwordDialogManagerComponent by lazy { DialogManagerComponent() }
    private val passwordManagerComponent by lazy { PasswordManagerComponent(passwordRepository, deletePasswordUseCase, decryptStringUseCase) }
    private val editManagerComponent by lazy { EditPasswordManagerComponent(decryptStringUseCase) }
    private val passwordUiManagerComponent by lazy { UiManagerComponent() }
    private val mainDialogManagerComponent by lazy { MainDialogManagerComponent() }
    private val mainPasswordManagerComponent by lazy { MainPasswordManagerComponent(passwordRepository) }
    private val mainAccountManagerComponent by lazy { MainAccountManagerComponent(accountRepository) }
    private val mainApplicationManagerComponent by lazy { MainApplicationManagerComponent(applicationRepository) }

    val mainScreenFactory by lazy {
        MainScreenViewModelFactory(
            dialogManager = mainDialogManagerComponent,
            passwordManager = mainPasswordManagerComponent,
            accountManager = mainAccountManagerComponent,
            appManager = mainApplicationManagerComponent
        )
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
        dialogManager = passwordDialogManagerComponent,
        passwordManager = passwordManagerComponent,
        editManager = editManagerComponent,
        uiManager = passwordUiManagerComponent
    )
}
