package com.cr_d.passwordmanagerapp.domain.use_cases

import com.cr_d.passwordmanagerapp.domain.services.PasswordAnalyzer
import com.cr_d.passwordmanagerapp.domain.value_objects.PasswordMetadata

class AnalyzePasswordUseCase (
    private val analyzer : PasswordAnalyzer
) {
    operator fun invoke(password: String): PasswordMetadata{
        return analyzer.analyze(password)
    }
}