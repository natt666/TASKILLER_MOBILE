package com.example.taskiller

import Proyecto
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ProjectAdapter(
    private val projects: MutableList<Proyecto>,
    private val onProjectClick: (Project) -> Unit,
    private val onChartClick: (Project) -> Unit,
    private val onTaskCountClick: (Project) -> Unit,
    private val onDeadlineClick: (Project) -> Unit
                    ) : RecyclerView.Adapter<ProjectAdapter.ProjectViewHolder>() {

    class ProjectViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val btnProjectName: Button = view.findViewById(R.id.btnProjectName)
        val btnChart: ImageButton = view.findViewById(R.id.btnChart)
        val txtViewTaskCount: TextView = view.findViewById(R.id.txtViewTaskCount)
        val txtViewDeadline: TextView = view.findViewById(R.id.txtViewDeadline)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.targeta_proyecto_control, parent, false)
        return ProjectViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        val project = projects[position]

        holder.btnProjectName.text = project.Titulo
        holder.txtViewTaskCount.text = "${project.taskCount} tareas"
        holder.txtViewDeadline.text = project.deadline

        holder.btnProjectName.setOnClickListener { onProjectClick(project) }
        holder.btnChart.setOnClickListener { onChartClick(project) }
        holder.txtViewTaskCount.setOnClickListener { onTaskCountClick(project) }
        holder.txtViewDeadline.setOnClickListener { onDeadlineClick(project) }
    }

    override fun getItemCount() = projects.size

    fun addProject(project: Project) {
        projects.add(project)
        notifyItemInserted(projects.size - 1)
    }
}