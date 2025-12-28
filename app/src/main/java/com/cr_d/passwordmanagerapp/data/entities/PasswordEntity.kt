package com.cr_d.passwordmanagerapp.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PasswordEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @ColumnInfo(name = "password")
    val plainPassword: String,
    @ColumnInfo(name = "app_name")
    val appName: String,
    @ColumnInfo(name = "app_url")
    val appUrl: String,
    @ColumnInfo(name = "account")
    val account: String,
    @ColumnInfo(name = "creation_date")
    val creationDate: String,
    @ColumnInfo(name = "last_update")
    val lastUpdate: String,
    @ColumnInfo(name = "notes")
    val notes: String,
)