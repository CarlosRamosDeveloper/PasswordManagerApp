package com.cr_d.passwordmanagerapp.ui.models

data class PasswordConfirmDialogData(
    val isDeletePasswordDialogShown: Boolean = false,
    val isCopyToDialogShown: Boolean = false,
    val isUpdatePasswordDialogShown: Boolean = false,
    val isUpdateNotesDialogShown: Boolean = false,
    val isDeleteNotesDialogShown: Boolean = false
)
