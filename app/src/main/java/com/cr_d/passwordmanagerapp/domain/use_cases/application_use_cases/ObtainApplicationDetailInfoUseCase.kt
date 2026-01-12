package com.cr_d.passwordmanagerapp.domain.use_cases.application_use_cases

import com.cr_d.passwordmanagerapp.data.dto.ApplicationDetailInfo
import com.cr_d.passwordmanagerapp.data.mapper.toDetail
import com.cr_d.passwordmanagerapp.data.mapper.toUiState
import com.cr_d.passwordmanagerapp.data.repository.interfaces.IPasswordRepository
import com.cr_d.passwordmanagerapp.domain.entities.Application
import com.cr_d.passwordmanagerapp.domain.use_cases.password_use_cases.ObtainPasswordDetailInfoUseCase
import com.cr_d.passwordmanagerapp.domain.use_cases.security_use_cases.DecryptStringUseCase

class ObtainApplicationDetailInfoUseCase (
    private val decrypt: DecryptStringUseCase,
    private val repository: IPasswordRepository,
    private val obtainInfo: ObtainPasswordDetailInfoUseCase
) {
    suspend operator fun invoke(app: Application): ApplicationDetailInfo {
        val decipheredNotes = decrypt(app.cipheredNotes)
        val passwords = repository.findByApplicationId(app.id).map {
            val extraInfo = obtainInfo.invoke(it)
            it.toDetail(extraInfo).toUiState()
        }

        return ApplicationDetailInfo(decipheredNotes, passwords)
    }
}
