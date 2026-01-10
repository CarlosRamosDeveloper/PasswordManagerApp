package com.cr_d.passwordmanagerapp.ui.screens.accounts.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

import com.cr_d.passwordmanagerapp.data.mapper.toDetail
import com.cr_d.passwordmanagerapp.data.mapper.toUiState
import com.cr_d.passwordmanagerapp.data.repository.interfaces.IAccountRepository
import com.cr_d.passwordmanagerapp.domain.use_cases.account_use_cases.ObtainAccountDetailInfoUseCase
import com.cr_d.passwordmanagerapp.domain.use_cases.security_use_cases.DecryptStringUseCase
import com.cr_d.passwordmanagerapp.ui.model.AccountUiState

class AccountDetailViewModel (
    private val accountId: Long,
    private val repository: IAccountRepository,
    private val obtainAccountDetail: ObtainAccountDetailInfoUseCase,
    private val decrypt: DecryptStringUseCase
) : ViewModel(){
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    data class UiState (
        val account: AccountUiState? = null
    )

    init {
        Log.d("AccountDetail", "onInit ${_uiState.value.account}")
        onRefresh()
    }

    fun onRefresh(){
        viewModelScope.launch {
            Log.d("AccountDetail", "onRefresh ${_uiState.value.account}")
            loadAccount(accountId)
        }
    }

    suspend fun loadAccount(accountId: Long) {
        val state = _uiState.value

        Log.d("AccountDetail", "load step 1 ${state.account}")
        val account = repository.findById(accountId) ?: return
        val extraData = obtainAccountDetail.invoke(account)
        val decipheredAccount = decrypt(account.cipheredAccount)
        val decipheredNotes = decrypt(account.cipheredNotes)
        Log.d("AccountDetail", "load step 2 ${state.account}")
        val parsedData = account.toDetail(extraData).toUiState(decipheredAccount)
        _uiState.update {
            it.copy(
                account = parsedData
            )
        }
        Log.d("AccountDetail", "load step 3 ${state.account}")
    }
}