package com.example.hms

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hms.model.Submission

class SubmissionAdapter(private val submissions: List<Submission>) :
    RecyclerView.Adapter<SubmissionAdapter.SubmissionViewHolder>() {

    class SubmissionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.submissionTitle)
        val dateTextView: TextView = itemView.findViewById(R.id.submissionDate)
        val videoTextView: TextView = itemView.findViewById(R.id.videoTextView) // Assuming there's a TextView to display video URL
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubmissionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_submission, parent, false)
        return SubmissionViewHolder(view)
    }

    override fun onBindViewHolder(holder: SubmissionViewHolder, position: Int) {
        val submission = submissions[position]
        holder.titleTextView.text = "Assignment ID: ${submission.Assignment_ID}"
        holder.dateTextView.text = "Date: ${submission.Date}"
        holder.videoTextView.text = submission.Video // Display video URL or similar text
    }

    override fun getItemCount(): Int = submissions.size
}
