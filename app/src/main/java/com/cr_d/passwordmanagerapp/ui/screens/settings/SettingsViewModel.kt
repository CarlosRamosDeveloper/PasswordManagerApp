package com.cr_d.passwordmanagerapp.ui.screens.settings

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

import com.cr_d.passwordmanagerapp.ui.model.DateFormatOption

class SettingsViewModel : ViewModel(){
    private val _settings = MutableStateFlow(AppSettings())
    val settings: StateFlow<AppSettings> = _settings.asStateFlow()

    data class AppSettings(
        val dateFormat: DateFormatOption = DateFormatOption.YMD
    )

    init {
        Log.d("CreationScreen", "Settings -> $this")
    }

    fun setDateFormat(option: DateFormatOption) {
        _settings.update {
            it.copy(dateFormat = option)
        }
    }
}