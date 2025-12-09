package com.example.taskiller

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskiller.adapters.UsuarioAdapter
import com.example.taskiller.models.Tarea
import com.example.taskiller.models.Usuario
import Datos
import android.content.Intent
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.Spinner
import com.google.gson.Gson
import java.io.File
import java.io.FileReader
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID

class DetalleTareaActivity : AppCompatActivity() {
    val tareaId = "e5f6a7b8-9c0d-1e2f-3a4b-5c6d7e8f9a0b"
    private lateinit var listaTareas: List<Tarea>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detalle_tarea)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.layoutDetallesTareaMain)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val datos = intent.getSerializableExtra("datos") as Datos
        listaTareas = datos.listaTareas
        val usuarios = datos.listaUsuarios
        val btnVolver = findViewById<ImageButton>(R.id.btnDetalleTareaVolver)

        val tareaActual = intent.getSerializableExtra("tarea") as Tarea
        val user = intent.getSerializableExtra("user") as Usuario

        val usuariosEnTarea = usuarios.filter { it.Id in tareaActual.listaUsuarios } ?: return
        configurarSpinnerEstado()

        MostraRecyclerViewUsuario(usuariosEnTarea)
        MostarNombreDeTarea(tareaActual)
        MostarFecha(tareaActual)
        MostarDescripcion(tareaActual)
        MostraRecyclerViewSubtarea(tareaActual, datos, user)
        MostarEstado(tareaActual)

        btnVolver.setOnClickListener {
            val intent = Intent(this, DetallesProyectoActivity::class.java )
            startActivity(intent)
        }
    }

    fun MostraRecyclerViewUsuario(usuarios: List<Usuario>) {
        val rvDetalleTareaUsuarioAsignado = findViewById<RecyclerView>(R.id.rvDetalleTareaUsuariosAsignado)
        val adapter = UsuarioAdapter(usuarios)
        rvDetalleTareaUsuarioAsignado.hasFixedSize()
        rvDetalleTareaUsuarioAsignado.layoutManager = LinearLayoutManager(this)
        rvDetalleTareaUsuarioAsignado.adapter = adapter
    }

    fun MostraRecyclerViewSubtarea(tarea: Tarea, datos: Datos, user: Usuario) {
        val rvDetalleTareaSubtareas = findViewById<RecyclerView>(R.id.rvDetalleTareaSubtareas)
        val subtareasIds = tarea.Subtareas

        if (subtareasIds != null) {

            val subtareasTarea: MutableList<Tarea> = subtareasIds
                .mapNotNull { id -> obtenerTareaPorId(id) }
                .toMutableList()

            val adapter = MyTaskAdapter(
                subtareasTarea,
                onItemClick = { tarea ->
                    val intent = Intent(this, DetalleTareaActivity::class.java)
                    intent.putExtra("datos", datos)
                    intent.putExtra("tarea", tarea)
                    intent.putExtra("user", user)
                    startActivity(intent)
                }
            )
            rvDetalleTareaSubtareas.hasFixedSize()
            rvDetalleTareaSubtareas.layoutManager = LinearLayoutManager(this)
            rvDetalleTareaSubtareas.adapter = adapter

        }

    }

    fun obtenerTareaPorId(id: UUID): Tarea? {
        return listaTareas.find { it.Id == id }
    }

    fun MostarNombreDeTarea(tarea: Tarea) {
        val lblNombreTarea = findViewById<TextView>(R.id.lblDetalleTareaNombreTarea)
        val nombre = tarea.Titulo
        lblNombreTarea.text = nombre
    }

    fun MostarFecha(tarea: Tarea){
        val lblInicio = findViewById<TextView>(R.id.lblDetalleTareaFechaInicio)
        val lblFinal = findViewById<TextView>(R.id.lblDetalleTareaFechaFinal)

        val inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")

        val outputFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy")

        val fechaInicio = runCatching {
            LocalDateTime.parse(tarea.FechaInicio, inputFormat).format(outputFormat)
        }.getOrElse { tarea.FechaInicio }

        val fechaFinal = runCatching {
            LocalDateTime.parse(tarea.FechaFinal, inputFormat).format(outputFormat)
        }.getOrElse { tarea.FechaFinal }

        lblInicio.text = fechaInicio
        lblFinal.text = fechaFinal
    }

    fun MostarDescripcion(tarea: Tarea){
        val lblDescripcion = findViewById<TextView>(R.id.lblDetalleTareaDescripcion)
        lblDescripcion.setText(tarea.Descripcion)
    }

    fun configurarSpinnerEstado() {
        val spinnerEstado = findViewById<Spinner>(R.id.spinnerDetalleTareaEstado)

        val estados = Tarea.Estados.values()
        val labels = estados.map {
            when (it) {
                Tarea.Estados.Por_Comenzar -> "Por comenzar"
                Tarea.Estados.En_Progreso -> "En progreso"
                Tarea.Estados.Entregado   -> "Entregado"
                Tarea.Estados.Revisado    -> "Revisado"
                Tarea.Estados.Bloqueado   -> "Bloqueado"
            }
        }

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            labels
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerEstado.adapter = adapter


    }


    fun MostarEstado(tarea: Tarea){
        val spinnerEstado = findViewById<Spinner>(R.id.spinnerDetalleTareaEstado)
        spinnerEstado.setSelection(tarea.Estado.ordinal)
    }

}