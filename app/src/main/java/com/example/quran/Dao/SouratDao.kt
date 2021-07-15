package com.example.quran.Dao

import androidx.room.*
import com.example.quran.Entities.Racine
import com.example.quran.Entities.Sourat
import com.example.quran.Entities.Verset

@Dao
interface SouratDao {
    @Query("SELECT * FROM sourat_table")
    fun getSourats(): List<Sourat>

    @Update
    fun updateSourat(Sourat: Sourat)

    @Delete
    fun deleteSourat(Sourat: Sourat)

    @Query("SELECT * FROM sourat_table WHERE sourat_table.id = :IdSourat")
    fun getSouratById(IdSourat: Int): List<Sourat>


}