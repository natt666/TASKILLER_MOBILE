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
import android.widget.LinearLayout
import android.widget.NumberPicker
import android.widget.Spinner
import com.google.gson.Gson
import java.io.File
import java.io.FileReader
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID

class DetalleTareaActivity : AppCompatActivity() {
    private lateinit var listaTareas: List<Tarea>
    private var horas = 0
    private var minutos = 0

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

        val txtTotal = findViewById<TextView>(R.id.total)
        val totalMin = tareaActual.DuracionMinutos
        horas = totalMin / 60
        minutos = totalMin % 60

        txtTotal.text = "${horas}h ${minutos}m"

        val layoutTiempo = findViewById<LinearLayout>(R.id.layoutDetalleTareaTiempo)
        layoutTiempo.setOnClickListener {
            mostrarDialogoTiempo(txtTotal, tareaActual, datos)
        }

        val usuariosEnTarea = usuarios.filter { it.Id in tareaActual.listaUsuarios } ?: return
        configurarSpinnerEstado()

        MostraRecyclerViewUsuario(usuariosEnTarea)
        MostarNombreDeTarea(tareaActual)
        MostarFecha(tareaActual)
        MostarDescripcion(tareaActual)
        MostraRecyclerViewSubtarea(tareaActual, datos, user)
        MostarEstado(tareaActual)

        btnVolver.setOnClickListener {
            finish()
        }
    }

    private fun mostrarDialogoTiempo(txtTotal: TextView, tareaActual: Tarea, datos: Datos) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_tiempo_tarea, null)

        val npHoras = dialogView.findViewById<NumberPicker>(R.id.npHoras)
        val npMinutos = dialogView.findViewById<NumberPicker>(R.id.npMinutos)

        npHoras.minValue = 0
        npHoras.maxValue = 24
        npMinutos.minValue = 0
        npMinutos.maxValue = 59

        val totalMin = tareaActual.DuracionMinutos
        val currentHoras = totalMin / 60
        val currentMinutos = totalMin % 60

        npHoras.value = currentHoras
        npMinutos.value = currentMinutos

        androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle(getString(R.string.horas_dedicadas))
            .setView(dialogView)
            .setPositiveButton(android.R.string.ok) { _, _ ->
                val h = npHoras.value
                val m = npMinutos.value
                val duracionMinutos = h * 60 + m

                tareaActual.DuracionMinutos = duracionMinutos

                horas = h
                minutos = m
                txtTotal.text = "${h}h ${m}m"

                guardarDatos(datos)
            }
            .setNegativeButton(android.R.string.cancel, null)
            .show()
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