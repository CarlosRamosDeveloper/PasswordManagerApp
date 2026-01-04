package com.cr_d.passwordmanagerapp.domain.use_cases

import com.cr_d.passwordmanagerapp.domain.services.PasswordGenerator
import com.cr_d.passwordmanagerapp.domain.value_objects.PasswordDataGeneration

class GeneratePasswordUseCase (
    private val generator: PasswordGenerator
){
    operator fun invoke(passwordData: PasswordDataGeneration) : String {
        return generator.generatePassword(passwordData)
    }
}