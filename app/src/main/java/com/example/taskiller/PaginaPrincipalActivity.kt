package com.example.taskiller

import Datos
import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskiller.models.Usuario

class PaginaPrincipalActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProjectAdapter
    private val projectList = mutableListOf<Project>()

    private lateinit var datos: Datos

    private lateinit var user: Usuario


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pagina_principal)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.tarjproyecto)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        datos = intent.getSerializableExtra("datos") as Datos
        user = intent.getSerializableExtra("user") as Usuario

        setupRecyclerView()
        setupAddButton()
        addSampleProjects() // ← AIXÒ ÉS IMPORTANT!
    }

    private fun setupRecyclerView() {
        recyclerView = findViewById(R.id.recyclerViewProjects)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = ProjectAdapter(
            datos.listaProyectos,
            onProjectClick = { project ->
                Toast.makeText(this, "Projecte: ${project.name}", Toast.LENGTH_SHORT).show()
            },
            onChartClick = { project ->
                Toast.makeText(this, "Gràfic de: ${project.name}", Toast.LENGTH_SHORT).show()
            },
            onTaskCountClick = { project ->
                Toast.makeText(this, "${project.taskCount} tasques", Toast.LENGTH_SHORT).show()
            },
            onDeadlineClick = { project ->
                Toast.makeText(this, "Data límit: ${project.deadline}", Toast.LENGTH_SHORT).show()
            }
                                )

        recyclerView.adapter = adapter
    }

    private fun setupAddButton() {
        val btnAddCard: Button = findViewById(R.id.btnAddCard)
        btnAddCard.setOnClickListener {
            val newProject = Project(
                name = "Projecte ${projectList.size + 1}",
                taskCount = (1..10).random(),
                deadline = "31/12/2024"
                                    )
            adapter.addProject(newProject)
        }
    }

    private fun addSampleProjects() {
        projectList.add(Project("App Mòbil", 5, "15/11/2024"))
        projectList.add(Project("Web Empresa", 8, "20/12/2024"))
        projectList.add(Project("Base de Dades", 3, "10/11/2024"))
        adapter.notifyDataSetChanged()
    }
}