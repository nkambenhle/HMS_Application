package com.example.hms

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hms.model.Feedback

class FeedbackAdapter(private val feedbacks: List<Feedback>) :
    RecyclerView.Adapter<FeedbackAdapter.FeedbackViewHolder>() {

    class FeedbackViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val feedbackTextView: TextView = itemView.findViewById(R.id.feedbackText) // Correct ID
        val marksTextView: TextView = itemView.findViewById(R.id.marksText) // Correct ID
        val videoTextView: TextView = itemView.findViewById(R.id.videoText) // Add this for video text
        val assignmentIdTextView: TextView = itemView.findViewById(R.id.assignmentIdText) // For assignment ID
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedbackViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_feedback, parent, false)
        return FeedbackViewHolder(view)
    }

    override fun onBindViewHolder(holder: FeedbackViewHolder, position: Int) {
        val feedback = feedbacks[position]
        holder.feedbackTextView.text = feedback.Feedback_Text
        holder.marksTextView.text = "Marks: ${feedback.Marks}"
        holder.videoTextView.text = feedback.Submission_ID?.Video ?: "No Video" // Update with actual video URL or handle null
        holder.assignmentIdTextView.text = feedback.Submission_ID?.Assignment_ID ?: "No Assignment ID" // Update accordingly
    }

    override fun getItemCount(): Int = feedbacks.size
}
