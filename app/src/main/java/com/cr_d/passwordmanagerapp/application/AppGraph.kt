package com.cr_d.passwordmanagerapp.application

import android.content.Context
import androidx.room.Room

import com.cr_d.passwordmanagerapp.data.crypto.CryptoService
import com.cr_d.passwordmanagerapp.data.daos.AccountDao
import com.cr_d.passwordmanagerapp.data.daos.ApplicationDao
import com.cr_d.passwordmanagerapp.data.daos.PasswordDao
import com.cr_d.passwordmanagerapp.data.database.AppDatabase
import com.cr_d.passwordmanagerapp.data.repository.in_memory.InMemoryPasswordRepository
import com.cr_d.passwordmanagerapp.data.repository.room.RoomAccountRepository
import com.cr_d.passwordmanagerapp.data.repository.room.RoomApplicationRepository
import com.cr_d.passwordmanagerapp.data.repository.room.RoomPasswordRepository
import com.cr_d.passwordmanagerapp.domain.services.PasswordAnalyzer
import com.cr_d.passwordmanagerapp.domain.use_cases.CalculateSecurityScoreUseCase
import com.cr_d.passwordmanagerapp.domain.use_cases.DecryptStringUseCase
import com.cr_d.passwordmanagerapp.domain.use_cases.DeletePasswordUseCase
import com.cr_d.passwordmanagerapp.domain.use_cases.EncryptStringUseCase
import com.cr_d.passwordmanagerapp.domain.use_cases.GeneratePasswordUseCase
import com.cr_d.passwordmanagerapp.domain.use_cases.GetAllPasswordsUseCase
import com.cr_d.passwordmanagerapp.domain.use_cases.ObtainPasswordDetailInfoUseCase
import com.cr_d.passwordmanagerapp.domain.use_cases.SavePasswordUseCase
import com.cr_d.passwordmanagerapp.domain.use_cases.UpdateNotesUseCase
import com.cr_d.passwordmanagerapp.domain.use_cases.UpdatePasswordUseCase
import com.cr_d.passwordmanagerapp.domain.services.PasswordGenerator
import com.cr_d.passwordmanagerapp.domain.services.SecurityScoreCalculator
import com.cr_d.passwordmanagerapp.domain.use_cases.AnalyzePasswordUseCase
import com.cr_d.passwordmanagerapp.domain.use_cases.GetAllAccountsUseCase
import com.cr_d.passwordmanagerapp.ui.screens.accounts.list.AccountListViewModel
import com.cr_d.passwordmanagerapp.ui.screens.accounts.list.AccountListViewModelFactory
import com.cr_d.passwordmanagerapp.ui.screens.main_screen.MainScreenViewModelFactory
import com.cr_d.passwordmanagerapp.ui.screens.main_screen.viewmodel_components.MainAccountManagerComponent
import com.cr_d.passwordmanagerapp.ui.screens.main_screen.viewmodel_components.MainApplicationManagerComponent
import com.cr_d.passwordmanagerapp.ui.screens.main_screen.viewmodel_components.MainDialogManagerComponent
import com.cr_d.passwordmanagerapp.ui.screens.main_screen.viewmodel_components.MainPasswordManagerComponent
import com.cr_d.passwordmanagerapp.ui.screens.passwords.create.CreatePasswordViewModelFactory
import com.cr_d.passwordmanagerapp.ui.screens.passwords.detail.PasswordDetailViewModelFactory
import com.cr_d.passwordmanagerapp.ui.screens.passwords.detail.viewmodel_components.DialogManagerComponent
import com.cr_d.passwordmanagerapp.ui.screens.passwords.detail.viewmodel_components.EditPasswordManagerComponent
import com.cr_d.passwordmanagerapp.ui.screens.passwords.detail.viewmodel_components.PasswordManagerComponent
import com.cr_d.passwordmanagerapp.ui.screens.passwords.detail.viewmodel_components.UiManagerComponent
import com.cr_d.passwordmanagerapp.ui.screens.passwords.list.PasswordListViewModelFactory

class AppGraph(
    private val appContext: Context
) {
    private val database: AppDatabase by lazy {
        Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "password-database"
        )
            .fallbackToDestructiveMigration(true)
            .build()
    }

    // DAOs
    val passwordDao: PasswordDao by lazy { database.passwordDao() }
    val accountDao: AccountDao by lazy { database.accountDao() }
    val applicationDao: ApplicationDao by lazy { database.appDao() }

    // Repositories
    private val inMemoryPasswordRepository by lazy { InMemoryPasswordRepository(obtainPasswordDetailInfoUseCase) }
    private val passwordRepository by lazy { RoomPasswordRepository(passwordDao, obtainPasswordDetailInfoUseCase) }
    private val accountRepository by lazy { RoomAccountRepository(accountDao) }
    private val applicationRepository by lazy { RoomApplicationRepository(applicationDao) }

    // Core
    private val passwordAnalyzer by lazy { PasswordAnalyzer() }
    private val generator by lazy { PasswordGenerator() }
    private val scoreCalculator by lazy { SecurityScoreCalculator(analyzePasswordUseCase) }
    private val calculateSecurityScoreUseCase by lazy {
        CalculateSecurityScoreUseCase(
            scoreCalculator
        )
    }
    private val cryptoService by lazy { CryptoService() }

    // Password UseCases
    private val analyzePasswordUseCase by lazy { AnalyzePasswordUseCase(passwordAnalyzer) }
    private val getAllPasswordsUseCase by lazy { GetAllPasswordsUseCase(passwordRepository) }
    private val generatePasswordUseCase by lazy { GeneratePasswordUseCase(generator) }
    private val createPasswordUseCase by lazy {
        SavePasswordUseCase(
            repository = passwordRepository,
            encrypt = encryptStringUseCase,
            analyzer = analyzePasswordUseCase,
            scoreCalculator = calculateSecurityScoreUseCase
        )
    }
    private val updatePasswordUseCase by lazy {
        UpdatePasswordUseCase(
            repository = passwordRepository,
            encrypt = encryptStringUseCase,
            analyzer = analyzePasswordUseCase,
            scoreCalculator = calculateSecurityScoreUseCase
        )
    }
    private val updateNotesUseCase by lazy {
        UpdateNotesUseCase(
            passwordRepository,
            encryptStringUseCase
        )
    }
    private val deletePasswordUseCase by lazy { DeletePasswordUseCase(passwordRepository) }
    val obtainPasswordDetailInfoUseCase by lazy {
        ObtainPasswordDetailInfoUseCase(
            appRepository = applicationRepository,
            accRepository = accountRepository,
            decrypt = decryptStringUseCase,
            scoreCalculator = calculateSecurityScoreUseCase,
            analyzer = analyzePasswordUseCase
        )
    }

    // Account UseCases
    private val getAllAccountsUseCase by lazy { GetAllAccountsUseCase(accountRepository) }

    // Crypto UseCases
    private val encryptStringUseCase by lazy { EncryptStringUseCase(cryptoService) }
    private val decryptStringUseCase by lazy { DecryptStringUseCase(cryptoService) }

    // ViewmodelComponents
    private val passwordDialogManagerComponent by lazy { DialogManagerComponent() }
    private val passwordManagerComponent by lazy {
        PasswordManagerComponent(
            passwordRepository,
            deletePasswordUseCase,
            decryptStringUseCase
        )
    }
    private val editManagerComponent by lazy { EditPasswordManagerComponent(decryptStringUseCase) }
    private val passwordUiManagerComponent by lazy { UiManagerComponent() }
    private val mainDialogManagerComponent by lazy { MainDialogManagerComponent() }
    private val mainPasswordManagerComponent by lazy {
        MainPasswordManagerComponent(
            passwordRepository
        )
    }
    private val mainAccountManagerComponent by lazy { MainAccountManagerComponent(accountRepository) }
    private val mainApplicationManagerComponent by lazy {
        MainApplicationManagerComponent(
            applicationRepository
        )
    }

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

    val accountListFactory by lazy { AccountListViewModelFactory(getAllAccountsUseCase, decryptStringUseCase) }
}