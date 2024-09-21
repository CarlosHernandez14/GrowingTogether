package com.example.growingtogether.dataclasses

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.Date

@Entity(tableName = "Usuario")
data class Usuario(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var nombre: String,
    var correo: String,
    var contrasena: String,
    var createdAt: Date
) : Serializable
