package com.cr_d.passwordmanagerapp.domain.use_cases.security_use_cases

import com.cr_d.passwordmanagerapp.data.crypto.CryptoService
import com.cr_d.passwordmanagerapp.domain.value_objects.EncryptedPayload

class EncryptStringUseCase(private val cryptoService: CryptoService) {
    operator fun invoke(plainText: String): EncryptedPayload {
        return cryptoService.encrypt(plainText)
    }
}