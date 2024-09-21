package com.example.growingtogether.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.growingtogether.dataclasses.Bebe
import com.example.growingtogether.dataclasses.Bitacora
import com.example.growingtogether.dataclasses.Registros_Bitacora
import com.example.growingtogether.dataclasses.Usuario

@Dao
interface GTDao {

    // Bebe CRUD operations
    @Query("SELECT * FROM Bebe")
    fun getAllBabies(): LiveData<List<Bebe>>

    @Query("SELECT * FROM Bebe WHERE id = :idBebe")
    fun getBabyById(idBebe: Int): LiveData<Bebe>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBaby(bebe: Bebe)

    @Update
    suspend fun updateBaby(bebe: Bebe)

    @Query("DELETE FROM Bebe WHERE id = :id")
    suspend fun deleteBaby(id: Int)

    // Bitacora CRUD operations
    @Query("SELECT * FROM BItacora")
    fun getAllBitacoras(): LiveData<List<Bitacora>>

    @Query("SELECT * FROM BItacora WHERE id = :idBitacora")
    fun getBitacoraById(idBitacora: Int): LiveData<Bitacora>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBitacora(bitacora: Bitacora)

    @Update
    suspend fun updateBitacora(bitacora: Bitacora)

    @Delete
    suspend fun deleteBitacora(bitacora: Bitacora)

    // Registros_Bitacora CRUD operations
    @Query("SELECT * FROM Registros_Bitacora WHERE id_bitacora = :idBitacora")
    fun getAllRecordsFromBitacora(idBitacora: Int): LiveData<List<Registros_Bitacora>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecord(record: Registros_Bitacora)

    @Update
    suspend fun updateRecord(record: Registros_Bitacora)

    @Delete
    suspend fun deleteRecord(record: Registros_Bitacora)

    // Usuario CRUD operations
    @Query("SELECT * FROM Usuario")
    fun getAllUsers(): LiveData<List<Usuario>>

    @Query("SELECT * FROM Usuario WHERE id = :idUsuario")
    fun getUserById(idUsuario: Int): LiveData<Usuario>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(usuario: Usuario)

    @Update
    suspend fun updateUser(usuario: Usuario)

    @Delete
    suspend fun deleteUser(usuario: Usuario)
}
