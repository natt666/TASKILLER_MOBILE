package com.example.taskiller

import Proyecto
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

class PaginaPrincipalActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProjectAdapter
    private val projectList = mutableListOf<Proyecto>()

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

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        recyclerView = findViewById(R.id.recyclerViewProjects)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = ProjectAdapter(
            projectList,
            onProjectClick = { Proyecto ->
                    Toast.makeText(this, "Projecte: ${Proyecto.Titulo}", Toast.LENGTH_SHORT).show()
            },
            onChartClick = { Proyecto ->
                Toast.makeText(this, "Gràfic de: ${Proyecto.Titulo}", Toast.LENGTH_SHORT).show()
            },
            onTaskCountClick = { Proyecto ->
                Toast.makeText(this, "${Proyecto.Titulo} tasques", Toast.LENGTH_SHORT).show()
            },
            onDeadlineClick = { project ->
                Toast.makeText(this, "Data límit: ${Proyecto.Estados.Por_Comenzar}", Toast.LENGTH_SHORT).show()
            }
                                )

        recyclerView.adapter = adapter
    }




}