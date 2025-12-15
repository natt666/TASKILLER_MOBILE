package com.example.taskiller

import Datos
import Proyecto
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.taskiller.models.Usuario
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ProjectAdapter(
    private val projects: MutableList<Proyecto>,
    private val datos: Datos,
    private val user: Usuario,
    private val onItemClick: (Proyecto) -> Unit
                    ) : RecyclerView.Adapter<ProjectAdapter.ProjectViewHolder>() {

    class ProjectViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val btnProjectName: TextView = view.findViewById(R.id.txtProjectName)
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

        val inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
        val outputFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy")

        val fechaFinal = runCatching {
            LocalDateTime.parse(project.FechaFinal, inputFormat).format(outputFormat)
        }.getOrElse { project.FechaFinal }


        var contadorTareas = 0
        for (tarea in datos.listaTareas){
            if (tarea.IdProyecto == project.Id){
                contadorTareas++
            }
        }

        holder.btnProjectName.text = project.Titulo
        holder.txtViewDeadline.text = fechaFinal
        holder.txtViewTaskCount.text = contadorTareas.toString()

        holder.itemView.setOnClickListener { onItemClick(project) }

        holder.btnChart.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, GraficosActivity::class.java).apply {
                putExtra("datos", datos)
                putExtra("user", user)
                putExtra("proyecto", project)
            }
            context.startActivity(intent)
        }
    }
    override fun getItemCount() = projects.size
}