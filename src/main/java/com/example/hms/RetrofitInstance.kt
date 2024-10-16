package com.example.hms

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://techcelestialsapp-fdhua4hhdwb9f4dc.southafricanorth-01.azurewebsites.net/"

    fun getRetrofitInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
