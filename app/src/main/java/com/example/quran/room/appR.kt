package com.example.quran.room

import android.app.Application
import com.example.quran.database.ServiceDB

class appR: Application() {
    override fun onCreate() {
        super.onCreate()
        ServiceDB.context=applicationContext
    }
}