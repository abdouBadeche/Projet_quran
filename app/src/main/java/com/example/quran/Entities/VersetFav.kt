package com.example.quran.Entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName= "fav_verset" , indices = [Index(value = ["id_verset"], unique = true)])
data class VersetFav(
    @PrimaryKey()
    val id_verset_fav: Int,
    val id_verset: Int
){

}