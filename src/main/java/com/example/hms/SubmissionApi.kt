package com.example.hms

import com.example.hms.model.Submission
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header  // <-- This is the missing import

interface SubmissionApi {
    @GET("submissions/my-submission")
    fun getUserSubmissions(
        @Header("Authorization") token: String
    ): Call<List<Submission>>
}
