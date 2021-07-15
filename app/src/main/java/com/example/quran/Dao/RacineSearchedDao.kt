package com.example.quran.Dao

import androidx.room.*
import com.example.quran.Entities.RacinSearched
import com.example.quran.Entities.Racine

@Dao
interface RacineSearchedDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRacineSearched(racineSearched: RacinSearched)

    @Update
    fun updateRacineSearched(racineSearched: RacinSearched)

    @Query("DELETE FROM racin_searched_table WHERE id == :idRacineSearched")
    fun deleteRacineSearched(idRacineSearched: Int)

    @Query("DELETE FROM racin_searched_table WHERE racin_id == :idRacine")
    fun deleteRacineSearchedByIdRacin(idRacine: Int)

    @Query("SELECT * FROM racin_searched_table WHERE id == :idRacineSearched")
    fun getRacineSearchedById(idRacineSearched: Int): List<RacinSearched>

    @Query("SELECT racine_table.* FROM  racine_table, racin_searched_table WHERE  racin_searched_table.racin_id == racine_table.id")
    fun getRacineSearchedList(): List<Racine>


}