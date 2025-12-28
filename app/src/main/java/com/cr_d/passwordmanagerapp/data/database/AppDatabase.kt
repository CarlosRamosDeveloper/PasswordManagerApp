package com.cr_d.passwordmanagerapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

import com.cr_d.passwordmanagerapp.data.daos.IPasswordDao
import com.cr_d.passwordmanagerapp.data.entities.PasswordEntity

@Database(entities = [PasswordEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun passwordDao() : IPasswordDao
}

