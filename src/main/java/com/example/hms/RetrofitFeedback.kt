package com.example.hms.api

import com.example.hms.model.Feedback
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface FeedbackApi {
    @GET("feedbacks/my-feedbacks")
    fun getUserFeedbacks(
        @Header("Authorization") token: String,
        //@Query("userId") userId: String
    ): Call<List<Feedback>>
}

