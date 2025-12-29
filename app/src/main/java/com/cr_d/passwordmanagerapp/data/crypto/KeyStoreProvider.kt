package com.cr_d.passwordmanagerapp.data.crypto

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.security.KeyStore
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

class KeyStoreProvider {
    fun getOrCreateKey(): SecretKey {
        val keyStore = KeyStore.getInstance(CryptoConstants.ANDROID_KEYSTORE).apply {
            load(null)
        }

        val existingKey = keyStore.getKey(CryptoConstants.KEY_ALIAS, null) as? SecretKey
        if (existingKey != null) return existingKey

        val keyGenerator = KeyGenerator.getInstance(
            KeyProperties.KEY_ALGORITHM_AES,
            CryptoConstants.ANDROID_KEYSTORE
        )

        val spec = KeyGenParameterSpec.Builder(
            CryptoConstants.KEY_ALIAS,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        )
            .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .setUserAuthenticationRequired(false)
            .build()

        keyGenerator.init(spec)

        return keyGenerator.generateKey()
    }
}