package com.example.taskiller
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.taskiller.models.Tarea

class MyTaskAdapter(
    private val tareas: MutableList<Tarea>,
    private val onStateChanged: (Tarea, Tarea.Estados) -> Unit
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

        // Asignar valores
        holder.txtTitulo.text = tarea.Titulo

        // Configurar el spinner de estado
        val context = holder.itemView.context

        holder.txtEstado.text = when (tarea.Estado) {
            Tarea.Estados.Por_Comenzar -> "Por comenzar"
            Tarea.Estados.En_Progreso -> "En progreso"
            Tarea.Estados.Entregado   -> "Entregado"
            Tarea.Estados.Revisado    -> "Revisado"
            Tarea.Estados.Bloqueado   -> "Bloqueado"
        }

        // Colores según prioridad
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

        // Listener de clic en el título y prioridad

    }

    override fun getItemCount() = tareas.size

    fun addTask(tarea: Tarea) {
        tareas.add(tarea)
        notifyItemInserted(tareas.size - 1)
    }
}
