package com.cr_d.passwordmanagerapp.ui.screens.applications.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.cr_d.passwordmanagerapp.domain.use_cases.application_use_cases.ApplicationParseToUiUseCase
import com.cr_d.passwordmanagerapp.domain.use_cases.application_use_cases.DeleteApplicationUseCase

class ApplicationDetailViewModelFactory (
    private val appId: Long,
    private val parser: ApplicationParseToUiUseCase,
    private val delete: DeleteApplicationUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ApplicationDetailViewModel(
            appId,
            parser,
            delete
        ) as T
    }
}