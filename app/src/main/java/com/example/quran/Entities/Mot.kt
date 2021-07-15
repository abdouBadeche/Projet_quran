package com.example.quran.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName= "mot_table")
data class Mot(
    @PrimaryKey()
    val id: Int,
    val word_ar: String,
    val word_an: String,
    val id_verset: Int,
    val id_sourat: Int,
    val id_racine: Int,
    val num_aya: Int
){

}