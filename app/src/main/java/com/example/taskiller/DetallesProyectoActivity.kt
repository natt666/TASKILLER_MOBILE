package com.example.taskiller

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskiller.models.Tarea
import com.example.taskiller.models.Usuario
import Proyecto
import Datos
import java.util.UUID

class DetallesProyectoActivity : AppCompatActivity() {

    private lateinit var datos: Datos
    private lateinit var user: Usuario
    private lateinit var proyecto: Proyecto

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detalles_proyecto)

        val datosCargados = initData()
        if (!datosCargados) {
            return
        }

        initUi()
    }

    private fun initData(): Boolean {
        val datosObtenidos = getDatos()
        if (datosObtenidos == null) {
            Toast.makeText(this, "Error al cargar los datos", Toast.LENGTH_SHORT).show()
            finish()
            return false
        }

        datos = datosObtenidos

        val idBuscado = UUID.fromString("f1a2b3c4-5d6e-7f8a-9b0c-1d2e3f4a5b6c")
        val proyectoEncontrado = datos.listaProyectos.firstOrNull { it.Id == idBuscado }
        val usuarioEncontrado = datos.listaUsuarios.firstOrNull()

        if (proyectoEncontrado == null || usuarioEncontrado == null) {
            Toast.makeText(this, "No se ha encontrado el proyecto o el usuario", Toast.LENGTH_SHORT).show()
            finish()
            return false
        }
        proyecto = proyectoEncontrado
        user = usuarioEncontrado

        return true
    }

    private fun initUi() {
        val recyclerTareas =
            findViewById<RecyclerView>(R.id.listDetallesProyectoListaDeTareas)
        val lblNombreProyecto =
            findViewById<TextView>(R.id.lblDetallesProyectoNombreProyecto)
        val btnVolver =
            findViewById<ImageButton>(R.id.btnDetallesProyectoVolver)
        val btnAreaPersonal =
            findViewById<ImageButton>(R.id.btnDetallesProyectoAreaPersonal)


        lblNombreProyecto.text = proyecto.Titulo

        recyclerTareas.layoutManager = LinearLayoutManager(this)

        mostrarDescripcion(proyecto)

        configurarListaDeTarea(recyclerTareas)

        btnVolver.setOnClickListener {
            finish()
        }

        btnAreaPersonal.setOnClickListener {
            Toast.makeText(this, "Área personal clickeada", Toast.LENGTH_SHORT).show()
        }
    }

    private fun mostrarDescripcion(p: Proyecto) {
        val lblDescripcion =
            findViewById<TextView>(R.id.lblDetallesProyectoDescripcion)
        lblDescripcion.text = p.Descripcion
    }

    private fun mostrarFechas(p: Proyecto) {
        val lblFechaInicio =
            findViewById<TextView>(R.id.lblDetalleProyectoFechaInicio)
        val lblFechaFinal =
            findViewById<TextView>(R.id.lblDetalleProyectoFechaFinal)

        lblFechaInicio.text = p.FechaInicio
        lblFechaInicio.text = p.FechaFinal
    }

    private fun configurarListaDeTarea(rv: RecyclerView) {
        val tareasDelProyecto = datos.listaTareas
            .filter { it.IdProyecto == proyecto.Id }
            .toMutableList()

        val adapter = MyTaskAdapter(
            tareas = tareasDelProyecto,
            onItemClick = { tarea ->
                val intent = Intent(this, DetalleTareaActivity::class.java).apply {
                    putExtra("datos", datos)
                    putExtra("tarea", tarea)
                    putExtra("user", user)
                }
                startActivity(intent)
            }
        )

        rv.adapter = adapter
    }

}
