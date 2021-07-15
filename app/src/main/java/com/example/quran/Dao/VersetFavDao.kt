package com.example.quran.Dao

import androidx.room.*
import com.example.quran.Entities.RacinSearched
import com.example.quran.Entities.Racine
import com.example.quran.Entities.Verset
import com.example.quran.Entities.VersetFav

@Dao
interface VersetFavDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertVersetFav(versetFav: VersetFav)

    @Update
    fun updateVersetFav(versetFav: VersetFav)

    @Query("DELETE FROM fav_verset WHERE id_verset_fav == :idVersetFav")
    fun deleteVersetFav(idVersetFav: Int)

    @Query("SELECT * FROM fav_verset WHERE id_verset_fav == :idVersetFav")
    fun getVersetFavById(idVersetFav: Int): List<VersetFav>

    @Query("SELECT verset_table.* FROM  verset_table, fav_verset WHERE  fav_verset.id_verset == verset_table.id")
    fun getVersetFavList(): List<Verset>


}