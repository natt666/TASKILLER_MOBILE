package com.example.taskiller

import Datos
import Proyecto
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskiller.models.Usuario

class PaginaPrincipalActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProjectAdapter

    private lateinit var datos: Datos
    private lateinit var user: Usuario

    private val proyectosColaborador: MutableList<Proyecto> = mutableListOf()

    private val detallesProyectoLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                val datosDevueltos = data?.getSerializableExtra("datos") as? Datos
                val userDevuelto = data?.getSerializableExtra("user") as? Usuario

                if (datosDevueltos != null) {
                    datos = datosDevueltos
                }
                if (userDevuelto != null) {
                    user = userDevuelto
                }
                adapter.updateContext(datos, user)
                refrescarLista()
            }
        }

    private val misTareasLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                val datosDevueltos = data?.getSerializableExtra("datos") as? Datos
                val userDevuelto = data?.getSerializableExtra("user") as? Usuario

                if (datosDevueltos != null) {
                    datos = datosDevueltos
                }
                if (userDevuelto != null) {
                    user = userDevuelto
                }
                adapter.updateContext(datos, user)
                refrescarLista()
            }
        }

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
        refrescarLista()
        setupBotones()
    }

    private fun setupRecyclerView() {
        recyclerView = findViewById(R.id.recyclerViewProjects)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = ProjectAdapter(
            proyectosColaborador,
            datos,
            user,
            onItemClick = { proyecto ->
                val intent = Intent(this, DetallesProyectoActivity::class.java).apply {
                    putExtra("proyecto", proyecto)
                    putExtra("datos", datos)
                    putExtra("user", user)
                }
                detallesProyectoLauncher.launch(intent)
            }
        )

        recyclerView.adapter = adapter
    }

    private fun setupBotones() {
        val logo = findViewById<ImageButton>(R.id.logo)
        logo.setOnClickListener {
            val intent = Intent(this, MisTareasActivity::class.java).apply {
                putExtra("datos", datos)
                putExtra("user", user)
            }
            misTareasLauncher.launch(intent)
        }
    }

    private fun refrescarLista() {
        proyectosColaborador.clear()
        proyectosColaborador.addAll(
            datos.listaProyectos.filter { proyecto ->
                proyecto.listaUsuarios.any { it == user.Id }
            }
        )
        adapter.notifyDataSetChanged()
    }
}
