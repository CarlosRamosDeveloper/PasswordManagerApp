package com.cr_d.passwordmanagerapp.domain.use_cases.application_use_cases

import com.cr_d.passwordmanagerapp.data.mapper.toDetail
import com.cr_d.passwordmanagerapp.data.mapper.toUiState
import com.cr_d.passwordmanagerapp.data.repository.interfaces.IApplicationRepository
import com.cr_d.passwordmanagerapp.ui.model.ApplicationUiState

class ApplicationParseToUiUseCase (
    private val repository: IApplicationRepository,
    private val obtainData: ObtainApplicationDetailInfoUseCase
) {
    suspend operator fun invoke(appId: Long): ApplicationUiState? {
        val application = repository.findById(appId) ?: return null
        val extraData = obtainData.invoke(application)

        return application.toDetail().toUiState(extraData)
    }
}