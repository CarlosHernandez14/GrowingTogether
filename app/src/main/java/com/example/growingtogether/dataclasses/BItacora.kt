package com.example.growingtogether.dataclasses

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "Bitacora")
data class Bitacora(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var id_bebe: Int,
    var fecha_inicio: Date,
    var fecha_fin: Date,
    var observaciones: String,
    var createdAt: Date
)
