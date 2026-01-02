package com.cr_d.passwordmanagerapp.application.use_cases

import com.cr_d.passwordmanagerapp.application.interfaces.IAccountRepository
import com.cr_d.passwordmanagerapp.application.interfaces.IApplicationRepository
import com.cr_d.passwordmanagerapp.data.crypto.EncryptedPayload
import com.cr_d.passwordmanagerapp.domain.entities.PasswordAnalyzer
import com.cr_d.passwordmanagerapp.domain.value_objects.PasswordDetailInfo
import com.cr_d.passwordmanagerapp.ui.dto.PasswordAccountInfoDto
import com.cr_d.passwordmanagerapp.ui.dto.PasswordAppInfoDto

class ObtainPasswordDetailInfoUseCase (
    private val appRepository: IApplicationRepository,
    private val accRepository: IAccountRepository,
    private val decrypt: DecryptStringUseCase
) {
    fun invoke(cipheredPassword: EncryptedPayload) : PasswordDetailInfo {
        val password = decrypt(cipheredPassword)

        val extraInfo = PasswordDetailInfo(
            appData = PasswordAppInfoDto(
                appName = "Test",
                appUrl = "Test.com"
            ),
            accountData = PasswordAccountInfoDto(
                account = "asd"
            ),
            metadata = PasswordAnalyzer.analyze(password),
            score = 1.0
        )

        return extraInfo
    }
}