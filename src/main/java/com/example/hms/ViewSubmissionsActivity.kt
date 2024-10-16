package com.example.hms

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.hms.api.SubmissionApi
import com.example.hms.model.Submission

class ViewSubmissionsActivity : AppCompatActivity() {

    private lateinit var submissionsRecyclerView: RecyclerView
    private lateinit var submissionsAdapter: SubmissionAdapter
    private lateinit var submissionApi: SubmissionApi
    private lateinit var token: String
    private lateinit var backButton: Button  // Declare backButton here

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_submissions)

        // Initialize the RecyclerView
        submissionsRecyclerView = findViewById(R.id.recyclerViewSubmissions)
        submissionsRecyclerView.layoutManager = LinearLayoutManager(this)

        // Get the token from LoginActivity or a global variable
        token = LoginActivity.token ?: throw IllegalStateException("Token is null. Please log in.")

        // Initialize Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://techcelestialsapp-fdhua4hhdwb9f4dc.southafricanorth-01.azurewebsites.net/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        submissionApi = retrofit.create(SubmissionApi::class.java)

        // Initialize the back button after setContentView
        backButton = findViewById(R.id.backButton)
        backButton.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }

        // Fetch user feedbacks
        fetchUserSubmission()
    }

    private fun fetchUserSubmission() {
        // Fetch submissions from the API
        if (token.isNotEmpty()) {  // Check if the token is not empty
            submissionApi.getUserSubmissions("Bearer $token").enqueue(object : Callback<List<Submission>> {
                override fun onResponse(
                    call: Call<List<Submission>>,
                    response: Response<List<Submission>>
                ) {
                    if (response.isSuccessful) {
                        val submissions = response.body() ?: emptyList()
                        // Pass the submissions to the adapter
                        submissionsAdapter = SubmissionAdapter(submissions)
                        submissionsRecyclerView.adapter = submissionsAdapter
                    } else {
                        Toast.makeText(
                            this@ViewSubmissionsActivity,
                            "Failed to load submissions: ${response.message()}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                override fun onFailure(call: Call<List<Submission>>, t: Throwable) {
                    Toast.makeText(
                        this@ViewSubmissionsActivity,
                        "Error: ${t.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
        } else {
            Toast.makeText(this, "Token is missing. Please log in.", Toast.LENGTH_SHORT).show()
        }
    }
}
