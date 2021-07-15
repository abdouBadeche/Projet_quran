package com.example.quran.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.quran.Dao.*
import com.example.quran.Entities.*

@Database(entities = [Sourat::class , Racine::class , Verset::class , Mot::class , RacinSearched::class  , VersetFav::class ] , version = 1)

abstract class AppDataBase : RoomDatabase() {
    abstract fun racineDao(): RacineDao
    abstract fun versetDao(): VersetDao
    abstract fun souratDao(): SouratDao
    abstract fun racineSearchedDao(): RacineSearchedDao
    abstract  fun versetFavDeo() : VersetFavDao

}
