package com.cr_d.passwordmanagerapp.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AccountEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    @ColumnInfo(name = "ciphered_account")
    val cipheredAccount: ByteArray,
    @ColumnInfo(name = "account_iv")
    val accountIv: ByteArray,
    @ColumnInfo(name = "ciphered_notes")
    val cipheredNotes: ByteArray,
    @ColumnInfo(name = "notes_iv")
    val notesIv: ByteArray
)
