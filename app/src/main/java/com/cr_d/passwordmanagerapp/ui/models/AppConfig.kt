package com.cr_d.passwordmanagerapp.ui.models

import androidx.compose.ui.unit.dp
import com.cr_d.passwordmanagerapp.data.crypto.EncryptedPayload

object AppConfig {
    val HORIZONTAL_FRAME_PADDING = 20.dp
    val emptyEncryptedPayload = EncryptedPayload(
        ByteArray(1), ByteArray(1)
    )
}