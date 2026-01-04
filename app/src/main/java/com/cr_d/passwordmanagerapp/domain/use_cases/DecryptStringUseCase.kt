package com.cr_d.passwordmanagerapp.domain.use_cases

import com.cr_d.passwordmanagerapp.data.crypto.CryptoService
import com.cr_d.passwordmanagerapp.domain.value_objects.EncryptedPayload

class DecryptStringUseCase(private val cryptoService: CryptoService) {
    operator fun invoke(payload: EncryptedPayload): String {
        return cryptoService.decrypt(payload)
    }
}
