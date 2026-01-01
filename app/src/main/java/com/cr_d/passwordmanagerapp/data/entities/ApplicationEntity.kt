package com.cr_d.passwordmanagerapp.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ApplicationEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    @ColumnInfo(name = "app_name")
    val appName: String,
    @ColumnInfo(name = "app_url")
    val appUrl: String?,
    @ColumnInfo(name = "ciphered_notes")
    val cipheredNotes: ByteArray,
    @ColumnInfo(name = "notes_iv")
    val notesIv: ByteArray
)
