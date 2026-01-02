package com.cr_d.passwordmanagerapp.application.use_cases

import com.cr_d.passwordmanagerapp.application.interfaces.IPasswordRepository
import com.cr_d.passwordmanagerapp.domain.value_objects.PasswordDetail

class GetAllPasswordsUseCase (
    private val repository: IPasswordRepository
){
    suspend operator fun invoke(): List<PasswordDetail>{
        return repository.findAll()
    }
}