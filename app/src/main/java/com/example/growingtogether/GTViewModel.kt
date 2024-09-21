package com.example.growingtogether

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.growingtogether.dataclasses.Bebe
import kotlinx.coroutines.launch
import java.sql.Date
import java.time.Instant
import java.time.LocalDate

class GTViewModel : ViewModel() {

    val gtDao = MainApplication.gtDatabase.getGTDao();

    val babiesList : LiveData<List<Bebe>> = gtDao.getAllBabies();

    fun addBabie(nombre: String, fechaNacimiento: Date, peso: Double, genero: Char, idBitacora: Int) {
        viewModelScope.launch {
            gtDao.insertBaby(Bebe(
                nombre = nombre,
                fechanacimiento = fechaNacimiento,
                peso = peso,
                genero = genero,
                idBitacora = idBitacora,
                createdAt = java.util.Date()
            ))
        }
    }

    fun deleteBaby(id: Int) {
        viewModelScope.launch {
            gtDao.deleteBaby(id)
        }
    }
}