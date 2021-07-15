package com.example.quran.Dao

import androidx.room.*
import com.example.quran.Entities.Racine
import com.example.quran.Entities.Verset

@Dao
interface VersetDao {
    @Query("SELECT * FROM verset_table")
    fun getVersets(): List<Verset>

    @Update
    fun updateVerset(Verset: Verset)

    @Delete
    fun deleteVerset(Verset: Verset)

    @Query("SELECT * FROM verset_table WHERE text_ar == :filter or text_an == :filter")
    fun getVersetsByName(filter: String): List<Verset>


    @Query("SELECT * FROM verset_table WHERE id == :id ")
    fun getVersetsById(id: Int): List<Verset>



    @Query("select verset_table.*  from verset_table JOIN mot_table on verset_table.verset_sourat_id == mot_table.id_verset and  verset_table.sourat_id == mot_table.id_sourat and mot_table.id_racine == :racin_id ")
    fun getVersetsByRacine(racin_id: Int) : List<Verset>


}