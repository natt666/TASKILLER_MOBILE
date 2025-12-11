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


class TaskAdapter(private val lista: MutableList<Tarea>) :
    RecyclerView.Adapter<TaskAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val lblInicio: TextView = view.findViewById(R.id.lblInicioTarea)
        val lblFin: TextView = view.findViewById(R.id.lblFinTarea)
        val titulo: TextView = view.findViewById(R.id.taskNameTextView)
        val estado: TextView = view.findViewById(R.id.taskStatusTextView)
        val inicio: TextView = view.findViewById(R.id.lblInicioTarea)
        val fin: TextView = view.findViewById(R.id.lblFinTarea)
        val prioridad: TextView = view.findViewById(R.id.prioridadTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_tarea, parent, false)
        return ViewHolder(vista)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = lista[position]
        holder.titulo.text = item.Titulo
        holder.estado.text = item.Estado.toString()
        holder.lblInicio.text = holder.itemView.context.getString(R.string.fecha_inicio_txt)
        holder.lblFin.text = "Fecha fin:"
        holder.inicio.text = item.FechaInicio
        holder.fin.text = item.FechaFinal
        holder.prioridad.text = item.Prioridad.toString()
    }

    override fun getItemCount(): Int = lista.size
}
