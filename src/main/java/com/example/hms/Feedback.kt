package com.example.hms.model

data class Feedback(
    val _id: String,
    val Submission_ID: Submission?,
    val Feedback_Text: String,
    val Marks: Int
)

data class Submission(
    val _id: String,
    val Assignment_ID: String,
    val User_ID: String,
    val Video: String,
    val Date: String
)


