package com.example.taskiller.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.taskiller.R
import com.example.taskiller.models.Usuario

class UsuarioAdapter(
    private val usuarios: List<Usuario>
) : RecyclerView.Adapter<UsuarioAdapter.UsuarioViewHolder>() {

    private val layout = R.layout.item_usuario

    class UsuarioViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgItemUsuario: ImageView = view.findViewById(R.id.imgItemUsuario)
        val txtItemUsuario: TextView = view.findViewById(R.id.txtItemUsuario)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsuarioViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return UsuarioViewHolder(view)
    }

    override fun onBindViewHolder(holder: UsuarioViewHolder, position: Int) {
        val u = usuarios[position]
        holder.txtItemUsuario.text = u.nombre
        holder.imgItemUsuario.setImageResource(R.drawable.logo_user_azu)
    }

    override fun getItemCount(): Int = usuarios.size
}
