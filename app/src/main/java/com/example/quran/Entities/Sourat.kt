package com.example.quran.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName= "sourat_table")
data class Sourat(
    @PrimaryKey()
    val id: Int,
    val nom: String,
    val location: String,
    val nbr_ayat: Int,
    val nbr_mot: Int,
    val nbr_lettre: Int
){

}