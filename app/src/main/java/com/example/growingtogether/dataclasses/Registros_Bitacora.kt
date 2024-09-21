package com.example.growingtogether.dataclasses

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "Registros_Bitacora")
data class Registros_Bitacora(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var id_bitacora: Int,
    var fecha_registro: Date,
    var emocion: String,
    var sintoma: String, // Seria la causa de la emocion
    var comentarios: String,
    var createdAt: Date
)
