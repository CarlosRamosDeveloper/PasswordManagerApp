package com.cr_d.passwordmanagerapp.domain.use_cases.application_use_cases

import com.cr_d.passwordmanagerapp.data.mapper.toDetail
import com.cr_d.passwordmanagerapp.data.mapper.toUiState
import com.cr_d.passwordmanagerapp.data.repository.interfaces.IApplicationRepository
import com.cr_d.passwordmanagerapp.ui.model.ApplicationUiState

class GetAllApplicationsUseCase(
    private val repository: IApplicationRepository,
    private val obtainData: ObtainApplicationDetailInfoUseCase
) {
    suspend operator fun invoke(): List<ApplicationUiState>{
        return repository.findAll().map {
            val extraData = obtainData.invoke(it)
            it.toDetail().toUiState(extraData)
        }
    }
}
