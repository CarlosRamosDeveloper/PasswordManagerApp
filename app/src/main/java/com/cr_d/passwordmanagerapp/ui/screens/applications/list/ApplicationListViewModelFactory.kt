package com.cr_d.passwordmanagerapp.ui.screens.applications.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.cr_d.passwordmanagerapp.domain.use_cases.application_use_cases.GetAllApplicationsUseCase

class ApplicationListViewModelFactory (
    private val getAllApplicationsUseCase: GetAllApplicationsUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ApplicationListViewModel(
            getAllApplicationsUseCase
        ) as T
    }
}