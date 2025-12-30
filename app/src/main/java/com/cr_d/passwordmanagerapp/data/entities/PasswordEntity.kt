package com.cr_d.passwordmanagerapp.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PasswordEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    @ColumnInfo(name = "password")
    val cipheredPassword: ByteArray,
    @ColumnInfo(name = "password_iv")
    val passwordIv: ByteArray,
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
    @ColumnInfo(name = "ciphered_notes")
    val cipheredNotes: ByteArray,
    @ColumnInfo(name = "notes_iv")
    val notesIv: ByteArray
)

// TODO: Agregar tabla de aplicación -> APP_Entity
// TODO: Agregar tabla de cuenta de usuario -> Account_Entity
// TODO: Permitir la busqueda por app
// TODO: Permtir la búsqueda por cuenta de usuario
// TODO: Al pinchar en nombre de app, debería de aparecer un dropdown con las apps ya creadas
// TODO: Al agregar una aplicación, debería de actualizarse el campo de url a "www.appname.com
// TODO: Al pinchar en el campo de cuenta de usuario, debería de desplegarse un dropdown con las cuentas creadas