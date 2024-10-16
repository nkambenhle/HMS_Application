package com.example.hms

import android.net.Uri
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.hms.model.Feedback
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    // RecyclerView for displaying assignments
    private lateinit var assignmentAdapter: AssignmentAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var feedbackAdapter: FeedbackAdapter

    // Media selection launcher
    private val pickMediaLauncher = registerForActivityResult(ActivityResultContracts.GetMultipleContents()) { uris: List<Uri> ->
        // Handle the returned URIs (the photos or videos selected by the user)
        for (uri in uris) {
            // Handle selected media URI, e.g., display or process it
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Step 1: Initialize the RecyclerView
        recyclerView = findViewById(R.id.recyclerView) // Ensure this ID matches your layout
        assignmentAdapter = AssignmentAdapter(emptyList()) // Start with an empty list
        recyclerView.adapter = assignmentAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Step 2: Fetch assignments from the API
        fetchAssignmentsFromApi()

        fetchFeedbacksFromApi()
    }

    // Step 3: Create a method to simulate fetching data
    private fun fetchAssignmentsFromApi() {
        // Example JSON response (you will replace this with your actual API call)
        val jsonResponse = """
        [
            {"id": "1", "title": "Assignment 1", "description": "First assignment", "marks": 100, "dueDate": "2024-10-30"},
            {"id": "2", "title": "Assignment 2", "description": "Second assignment", "marks": 80, "dueDate": "2024-11-05"}
        ]
        """

        // Update the adapter with the fetched assignments
        //assignmentAdapter.updateAssignments(jsonResponse)
    }

    private fun fetchFeedbacksFromApi() {
        // Example JSON response (replace this with your actual API call)
        val jsonResponse = """
        // Your feedbacks JSON array here
    """

        // Convert the JSON response into a list of Feedback objects
        val feedbackList: List<Feedback> = parseFeedbackJson(jsonResponse)

        // Update the adapter with the fetched feedbacks
        //feedbackAdapter.updateFeedback(feedbackList)
    }

    // Parsing method for the feedback JSON
    private fun parseFeedbackJson(jsonResponse: String): List<Feedback> {
        val gson = Gson()
        val feedbackType = object : TypeToken<List<Feedback>>() {}.type
        return gson.fromJson(jsonResponse, feedbackType)
    }

}
