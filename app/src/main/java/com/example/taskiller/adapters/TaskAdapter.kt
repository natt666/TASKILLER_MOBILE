package com.example.taskiller.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.taskiller.R
import com.example.taskiller.models.Tarea
import com.example.taskiller.models.TarjetaTarea
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class TaskAdapter(private val lista: MutableList<Tarea>) :
    RecyclerView.Adapter<TaskAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titulo: TextView = view.findViewById(R.id.taskNameTextView)
        val estado: TextView = view.findViewById(R.id.taskStatusTextView)
        val inicio: TextView = view.findViewById(R.id.startDateTextView)
        val fin: TextView = view.findViewById(R.id.endDateTextView)
        val prioridad: TextView = view.findViewById(R.id.prioridadTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_tarea, parent, false)
        return ViewHolder(vista)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = lista[position]
        var txtEstado : String
        val fechaInicioInput = LocalDateTime.parse(item.FechaInicio, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"))
        val fechaFinalInput = LocalDateTime.parse(item.FechaFinal, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"))
        val fechaInicio = fechaInicioInput.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        val fechaFinal = fechaFinalInput.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))

        when (item.Estado) {
            Tarea.Estados.Por_Comenzar -> {
                txtEstado = "POR COMENZAR"
            }
            Tarea.Estados.En_Progreso -> {
                txtEstado = "EN PROGRESO"
            }
            Tarea.Estados.Entregado -> {
                txtEstado = "ENTREGADO"
            }
            Tarea.Estados.Revisado -> {
                txtEstado = "REVISADO"
            }
            Tarea.Estados.Bloqueado -> {
                txtEstado = "BLOQUEADO"
            }
        }

        when (item.Estado) {
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

        holder.titulo.text = item.Titulo
        holder.estado.text = txtEstado
        holder.inicio.text = fechaInicio
        holder.fin.text = fechaFinal
        holder.prioridad.text = item.Prioridad.toString()
    }

    override fun getItemCount(): Int = lista.size
}
