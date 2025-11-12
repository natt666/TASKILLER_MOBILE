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
import com.example.taskiller.utils.Datos
import com.google.gson.Gson
import java.io.File
import java.io.FileReader
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DetalleTareaActivity : AppCompatActivity() {
    val tareaId = "f6a7b8c9-0d1e-2f3a-4b5c-6d7e8f9a0b1c";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detalle_tarea)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.layoutDetallesTareaMain)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val datos = getDatos() ?: return
        val tareas = datos.listaTareas
        val usuarios = datos.listaUsuarios

        val tareaActual = tareas.find { it.Id.toString() == tareaId } ?: return
        val usuariosEnTarea = usuarios.filter { it.Id in tareaActual.listaUsuarios } ?: return

        MostraRecyclerViewUsuario(usuariosEnTarea)
        MostarNombreDeTarea(tareaActual)
        MostarFecha(tareaActual)
        MostarDescripcion(tareaActual)
    }

    fun MostraRecyclerViewUsuario(usuarios: List<Usuario>) {
        val lstDetalleTareaUsuarioAsignado = findViewById<RecyclerView>(R.id.lstDetalleTareaUsuarioAsignado)
        val adapter = UsuarioAdapter(usuarios)
        lstDetalleTareaUsuarioAsignado.hasFixedSize()
        lstDetalleTareaUsuarioAsignado.layoutManager = LinearLayoutManager(this)
        lstDetalleTareaUsuarioAsignado.adapter = adapter
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


    fun getDatos(): Datos? {
        return try {
            val jsonFile = File(filesDir, "json/TaskillerData.json")

            if (!jsonFile.exists()) {
                Log.e("LoginActivity", "Archivo JSON no encontrado: ${jsonFile.absolutePath}")
                return null
            }

            val gson = Gson()
            FileReader(jsonFile).use { reader ->
                gson.fromJson(reader, Datos::class.java)
            }

        } catch (e: Exception) {
            Log.e("LoginActivity", "Error leyendo JSON", e)
            null
        }

    }
}