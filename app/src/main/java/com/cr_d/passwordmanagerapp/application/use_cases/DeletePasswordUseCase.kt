package com.cr_d.passwordmanagerapp.application.use_cases

import com.cr_d.passwordmanagerapp.application.interfaces.IPasswordRepository

class DeletePasswordUseCase (
    private val repository: IPasswordRepository
){
    operator fun invoke(id: Long){
        repository.delete(id)
    }
}