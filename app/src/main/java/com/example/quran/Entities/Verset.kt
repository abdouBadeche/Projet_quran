package com.example.quran.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName= "verset_table")
data class Verset(
    @PrimaryKey()
    val id: Int,
    val text_ar: String,
    val text_an: String,
    val sourat_id: Int,
    val verset_sourat_id: Int,
    val nbr_mots: Int
){

}