package com.cr_d.passwordmanagerapp.application.use_cases

import com.cr_d.passwordmanagerapp.domain.entities.PasswordGenerator

class GeneratePasswordUseCase (
    private val generator: PasswordGenerator
){
    operator fun invoke() : String {
        return generator.generatePassword()
    }
}