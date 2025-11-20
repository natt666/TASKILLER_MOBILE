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

class DetallesProyectoActivity : AppCompatActivity() {

    private var datos: Datos? = null
    private var proyecto: Proyecto? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detalles_proyecto)

        val recyclerTareas = findViewById<RecyclerView>(R.id.listDetallesProyectoListaDeTareas)
        val lblNombreProyecto = findViewById<TextView>(R.id.lblDetallesProyectoNombreProyecto)
        val btnVolver = findViewById<Button>(R.id.btnDetallesProyectoBtnVolver)
        val btnAreaPersonal = findViewById<ImageButton>(R.id.btnDetallesProyectoAreaPersonal)

        // Asignar variables de clase desde el Intent
        datos = intent.getParcelableExtra("datos")
        proyecto = intent.getParcelableExtra("proyecto")

        lblNombreProyecto.text = proyecto?.Titulo ?: "Proyecto"

        // Configurar RecyclerView
        recyclerTareas.layoutManager = LinearLayoutManager(this)

        // Filtrar solo las tareas de este proyecto
        val tareasDelProyecto = datos?.listaTareas
            ?.filter { it.IdProyecto == proyecto?.Id }
            ?.toMutableList() ?: mutableListOf()

        val adapter = MyTaskAdapter(
            tareas = tareasDelProyecto,
            onStateChanged = { tarea, nuevoEstado ->
                // Aquí puedes manejar los cambios de estado
            }
        )

        recyclerTareas.adapter = adapter

        // Botón volver
        btnVolver.setOnClickListener { finish() }

        // Botón área personal
        btnAreaPersonal.setOnClickListener {
            Toast.makeText(this, "Área personal clickeada", Toast.LENGTH_SHORT).show()
        }
    }
}
