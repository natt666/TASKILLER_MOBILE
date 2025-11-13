package com.example.taskiller.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.taskiller.R
import com.example.taskiller.models.TarjetaTarea

class TaskAdapter(private val lista: List<TarjetaTarea>) :
    RecyclerView.Adapter<TaskAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titulo: TextView = view.findViewById(R.id.taskNameTextView)
        val estado: TextView = view.findViewById(R.id.taskStatusTextView)
        val fechaIni: TextView = view.findViewById(R.id.startDateTextView)
        val fechaFin: TextView = view.findViewById(R.id.endDateTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_tarea, parent, false)
        return ViewHolder(vista)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = lista[position]
        holder.titulo.text = item.titulo
        holder.estado.text = item.estado
        holder.fechaIni.text = item.fechaInicio as CharSequence?
        holder.fechaFin.text = item.fechaFin as CharSequence?
    }

    override fun getItemCount(): Int = lista.size
}
