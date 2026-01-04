package com.cr_d.passwordmanagerapp.domain.use_cases

import com.cr_d.passwordmanagerapp.data.dto.PasswordDetail
import com.cr_d.passwordmanagerapp.data.repository.IPasswordRepository

class GetAllPasswordsUseCase (
    private val repository: IPasswordRepository
){
    suspend operator fun invoke(): List<PasswordDetail>{
        return repository.findAll()
    }
}