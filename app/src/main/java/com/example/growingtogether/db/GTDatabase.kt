package com.example.growingtogether.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.growingtogether.dataclasses.BItacora
import com.example.growingtogether.dataclasses.Bebe
import com.example.growingtogether.dataclasses.Registros_Bitacora
import com.example.growingtogether.dataclasses.Usuario

@Database(entities = [Bebe::class, BItacora::class, Registros_Bitacora::class, Usuario::class], version = 1)
@TypeConverters(Converters::class)
abstract class GTDatabase : RoomDatabase() {

    companion object {
        const val NAME = "GT_DB"
    }

    abstract fun getGTDao () : GTDao

}