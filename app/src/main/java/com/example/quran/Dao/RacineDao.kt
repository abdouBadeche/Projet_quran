package com.example.quran.Dao

import androidx.room.*
import com.example.quran.Entities.Racine

@Dao
interface RacineDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addRacine(racine : Racine)


    @Query("SELECT * FROM racine_table")
    fun getRacines(): List<Racine>

    @Update
    fun updateRacine(Racine: Racine)

    @Delete
    fun deleteRacine(Racine: Racine)

    @Query("SELECT * FROM racine_table WHERE racine == :filter")
    fun getRacineByName(filter: String): List<Racine>

    @Query("SELECT * FROM racine_table WHERE id == :id_racine")
    fun getRacineById(id_racine: Int): List<Racine>


}