package com.cr_d.passwordmanagerapp.ui.model

data class MainConfirmDialogData (
    val isPasswordMassDeleteDialogShown: Boolean = false,
    val isPasswordPopulateDatabaseDialogShown: Boolean = false,
    val isAccountsMassDeleteDialogShown: Boolean = false,
    val isAccountsPopulateDatabaseDialogShown: Boolean = false,
    val isApplicationMassDeleteDialogShown: Boolean = false,
    val isApplicationPopulateDatabaseDialogShown: Boolean = false
)