package com.example.quran.Entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName= "racin_searched_table" , indices = [Index(value = ["racin_id"], unique = true)])
data class RacinSearched(
    @PrimaryKey()
    val id: Int,
    val racin_id: Int
){

}