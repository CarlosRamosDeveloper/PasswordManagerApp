package com.cr_d.passwordmanagerapp.data.mapper

import com.cr_d.passwordmanagerapp.data.entities.PasswordEntity
import com.cr_d.passwordmanagerapp.domain.value_objects.Password
import com.cr_d.passwordmanagerapp.domain.value_objects.PasswordDetail
import com.cr_d.passwordmanagerapp.domain.value_objects.PasswordDetailInfo

fun Password.toEntity(): PasswordEntity {
    return PasswordEntity(
        id = id,
        cipheredPassword = cipheredPassword.encryptedText,
        passwordIv = cipheredPassword.iv,
        appId = appId,
        accountId = accountId,
        creationDate = dateInfo.creationDate.toString(),
        lastUpdate = dateInfo.lastUpdate.toString(),
        cipheredNotes = cipheredNotes.encryptedText,
        notesIv = cipheredNotes.iv
    )
}

fun Password.toDetail(extraInfo: PasswordDetailInfo): PasswordDetail {
    return PasswordDetail(
        id = id,
        cipheredPassword = cipheredPassword,
        appData = extraInfo.appData,
        accountData = extraInfo.accountData,
        metadata = extraInfo.metadata,
        dateInfo = dateInfo,
        score = extraInfo.score,
        cipheredNotes = cipheredNotes,
    )
}