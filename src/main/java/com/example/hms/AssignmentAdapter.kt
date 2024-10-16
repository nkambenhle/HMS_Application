package com.example.hms

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hms.model.Assignment

class AssignmentAdapter(private val assignments: List<Assignment>) :
    RecyclerView.Adapter<AssignmentAdapter.AssignmentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AssignmentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_assignment, parent, false)
        return AssignmentViewHolder(view)
    }

    override fun onBindViewHolder(holder: AssignmentViewHolder, position: Int) {
        val assignment = assignments[position]

        // Bind the data to the UI elements
        holder.titleTextView.text = assignment.Title
        holder.descriptionTextView.text = assignment.Description
        holder.marksTextView.text = "Marks: ${assignment.Marks}"
        holder.openDateTextView.text = "Open: ${assignment.Open_Date}"
        holder.dueDateTextView.text = "Due: ${assignment.Due_Date}"
        holder.moduleNameTextView.text = assignment.Module_ID.Module_Name
    }

    override fun getItemCount(): Int {
        return assignments.size
    }

    class AssignmentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.textViewTitle)
        val descriptionTextView: TextView = itemView.findViewById(R.id.textViewDescription)
        val marksTextView: TextView = itemView.findViewById(R.id.textViewMarks)
        val openDateTextView: TextView = itemView.findViewById(R.id.textViewOpenDate)
        val dueDateTextView: TextView = itemView.findViewById(R.id.textViewDueDate)
        val moduleNameTextView: TextView = itemView.findViewById(R.id.textViewModuleName)
    }
}
