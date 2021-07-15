package com.example.quran.api.tafsir

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface TafsirService {

    @GET("tafseer/{id}/{sura}/{aya}")
    fun getTafsirByMofasirId(@Path("id") Id:Int, @Path("sura") sura:Int, @Path("aya") aya:Int): Call<TafsirResponse>

}

