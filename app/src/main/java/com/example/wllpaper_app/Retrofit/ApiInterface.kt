package com.example.wallhaven.Retrofit

import com.example.wallhaven.Model.WallModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("api/")
    fun searchWallPaper(
        @Query("key") key:String,@Query("q") q:String,@Query("category") category:String
    ):Call<WallModel>
}