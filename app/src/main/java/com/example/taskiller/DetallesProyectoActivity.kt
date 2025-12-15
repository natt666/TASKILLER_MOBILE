package com.example.taskiller

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskiller.models.Tarea
import com.example.taskiller.models.Usuario
import Datos
import Proyecto
import java.text.SimpleDateFormat
import java.util.Locale

class DetallesProyectoActivity : AppCompatActivity() {

    private lateinit var datos: Datos
    private lateinit var user: Usuario
    private lateinit var proyecto: Proyecto

    private lateinit var recyclerTareas: RecyclerView
    private lateinit var adapter: MyTaskAdapter
    private val tareasDelProyecto: MutableList<Tarea> = mutableListOf()

    private val detalleLauncher =
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

                cargarTareasDelProyecto()
                adapter.notifyDataSetChanged()
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

                cargarTareasDelProyecto()
                adapter.notifyDataSetChanged()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detalles_proyecto)

        if (initData()) {
            initUi()
        }
    }

    private fun initData(): Boolean {
        val proyectoEncontrado = intent.getSerializableExtra("proyecto") as? Proyecto
        val usuarioEncontrado = intent.getSerializableExtra("user") as? Usuario
        val datosExtra = intent.getSerializableExtra("datos") as? Datos

        if (datosExtra == null || proyectoEncontrado == null || usuarioEncontrado == null) {
            Toast.makeText(this, "Error al cargar los datos", Toast.LENGTH_SHORT).show()
            finish()
            return false
        }

        datos = datosExtra
        proyecto = proyectoEncontrado
        user = usuarioEncontrado
        return true
    }

    private fun initUi() {
        recyclerTareas = findViewById(R.id.listDetallesProyectoListaDeTareas)

        val lblNombreProyecto: TextView = findViewById(R.id.lblDetallesProyectoNombreProyecto)
        val btnVolver: ImageButton = findViewById(R.id.btnDetallesProyectoVolver)
        val btnAreaPersonal: ImageButton = findViewById(R.id.btnDetallesProyectoAreaPersonal)

        lblNombreProyecto.text = proyecto.Titulo
        mostrarDescripcion(proyecto)
        mostrarFechas(proyecto)

        recyclerTareas.layoutManager = LinearLayoutManager(this)

        cargarTareasDelProyecto()
        adapter = MyTaskAdapter(
            tareas = tareasDelProyecto,
            onItemClick = { tarea ->
                val intent = Intent(this, DetalleTareaActivity::class.java).apply {
                    putExtra("datos", datos)
                    putExtra("tarea", tarea)
                    putExtra("user", user)
                }
                detalleLauncher.launch(intent)
            }
        )
        recyclerTareas.adapter = adapter

        btnVolver.setOnClickListener {
            val result = Intent().apply {
                putExtra("datos", datos)
                putExtra("user", user)
            }
            setResult(Activity.RESULT_OK, result)
            finish()
        }

        btnAreaPersonal.setOnClickListener {
            val intent = Intent(this, MisTareasActivity::class.java).apply {
                putExtra("datos", datos)
                putExtra("user", user)
            }
            misTareasLauncher.launch(intent)
        }
    }

    private fun cargarTareasDelProyecto() {
        tareasDelProyecto.clear()
        tareasDelProyecto.addAll(datos.listaTareas.filter { it.IdProyecto == proyecto.Id })
    }

    private fun mostrarDescripcion(p: Proyecto) {
        val lblDescripcion: TextView = findViewById(R.id.lblDetallesProyectoDescripcion)
        lblDescripcion.text = p.Descripcion
    }

    private fun mostrarFechas(p: Proyecto) {
        val lblFechaInicio: TextView = findViewById(R.id.lblDetalleProyectoFechaInicio)
        val lblFechaFinal: TextView = findViewById(R.id.lblDetalleProyectoFechaFinal)

        val formatoOriginal = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val formatoDeseado = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        val fechaInicioFormateada = formatoDeseado.format(formatoOriginal.parse(p.FechaInicio)!!)
        val fechaFinalFormateada = formatoDeseado.format(formatoOriginal.parse(p.FechaFinal)!!)

        lblFechaInicio.text = fechaInicioFormateada
        lblFechaFinal.text = fechaFinalFormateada
    }
}
