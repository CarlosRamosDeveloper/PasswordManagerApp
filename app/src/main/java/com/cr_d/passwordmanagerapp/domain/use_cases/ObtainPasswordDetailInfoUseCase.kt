package com.cr_d.passwordmanagerapp.domain.use_cases

import com.cr_d.passwordmanagerapp.data.repository.IAccountRepository
import com.cr_d.passwordmanagerapp.data.repository.IApplicationRepository
import com.cr_d.passwordmanagerapp.domain.services.PasswordAnalyzer
import com.cr_d.passwordmanagerapp.domain.value_objects.Password
import com.cr_d.passwordmanagerapp.domain.value_objects.PasswordDetailInfo
import com.cr_d.passwordmanagerapp.ui.dto.PasswordAccountInfoDto
import com.cr_d.passwordmanagerapp.ui.dto.PasswordAppInfoDto

class ObtainPasswordDetailInfoUseCase (
    private val appRepository: IApplicationRepository,
    private val accRepository: IAccountRepository,
    private val decrypt: DecryptStringUseCase,
    private val scoreCalculator: CalculateSecurityScoreUseCase
) {
    suspend fun invoke(password: Password) : PasswordDetailInfo {
        val plainPassword = decrypt(password.cipheredPassword)
        val app = appRepository.findById(password.appId)
        val cipheredAccount = accRepository.findById(password.accountId)
        val accountName = decrypt(cipheredAccount!!.cipheredAccount)
        val score = scoreCalculator(plainPassword)

        val extraInfo = PasswordDetailInfo(
            appData = PasswordAppInfoDto(
                appName = app!!.appName,
                appUrl = app.appUrl!!
            ),
            accountData = PasswordAccountInfoDto(
                account = accountName
            ),
            metadata = PasswordAnalyzer.analyze(plainPassword),
            score = score
        )

        return extraInfo
    }
}