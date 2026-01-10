package com.cr_d.passwordmanagerapp.application

import android.content.Context
import androidx.room.Room
import kotlin.getValue

import com.cr_d.passwordmanagerapp.data.crypto.CryptoService
import com.cr_d.passwordmanagerapp.data.daos.AccountDao
import com.cr_d.passwordmanagerapp.data.daos.ApplicationDao
import com.cr_d.passwordmanagerapp.data.daos.PasswordDao
import com.cr_d.passwordmanagerapp.data.database.AppDatabase
//import com.cr_d.passwordmanagerapp.data.repository.in_memory.InMemoryPasswordRepository
import com.cr_d.passwordmanagerapp.data.repository.interfaces.IAccountRepository
import com.cr_d.passwordmanagerapp.data.repository.interfaces.IApplicationRepository
import com.cr_d.passwordmanagerapp.data.repository.interfaces.IPasswordRepository
import com.cr_d.passwordmanagerapp.data.repository.room.RoomAccountRepository
import com.cr_d.passwordmanagerapp.data.repository.room.RoomApplicationRepository
import com.cr_d.passwordmanagerapp.data.repository.room.RoomPasswordRepository
import com.cr_d.passwordmanagerapp.domain.services.PasswordAnalyzer
import com.cr_d.passwordmanagerapp.domain.use_cases.password_use_cases.CalculateSecurityScoreUseCase
import com.cr_d.passwordmanagerapp.domain.use_cases.security_use_cases.DecryptStringUseCase
import com.cr_d.passwordmanagerapp.domain.use_cases.password_use_cases.DeletePasswordUseCase
import com.cr_d.passwordmanagerapp.domain.use_cases.security_use_cases.EncryptStringUseCase
import com.cr_d.passwordmanagerapp.domain.use_cases.password_use_cases.GeneratePasswordUseCase
import com.cr_d.passwordmanagerapp.domain.use_cases.password_use_cases.ObtainPasswordDetailInfoUseCase
import com.cr_d.passwordmanagerapp.domain.use_cases.password_use_cases.SavePasswordUseCase
import com.cr_d.passwordmanagerapp.domain.use_cases.password_use_cases.UpdateNotesUseCase
import com.cr_d.passwordmanagerapp.domain.use_cases.password_use_cases.UpdatePasswordUseCase
import com.cr_d.passwordmanagerapp.domain.services.PasswordGenerator
import com.cr_d.passwordmanagerapp.domain.services.SecurityScoreCalculator
import com.cr_d.passwordmanagerapp.domain.use_cases.account_use_cases.AccountParseToUiUseCase
import com.cr_d.passwordmanagerapp.domain.use_cases.account_use_cases.DeleteAccountUseCase
import com.cr_d.passwordmanagerapp.domain.use_cases.password_use_cases.AnalyzePasswordUseCase
import com.cr_d.passwordmanagerapp.domain.use_cases.account_use_cases.GetAllAccountsUseCase
import com.cr_d.passwordmanagerapp.domain.use_cases.password_use_cases.GetAllPasswordDetailUseCase
import com.cr_d.passwordmanagerapp.domain.use_cases.account_use_cases.ObtainAccountDetailInfoUseCase
import com.cr_d.passwordmanagerapp.domain.use_cases.account_use_cases.SaveAccountUseCase
import com.cr_d.passwordmanagerapp.domain.use_cases.application_use_cases.ApplicationParseToUiUseCase
import com.cr_d.passwordmanagerapp.domain.use_cases.application_use_cases.GetAllApplicationsUseCase
import com.cr_d.passwordmanagerapp.domain.use_cases.application_use_cases.ObtainApplicationDetailInfoUseCase
import com.cr_d.passwordmanagerapp.domain.use_cases.application_use_cases.SaveApplicationUseCase
import com.cr_d.passwordmanagerapp.ui.screens.accounts.create.CreateAccountViewModelFactory
import com.cr_d.passwordmanagerapp.ui.screens.accounts.detail.AccountDetailViewModelFactory
import com.cr_d.passwordmanagerapp.ui.screens.accounts.list.AccountListViewModelFactory
import com.cr_d.passwordmanagerapp.ui.screens.applications.create.CreateApplicationViewModelFactory
import com.cr_d.passwordmanagerapp.ui.screens.applications.detail.ApplicationDetailViewModelFactory
import com.cr_d.passwordmanagerapp.ui.screens.applications.list.ApplicationListViewModelFactory
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
    //private val inMemoryPasswordRepository by lazy { InMemoryPasswordRepository(obtainPasswordDetailInfoUseCase) }
    private val passwordRepository: IPasswordRepository by lazy { RoomPasswordRepository(passwordDao) }
    private val accountRepository: IAccountRepository by lazy { RoomAccountRepository(accountDao) }
    private val applicationRepository: IApplicationRepository by lazy { RoomApplicationRepository(applicationDao) }

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
    private val getAllPasswordDetailUseCase by lazy { GetAllPasswordDetailUseCase(passwordRepository, obtainPasswordDetailInfoUseCase) }
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
            repository = passwordRepository,
            encrypt = encryptStringUseCase,
            obtainData = obtainPasswordDetailInfoUseCase
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
    private val getAllAccountsUseCase by lazy {
        GetAllAccountsUseCase(
            repository = accountRepository,
            getInfo = obtainAccountDetailInfoUseCase
        )
    }
    private val obtainAccountDetailInfoUseCase by lazy { ObtainAccountDetailInfoUseCase(passwordRepository, decryptStringUseCase, obtainPasswordDetailInfoUseCase) }
    private val accountParseToUiUseCase by lazy {
        AccountParseToUiUseCase(
            repository = accountRepository,
            obtainData = obtainAccountDetailInfoUseCase,
        )
    }
    private val saveAccountUseCase by lazy { SaveAccountUseCase(accountRepository, encryptStringUseCase) }
    private val deleteAccountUseCase by lazy { DeleteAccountUseCase(accountRepository) }

    // Application UseCases
    private val applicationParseToUiUseCase by lazy { ApplicationParseToUiUseCase(applicationRepository,obtainApplicationDetailInfoUseCase) }
    private val getAllApplicationsUseCase by lazy {
        GetAllApplicationsUseCase(
            repository = applicationRepository,
            obtainData = obtainApplicationDetailInfoUseCase
        )
    }
    private val obtainApplicationDetailInfoUseCase by lazy { ObtainApplicationDetailInfoUseCase(
        decrypt = decryptStringUseCase,
        repository = passwordRepository,
        obtainInfo = obtainPasswordDetailInfoUseCase
    ) }
    private val saveApplicationUseCase by lazy { SaveApplicationUseCase(
        repository = applicationRepository,
        encrypt = encryptStringUseCase
    ) }

    // Crypto UseCases
    private val encryptStringUseCase by lazy { EncryptStringUseCase(cryptoService) }
    private val decryptStringUseCase by lazy { DecryptStringUseCase(cryptoService) }

    // ViewmodelComponents
    private val passwordDialogManagerComponent by lazy { DialogManagerComponent() }
    private val passwordManagerComponent by lazy {
        PasswordManagerComponent(
            repository = passwordRepository,
            deletePassword = deletePasswordUseCase,
            decrypt = decryptStringUseCase,
            obtainData = obtainPasswordDetailInfoUseCase
        )
    }
    private val editManagerComponent by lazy { EditPasswordManagerComponent(decryptStringUseCase, obtainPasswordDetailInfoUseCase) }
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
    val listPasswordFactory by lazy { PasswordListViewModelFactory(getAllPasswordDetailUseCase) }
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
    val accountListFactory by lazy { AccountListViewModelFactory(getAllAccountsUseCase) }
    fun accountDetailFactory (accountId: Long) = AccountDetailViewModelFactory(
        accountId = accountId,
        accountParseToUiUseCase = accountParseToUiUseCase,
        delete = deleteAccountUseCase
    )
    val createApplicationFactory by lazy { CreateApplicationViewModelFactory(saveApplicationUseCase) }
    val applicationListFactory by lazy { ApplicationListViewModelFactory(getAllApplicationsUseCase) }
    fun applicationDetailFactory (appId: Long) = ApplicationDetailViewModelFactory(
        appId = appId,
        parser = applicationParseToUiUseCase
    )
    val createAccountFactory by lazy { CreateAccountViewModelFactory(saveAccountUseCase) }
}