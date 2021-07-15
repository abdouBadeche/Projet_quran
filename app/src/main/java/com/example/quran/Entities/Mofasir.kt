package com.example.quran.Entities

data class Mofasir(
var id: Int = 0,
var name: String = ""
){
    override fun toString(): String {
        return name
    }
}



