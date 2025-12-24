package com.cr_d.passwordmanagerapp.application.use_cases

import com.cr_d.passwordmanagerapp.application.interfaces.IPasswordRepository
import com.cr_d.passwordmanagerapp.domain.value_objects.PasswordData

class GetAllPasswordsUseCase (
    private val repository: IPasswordRepository
){
    operator fun invoke(): List<PasswordData>{
        return repository.findAll()
    }
}