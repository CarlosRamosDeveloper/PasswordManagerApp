package com.cr_d.passwordmanagerapp.ui.screens.applications.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.cr_d.passwordmanagerapp.domain.use_cases.application_use_cases.ApplicationParseToUiUseCase

class ApplicationDetailViewModelFactory (
    private val appId: Long,
    private val parser: ApplicationParseToUiUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ApplicationDetailViewModel(
            appId,
            parser
        ) as T
    }
}