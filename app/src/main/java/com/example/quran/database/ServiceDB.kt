package com.example.quran.database

import android.annotation.SuppressLint
import android.content.Context
import androidx.room.Room


@SuppressLint("StaticFieldLeak")
object ServiceDB{
    lateinit var context: Context
    val database by lazy {
        Room.databaseBuilder(context, AppDataBase::class.java,"ap_quran")
            .createFromAsset("quran.db")
            .allowMainThreadQueries()
            .build()
    }
}