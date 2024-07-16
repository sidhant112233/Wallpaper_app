package com.example.wallhaven.Retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {

    companion object{
        var retrofit:Retrofit? = null
        val BASE_URL = "https://pixabay.com/"
        fun getApiClient():Retrofit?{
            if (retrofit == null)
            {
                retrofit= Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
            }
            return retrofit
        }
    }
}