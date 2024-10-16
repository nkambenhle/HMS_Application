package com.example.hms

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent

class MenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        // Initialize buttons
        val viewAssignmentsButton: Button = findViewById(R.id.viewAssignmentsButton)
        val makeSubmissionButton: Button = findViewById(R.id.makeSubmissionButton)
        val viewFeedbacksButton: Button = findViewById(R.id.viewFeedbacksButton)
        val viewSubmissionButton: Button = findViewById(R.id.viewSubmissionsButton)

        // Set click listener for View Assignments button
        viewAssignmentsButton.setOnClickListener {
            val intent = Intent(this, ViewAssignmentsActivity::class.java)
            startActivity(intent)
        }

        // Set click listener for Make Submission button
        makeSubmissionButton.setOnClickListener {
           val intent = Intent(this, MakeSubmissionActivity::class.java)
            startActivity(intent)
        }

        // Set click listener for View Feedbacks button
        viewFeedbacksButton.setOnClickListener {
           val intent = Intent(this, ViewFeedbacksActivity::class.java)
            startActivity(intent)
        }

        viewSubmissionButton.setOnClickListener {
            val intent = Intent(this, ViewSubmissionsActivity::class.java)
            startActivity(intent)
        }
    }
}
