package com.cr_d.passwordmanagerapp.domain.use_cases

import com.cr_d.passwordmanagerapp.data.repository.interfaces.IPasswordRepository

class DeletePasswordUseCase (
    private val repository: IPasswordRepository
){
    suspend operator fun invoke(id: Long){
        repository.delete(id)
    }
}