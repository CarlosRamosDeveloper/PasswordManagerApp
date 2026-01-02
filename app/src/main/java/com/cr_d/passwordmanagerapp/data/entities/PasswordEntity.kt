package com.cr_d.passwordmanagerapp.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "passwords",
    indices = [Index(value = ["app_id", "account_id"], unique = true)]
)
data class PasswordEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    @ColumnInfo(name = "password")
    val cipheredPassword: ByteArray,
    @ColumnInfo(name = "password_iv")
    val passwordIv: ByteArray,
    @ColumnInfo(name = "app_id")
    val appId: Long,
    @ColumnInfo(name = "account_id")
    val accountId: Long,
    @ColumnInfo(name = "creation_date")
    val creationDate: String,
    @ColumnInfo(name = "last_update")
    val lastUpdate: String,
    @ColumnInfo(name = "ciphered_notes")
    val cipheredNotes: ByteArray,
    @ColumnInfo(name = "notes_iv")
    val notesIv: ByteArray
)
