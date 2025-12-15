package com.example.taskiller

import Datos
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskiller.adapters.TaskAdapter
import com.example.taskiller.models.Tarea
import com.example.taskiller.models.Usuario

class MisTareasActivity : AppCompatActivity() {
    private lateinit var datos: Datos
    private lateinit var user: Usuario
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_mis_tareas)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        datos =intent.getSerializableExtra("datos") as Datos
        user =intent.getSerializableExtra("user") as Usuario
        val recyclerTareas = findViewById<RecyclerView>(R.id.rVTareas)
        recyclerTareas.layoutManager = LinearLayoutManager(this)
        val tareas = mutableListOf<Tarea>()
        val btnVolver = findViewById<ImageButton>(R.id.btnMisTareasVolver)
        for (tarea in datos.listaTareas) {
            if (tarea.listaUsuarios.contains(user.Id)) {
                tareas.add(tarea)
            }
        }
        val adapter = TaskAdapter(
            tareas,
            onCardClick = { tarea ->
                val intent = Intent(this, DetalleTareaActivity::class.java).apply {
                    putExtra("datos", datos)
                    putExtra("tarea", tarea)
                    putExtra("user", user)
                }
                startActivity(intent)
            })
        recyclerTareas.adapter = adapter
        btnVolver.setOnClickListener {
            finish()
        }
    }
}