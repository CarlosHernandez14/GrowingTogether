package com.example.growingtogether

import android.app.Application
import androidx.room.Room
import com.example.growingtogether.db.GTDatabase

class MainApplication : Application() {

    companion object {
        lateinit var gtDatabase : GTDatabase
    }

    override fun onCreate() {
        super.onCreate()
        gtDatabase = Room.databaseBuilder(
            applicationContext,
            GTDatabase::class.java,
            GTDatabase.NAME
        ).build()
    }
}