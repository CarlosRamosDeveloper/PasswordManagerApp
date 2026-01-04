package com.cr_d.passwordmanagerapp.domain.use_cases

import com.cr_d.passwordmanagerapp.data.dto.PasswordDetailInfo
import com.cr_d.passwordmanagerapp.data.dto.PasswordAccountInfoDto
import com.cr_d.passwordmanagerapp.data.dto.PasswordAppInfoDto
import com.cr_d.passwordmanagerapp.data.repository.interfaces.IAccountRepository
import com.cr_d.passwordmanagerapp.data.repository.interfaces.IApplicationRepository
import com.cr_d.passwordmanagerapp.domain.entities.Password
import com.cr_d.passwordmanagerapp.domain.services.PasswordAnalyzer

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