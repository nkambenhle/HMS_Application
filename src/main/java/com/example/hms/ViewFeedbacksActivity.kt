package com.example.hms

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hms.api.FeedbackApi
import com.example.hms.model.Feedback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ViewFeedbacksActivity : AppCompatActivity() {

    private lateinit var feedbackApi: FeedbackApi
    private lateinit var recyclerView: RecyclerView
    private lateinit var feedbackAdapter: FeedbackAdapter
    private lateinit var backButton: Button  // Declare backButton here
    private lateinit var token: String  // Declare token variable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_feedbacks)  // Ensure correct layout

        // Initialize the RecyclerView
        recyclerView = findViewById(R.id.recyclerViewFeedback)
        feedbackAdapter = FeedbackAdapter(emptyList())
        recyclerView.adapter = feedbackAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Initialize Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://techcelestialsapp-fdhua4hhdwb9f4dc.southafricanorth-01.azurewebsites.net/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        feedbackApi = retrofit.create(FeedbackApi::class.java)

        // Get the token from LoginActivity or a global variable
        token = LoginActivity.token ?: throw IllegalStateException("Token is null. Please log in.")

        // Initialize the back button
        backButton = findViewById(R.id.backButton)
        backButton.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }

        // Fetch user feedbacks
        fetchUserFeedbacks()
    }

    private fun fetchUserFeedbacks() {
        // Ensure token is not null before making the request
        if (token.isNotEmpty()) {  // Check if the token is not empty
            feedbackApi.getUserFeedbacks("Bearer $token").enqueue(object : Callback<List<Feedback>> {
                override fun onResponse(
                    call: Call<List<Feedback>>,
                    response: Response<List<Feedback>>
                ) {
                    if (response.isSuccessful) {
                        val feedbacks = response.body() ?: emptyList()
                        feedbackAdapter = FeedbackAdapter(feedbacks)
                        recyclerView.adapter = feedbackAdapter
                    } else {
                        Toast.makeText(
                            this@ViewFeedbacksActivity,
                            "Failed to fetch feedbacks: ${response.message()}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                override fun onFailure(call: Call<List<Feedback>>, t: Throwable) {
                    Toast.makeText(
                        this@ViewFeedbacksActivity,
                        "Network error: ${t.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
        } else {
            Toast.makeText(this, "Token is missing. Please log in.", Toast.LENGTH_SHORT).show()
        }
    }
}
