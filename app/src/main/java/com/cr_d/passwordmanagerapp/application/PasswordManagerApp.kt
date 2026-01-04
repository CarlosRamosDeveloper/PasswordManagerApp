package com.cr_d.passwordmanagerapp.application

import android.app.Application

class PasswordManagerApp : Application() {
    lateinit var appGraph: AppGraph
        private set

    override fun onCreate() {
        super.onCreate()
        appGraph = AppGraph(this)
    }
}