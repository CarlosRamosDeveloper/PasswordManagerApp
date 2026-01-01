package com.cr_d.passwordmanagerapp.application.database

import android.app.Application
import androidx.room.Room
import com.cr_d.passwordmanagerapp.application.interfaces.IAccountRepository

import com.cr_d.passwordmanagerapp.application.interfaces.IPasswordRepository
import com.cr_d.passwordmanagerapp.application.interfaces.RoomAccountRepository
import com.cr_d.passwordmanagerapp.application.interfaces.RoomPasswordRepository
import com.cr_d.passwordmanagerapp.data.daos.AccountDao
import com.cr_d.passwordmanagerapp.data.daos.PasswordDao
import com.cr_d.passwordmanagerapp.data.database.AppDatabase

class RoomApplication : Application(){
    companion object{
        private lateinit var db: AppDatabase
        fun getDatabase(): AppDatabase = db
        fun passwordDao(): PasswordDao = db.passwordDao()
        fun accountDao(): AccountDao = db.accountDao()
        fun getPasswordRepository(): IPasswordRepository = RoomPasswordRepository(passwordDao())
        fun getAccountRepository(): IAccountRepository = RoomAccountRepository(accountDao())
    }


    override fun onCreate() {
        super.onCreate()

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "password-database"
        )
            .fallbackToDestructiveMigration(true)
            .build()

        val dao = db.passwordDao()
        val repo = RoomPasswordRepository(dao)
    }
}