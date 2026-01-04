package com.cr_d.passwordmanagerapp.ui.model

import androidx.compose.ui.unit.dp

import com.cr_d.passwordmanagerapp.domain.value_objects.EncryptedPayload

object AppConfig {
    val HORIZONTAL_FRAME_PADDING = 20.dp
    val emptyEncryptedPayload = EncryptedPayload(
        ByteArray(1), ByteArray(1)
    )
}