package com.example.taskiller

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView

class ProjectAdapter(
    private val projects: MutableList<Project>,
    private val onProjectClick: (Project) -> Unit,
    private val onChartClick: (Project) -> Unit,
    private val onTaskCountClick: (Project) -> Unit,
    private val onDeadlineClick: (Project) -> Unit
                    ) : RecyclerView.Adapter<ProjectAdapter.ProjectViewHolder>() {

    class ProjectViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val btnProjectName: Button = view.findViewById(R.id.btnProjectName)
        val btnChart: ImageButton = view.findViewById(R.id.btnChart)
        val btnTaskCount: Button = view.findViewById(R.id.btnTaskCount)
        val btnDeadline: Button = view.findViewById(R.id.btnDeadline)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.targeta_proyecto_control, parent, false)
        return ProjectViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        val project = projects[position]

        holder.btnProjectName.text = project.name
        holder.btnTaskCount.text = "${project.taskCount} tareas"
        holder.btnDeadline.text = project.deadline

        holder.btnProjectName.setOnClickListener { onProjectClick(project) }
        holder.btnChart.setOnClickListener { onChartClick(project) }
        holder.btnTaskCount.setOnClickListener { onTaskCountClick(project) }
        holder.btnDeadline.setOnClickListener { onDeadlineClick(project) }
    }

    override fun getItemCount() = projects.size

    fun addProject(project: Project) {
        projects.add(project)
        notifyItemInserted(projects.size - 1)
    }
}