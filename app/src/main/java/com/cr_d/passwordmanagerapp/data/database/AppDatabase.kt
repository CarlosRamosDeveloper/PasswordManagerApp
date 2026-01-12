package com.cr_d.passwordmanagerapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

import com.cr_d.passwordmanagerapp.data.daos.AccountDao
import com.cr_d.passwordmanagerapp.data.daos.ApplicationDao
import com.cr_d.passwordmanagerapp.data.daos.PasswordDao
import com.cr_d.passwordmanagerapp.data.entities.AccountEntity
import com.cr_d.passwordmanagerapp.data.entities.ApplicationEntity
import com.cr_d.passwordmanagerapp.data.entities.PasswordEntity

@Database(entities = [PasswordEntity::class, AccountEntity::class, ApplicationEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun passwordDao() : PasswordDao
    abstract fun accountDao() : AccountDao
    abstract fun appDao(): ApplicationDao
}
