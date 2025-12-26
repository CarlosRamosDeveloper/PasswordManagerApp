package com.cr_d.passwordmanagerapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity
data class PasswordEntity(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "password")
    val plainPassword: String,
    @ColumnInfo(name = "app_name")
    val appName: String,
    @ColumnInfo(name = "app_url")
    val appUrl: String,
    @ColumnInfo(name = "account")
    val account: String,
    @ColumnInfo(name = "creation_date")
    val creationDate: LocalDate,
    @ColumnInfo(name = "last_update")
    val lastUpdate: LocalDate,
    @ColumnInfo(name = "notes")
    val notes: String,
)
