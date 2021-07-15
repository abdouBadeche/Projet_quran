package com.example.quran.api.verset


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface VersetService {
    @GET("aya")
    fun getAya(@Query("index") index:Int ): Call<VersetResponse>

   @GET("sura")
   fun getAyaIndex(@Query("index") sura:Int, @Query("start") aya:Int, @Query("limit") limit:Int): Call<VersetIndexResponse>
}