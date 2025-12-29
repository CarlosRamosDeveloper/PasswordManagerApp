package com.cr_d.passwordmanagerapp.ui.screens.main_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cr_d.passwordmanagerapp.application.interfaces.IPasswordRepository
import com.cr_d.passwordmanagerapp.data.SampleData
import kotlinx.coroutines.launch

class MainScreenViewModel (
    val repository: IPasswordRepository
) : ViewModel() {

    fun onPopulate(){
        viewModelScope.launch {
            repository.massSave(SampleData.passwords)
        }
    }

    fun onMassDelete(){
        viewModelScope.launch {
            repository.massDelete()
        }
    }
}