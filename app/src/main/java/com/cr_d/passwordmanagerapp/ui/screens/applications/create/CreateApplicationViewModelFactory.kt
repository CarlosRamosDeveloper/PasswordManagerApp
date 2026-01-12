package com.cr_d.passwordmanagerapp.ui.screens.applications.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.cr_d.passwordmanagerapp.domain.use_cases.application_use_cases.SaveApplicationUseCase

class CreateApplicationViewModelFactory (
    private val save: SaveApplicationUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CreateApplicationViewModel(
            save,
        ) as T
    }
}