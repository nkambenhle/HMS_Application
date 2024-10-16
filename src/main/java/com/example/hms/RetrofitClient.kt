package com.example.hms

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.hms.api.AssignmentApiService

object RetrofitClient {
    private const val BASE_URL = "https://techcelestialsapp-fdhua4hhdwb9f4dc.southafricanorth-01.azurewebsites.net/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api: AssignmentApiService = retrofit.create(AssignmentApiService::class.java)
}
