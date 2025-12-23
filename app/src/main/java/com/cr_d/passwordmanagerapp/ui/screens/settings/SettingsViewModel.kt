package com.cr_d.passwordmanagerapp.ui.screens.settings

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

import com.cr_d.passwordmanagerapp.ui.models.DateFormatOption
import kotlinx.coroutines.flow.update

class SettingsViewModel : ViewModel(){
    private val _settings = MutableStateFlow(AppSettings())
    val settings: StateFlow<AppSettings> = _settings.asStateFlow()

    data class AppSettings(
        val dateFormat: DateFormatOption = DateFormatOption.YMD
    )

    fun setDateFormat(option: DateFormatOption) {
        _settings.update {
            it.copy(dateFormat = option)
        }
    }
}