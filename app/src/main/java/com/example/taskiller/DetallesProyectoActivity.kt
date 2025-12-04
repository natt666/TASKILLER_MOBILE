package com.example.taskiller

import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import Proyecto
import Datos
import android.content.Intent
import com.example.taskiller.models.Tarea
import com.example.taskiller.models.Usuario
import java.util.UUID

class DetallesProyectoActivity : AppCompatActivity() {

    private lateinit var datos: Datos
    private lateinit var user: Usuario
    private lateinit var proyecto: Proyecto

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detalles_proyecto)

        val recyclerTareas = findViewById<  RecyclerView>(R.id.listDetallesProyectoListaDeTareas)
        val lblNombreProyecto = findViewById<TextView>(R.id.lblDetallesProyectoNombreProyecto)
        val btnVolver = findViewById<ImageButton>(R.id.btnDetallesProyectoVolver)
        val btnAreaPersonal = findViewById<ImageButton>(R.id.btnDetallesProyectoAreaPersonal)

        this.datos = getDatos()!!;
        val idBuscado = UUID.fromString("f1a2b3c4-5d6e-7f8a-9b0c-1d2e3f4a5b6c")
        this.proyecto = datos.listaProyectos.firstOrNull { it.Id == idBuscado }!!
        this.user = datos.listaUsuarios.firstOrNull()!!

        lblNombreProyecto.text = proyecto?.Titulo ?: "Proyecto"

        recyclerTareas.layoutManager = LinearLayoutManager(this)

        MostarDescripcion(proyecto)

        val tareasDelProyecto = datos?.listaTareas
            ?.filter { it.IdProyecto == proyecto?.Id }
            ?.toMutableList() ?: mutableListOf()

        val adapter = MyTaskAdapter(
            tareas = tareasDelProyecto,
            onItemClick = { tarea ->
                val intent = Intent(this, DetalleTareaActivity::class.java)
                intent.putExtra("datos", datos)
                intent.putExtra("tarea", tarea)
                intent.putExtra("user", user)
                startActivity(intent)
            }
        )

        recyclerTareas.adapter = adapter

        btnVolver.setOnClickListener { finish() }

        btnAreaPersonal.setOnClickListener {
            Toast.makeText(this, "Área personal clickeada", Toast.LENGTH_SHORT).show()
        }
    }

    fun MostarDescripcion(Proyecto: Proyecto){
        val lblDescripcion = findViewById<TextView>(R.id.lblDetallesProyectoDescripcion)
        lblDescripcion.setText(Proyecto.Descripcion)
    }
}
