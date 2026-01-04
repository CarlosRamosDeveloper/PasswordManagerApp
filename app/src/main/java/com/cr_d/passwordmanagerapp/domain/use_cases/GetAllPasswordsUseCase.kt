package com.cr_d.passwordmanagerapp.domain.use_cases

import com.cr_d.passwordmanagerapp.data.repository.IPasswordRepository
import com.cr_d.passwordmanagerapp.data.dto.PasswordDetail

class GetAllPasswordsUseCase (
    private val repository: IPasswordRepository
){
    suspend operator fun invoke(): List<PasswordDetail>{
        return repository.findAll()
    }
}