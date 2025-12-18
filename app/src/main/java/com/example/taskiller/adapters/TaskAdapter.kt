package com.example.taskiller.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.taskiller.R
import com.example.taskiller.models.Tarea
import com.example.taskiller.models.Usuario
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class TaskAdapter(
    private val user: Usuario,
    private val tareas: MutableList<Tarea>,
    private val onCardClick: (Tarea) -> Unit) : RecyclerView.Adapter<TaskAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titulo: TextView = view.findViewById(R.id.taskNameTextView)
        val estado: TextView = view.findViewById(R.id.taskStatusTextView)
        val inicio: TextView = view.findViewById(R.id.startDateTextView)
        val fin: TextView = view.findViewById(R.id.endDateTextView)
        val prioridad: ImageView = view.findViewById(R.id.prioridadImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_tarea, parent, false)
        return ViewHolder(vista)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tarea = tareas[position]
        val fechaInicioInput = LocalDateTime.parse(tarea.FechaInicio,
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"))
        val fechaFinalInput = LocalDateTime.parse(tarea.FechaFinal,
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"))
        val fechaInicio = fechaInicioInput.format(
            DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        val fechaFinal = fechaFinalInput.format(
            DateTimeFormatter.ofPattern("dd/MM/yyyy"))

        val context = holder.itemView.context

        val estadosArray = context.resources.getStringArray(R.array.estados)

        val txtEstado = when (tarea.Estado) {
            Tarea.Estados.Por_Comenzar -> estadosArray[0]
            Tarea.Estados.En_Progreso -> estadosArray[1]
            Tarea.Estados.Entregado -> estadosArray[2]
            Tarea.Estados.Revisado -> estadosArray[3]
            Tarea.Estados.Bloqueado -> estadosArray[4]
        }

        when (tarea.Estado) {
            Tarea.Estados.Por_Comenzar -> {
                holder.estado.setBackgroundResource(R.drawable.bg_estado_por_comenzar)
            }
            Tarea.Estados.En_Progreso -> {
                holder.estado.setBackgroundResource(R.drawable.bg_estado_en_progreso)
            }
            Tarea.Estados.Entregado -> {
                holder.estado.setBackgroundResource(R.drawable.bg_estado_entregado)
            }
            Tarea.Estados.Revisado -> {
                holder.estado.setBackgroundResource(R.drawable.bg_estado_revisado)
            }
            Tarea.Estados.Bloqueado -> {
                holder.estado.setBackgroundResource(R.drawable.bg_estado_bloqueado)
            }
        }

        when (tarea.Prioridad) {
            Tarea.Prioridades.BAJA -> {
                holder.prioridad.setImageResource(R.drawable.prioridad_baja)
            }
            Tarea.Prioridades.MEDIA -> {
                holder.prioridad.setImageResource(R.drawable.prioridad_media)
            }
            Tarea.Prioridades.ALTA -> {
                holder.prioridad.setImageResource(R.drawable.prioridad_alta)
            }
        }
        holder.titulo.text = tarea.Titulo
        holder.estado.text = txtEstado
        holder.inicio.text = fechaInicio
        holder.fin.text = fechaFinal
        holder.itemView.setOnClickListener {onCardClick(tarea)}
    }
    override fun getItemCount(): Int = tareas.size
}
