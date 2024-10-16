package com.example.hms.api

import com.example.hms.model.Assignment
import retrofit2.Call
import retrofit2.http.GET

interface AssignmentApiService {
    @GET("assignments")
    fun getAssignments(): Call<List<Assignment>>
}
