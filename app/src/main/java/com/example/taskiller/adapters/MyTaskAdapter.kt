package com.example.taskiller
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.taskiller.models.Tarea

class MyTaskAdapter(
    private val tareas: MutableList<Tarea>,
    private val onItemClick: (Tarea) -> Unit
) : RecyclerView.Adapter<MyTaskAdapter.TaskViewHolder>() {

    class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtTitulo: TextView = view.findViewById(R.id.btnTitulo)
        val txtEstado: TextView = view.findViewById(R.id.txtEstado)
        val imgPrioridad: ImageView = view.findViewById(R.id.btnPrioridad)
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_mi_tarea, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val tarea = tareas[position]

        holder.txtTitulo.text = tarea.Titulo

        val context = holder.itemView.context

        val textoEstado: String

        when (tarea.Estado) {
            Tarea.Estados.Por_Comenzar -> {
                textoEstado = "Por comenzar"
            }
            Tarea.Estados.En_Progreso -> {
                textoEstado = "En progreso"
            }
            Tarea.Estados.Entregado -> {
                textoEstado = "Entregado"
            }
            Tarea.Estados.Revisado -> {
                textoEstado = "Revisado"
            }
            Tarea.Estados.Bloqueado -> {
                textoEstado = "Bloqueado"
            }
        }

        when (tarea.Estado) {
            Tarea.Estados.Por_Comenzar -> {
                holder.txtEstado.setBackgroundResource(R.drawable.bg_estado_por_comenzar)
            }
            Tarea.Estados.En_Progreso -> {
                holder.txtEstado.setBackgroundResource(R.drawable.bg_estado_en_progreso)
            }
            Tarea.Estados.Entregado -> {
                holder.txtEstado.setBackgroundResource(R.drawable.bg_estado_entregado)
            }
            Tarea.Estados.Revisado -> {
                holder.txtEstado.setBackgroundResource(R.drawable.bg_estado_revisado)
            }
            Tarea.Estados.Bloqueado -> {
                holder.txtEstado.setBackgroundResource(R.drawable.bg_estado_bloqueado)
            }
        }

        holder.txtEstado.text = textoEstado



        when (tarea.Prioridad) {
            Tarea.Prioridades.ALTA -> {
                holder.imgPrioridad.setImageResource(R.drawable.prioridad_alta)
            }
            Tarea.Prioridades.MEDIA -> {
                holder.imgPrioridad.setImageResource(R.drawable.prioridad_media)
            }
            Tarea.Prioridades.BAJA -> {
                holder.imgPrioridad.setImageResource(R.drawable.prioridad_baja)
            }
        }

        holder.itemView.setOnClickListener {
            onItemClick(tarea)
        }
    }
    override fun getItemCount() = tareas.size
}
