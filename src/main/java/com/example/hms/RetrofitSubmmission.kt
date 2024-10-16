package com.example.hms.api

import com.example.hms.model.Submission
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface SubmissionApi {
    @GET("submissions/my-submissions")
    fun getUserSubmissions(
        @Header("Authorization") token: String
    ): Call<List<Submission>>
}
