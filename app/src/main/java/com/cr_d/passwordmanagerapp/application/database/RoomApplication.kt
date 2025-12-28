package com.cr_d.passwordmanagerapp.application.database

import android.app.Application
import androidx.room.Room
import com.cr_d.passwordmanagerapp.data.database.AppDatabase

class RoomApplication : Application(){
    companion object{
        lateinit var db: AppDatabase
    }

    override fun onCreate() {
        super.onCreate()

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "password-database"
        ).build()
    }
}