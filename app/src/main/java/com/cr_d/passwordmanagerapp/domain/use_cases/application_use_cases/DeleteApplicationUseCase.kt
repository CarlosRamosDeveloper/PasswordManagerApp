package com.cr_d.passwordmanagerapp.domain.use_cases.application_use_cases

import com.cr_d.passwordmanagerapp.data.repository.interfaces.IApplicationRepository

class DeleteApplicationUseCase(
    private val repository: IApplicationRepository
) {
    suspend operator fun invoke(appId: Long) {
        repository.delete(appId)
    }
}