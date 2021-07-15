package com.example.quran.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName= "racine_table")
data class Racine(
    @PrimaryKey()
    val id: Int,
    val racine: String
){

}