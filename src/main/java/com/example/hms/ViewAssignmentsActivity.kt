package com.example.hms

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hms.api.AssignmentApiService
import com.example.hms.model.Assignment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ViewAssignmentsActivity : AppCompatActivity() {

    private lateinit var assignmentApi: AssignmentApiService
    private lateinit var recyclerView: RecyclerView
    private lateinit var assignmentAdapter: AssignmentAdapter
    private lateinit var backButton: Button  // Declare backButton here

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_assignments)  // Ensure correct layout

        // Initialize the RecyclerView
        recyclerView = findViewById(R.id.recyclerViewAssignments)
        assignmentAdapter = AssignmentAdapter(emptyList())
        recyclerView.adapter = assignmentAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Initialize Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://techcelestialsapp-fdhua4hhdwb9f4dc.southafricanorth-01.azurewebsites.net/")  // Ensure correct URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        assignmentApi = retrofit.create(AssignmentApiService::class.java)

        // Initialize the back button
        backButton = findViewById(R.id.backButton)
        backButton.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }

        // Fetch user assignments
        fetchUserAssignments()
    }

    private fun fetchUserAssignments() {
        assignmentApi.getAssignments().enqueue(object : Callback<List<Assignment>> {
            override fun onResponse(
                call: Call<List<Assignment>>,
                response: Response<List<Assignment>>
            ) {
                if (response.isSuccessful) {
                    val assignments = response.body() ?: emptyList()
                    assignmentAdapter = AssignmentAdapter(assignments)
                    recyclerView.adapter = assignmentAdapter
                } else {
                    Toast.makeText(
                        this@ViewAssignmentsActivity,
                        "Failed to fetch assignments: ${response.message()}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<Assignment>>, t: Throwable) {
                Toast.makeText(
                    this@ViewAssignmentsActivity,
                    "Network error: ${t.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }
}
