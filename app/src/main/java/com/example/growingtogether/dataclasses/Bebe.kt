package com.example.growingtogether.dataclasses

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "Bebe")
data class Bebe(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    var nombre: String,
    var fechanacimiento: Date,
    var peso: Double,
    var genero: Char,
    var idBitacora: Int,
    var createdAt : Date
)