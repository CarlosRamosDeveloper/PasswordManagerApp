package com.cr_d.passwordmanagerapp.ui.screens.applications.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cr_d.passwordmanagerapp.domain.use_cases.application_use_cases.ApplicationParseToUiUseCase
import com.cr_d.passwordmanagerapp.domain.use_cases.application_use_cases.DeleteApplicationUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

import com.cr_d.passwordmanagerapp.ui.model.ApplicationUiState

class ApplicationDetailViewModel(
    private val appId: Long,
    private val parser: ApplicationParseToUiUseCase,
    private val delete: DeleteApplicationUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    data class UiState (
        val application: ApplicationUiState? = null,
        val isDeleteDialogShown: Boolean = false,
    )

    init {
        onRefresh()
    }

    fun onRefresh(){
        viewModelScope.launch {
            loadApplication(appId)
        }
    }

    fun onEnableDeleteDialog(){
        _uiState.update {
            it.copy(
                isDeleteDialogShown = true
            )
        }
    }

    fun onDisableDeleteDialog(){
        _uiState.update {
            it.copy(
                isDeleteDialogShown = false
            )
        }
    }

    suspend fun loadApplication(appId:Long){
        val application = parser(appId)

        _uiState.update {
            it.copy(
                application = application
            )
        }
    }

    fun onDeleteApplication(){
        viewModelScope.launch {
            delete(appId)
            onDisableDeleteDialog()
        }
    }
}