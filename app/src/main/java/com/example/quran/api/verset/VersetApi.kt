package com.example.quran.api.verset

data class VersetApi(
    val index:Int,
    val sura: Int,
    val aya: Int,
    val simple: String,
    val translate: VersetTranslate
) {
}