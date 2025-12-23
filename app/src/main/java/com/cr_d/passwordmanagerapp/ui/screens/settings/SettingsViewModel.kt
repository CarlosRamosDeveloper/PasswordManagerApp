package com.cr_d.passwordmanagerapp.ui.screens.settings

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

import com.cr_d.passwordmanagerapp.ui.models.DateFormatOption

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