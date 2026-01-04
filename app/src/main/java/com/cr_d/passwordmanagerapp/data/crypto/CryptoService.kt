package com.cr_d.passwordmanagerapp.data.crypto

import com.cr_d.passwordmanagerapp.domain.value_objects.EncryptedPayload
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

class CryptoService (
    private val keyProvider: KeyStoreProvider = KeyStoreProvider()
) {
    private fun getCipherDecrypt(key: SecretKey, iv: ByteArray): Cipher {
        return Cipher.getInstance(CryptoConstants.AES_MODE).apply {
            init(Cipher.DECRYPT_MODE, key, GCMParameterSpec(128, iv))
        }
    }

    fun encrypt(data: String): EncryptedPayload {
        val key = keyProvider.getOrCreateKey()
        val cipher = Cipher.getInstance(CryptoConstants.AES_MODE)

        cipher.init(Cipher.ENCRYPT_MODE, key)
        val iv = cipher.iv
        val encryptedData = cipher.doFinal(data.toByteArray())

        return EncryptedPayload(encryptedData, iv)
    }

    fun decrypt(payload: EncryptedPayload): String {
        val key = keyProvider.getOrCreateKey()
        val cipher = getCipherDecrypt(key, payload.iv)

        return String(cipher.doFinal(payload.encryptedText))
    }
}