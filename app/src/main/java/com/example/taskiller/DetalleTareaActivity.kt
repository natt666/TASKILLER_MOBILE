package com.example.taskiller

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskiller.adapters.UsuarioAdapter
import com.example.taskiller.models.Tarea
import com.example.taskiller.models.Usuario
import Datos
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID

class DetalleTareaActivity : AppCompatActivity() {

    private lateinit var datos: Datos
    private lateinit var user: Usuario
    private lateinit var tareaId: UUID
    private lateinit var listaTareas: List<Tarea>
    private lateinit var usuarios: List<Usuario>

    private lateinit var txtTotal: TextView
    private lateinit var btnVolver: ImageButton

    private lateinit var spinnerEstado: Spinner
    private var ignorarEventoSpinner: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detalle_tarea)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.layoutDetallesTareaMain)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        datos = intent.getSerializableExtra("datos") as Datos
        user = intent.getSerializableExtra("user") as Usuario
        val tareaIntent = intent.getSerializableExtra("tarea") as Tarea
        tareaId = tareaIntent.Id!!

        listaTareas = datos.listaTareas
        usuarios = datos.listaUsuarios

        txtTotal = findViewById(R.id.total)
        btnVolver = findViewById(R.id.btnDetalleTareaVolver)

        spinnerEstado = findViewById(R.id.spinnerDetalleTareaEstado)
        configurarSpinnerEstado()


        findViewById<LinearLayout>(R.id.layoutDetalleTareaTiempo).setOnClickListener {
            mostrarDialogoTiempo()
        }

        btnVolver.setOnClickListener {
            devolverResultadoYSalir()
        }

        refrescarPantalla()
    }

    private val subTareaLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data

                val datosDevueltos = data?.getSerializableExtra("datos") as? Datos
                val userDevuelto = data?.getSerializableExtra("user") as? Usuario

                if (datosDevueltos != null) datos = datosDevueltos
                if (userDevuelto != null) user = userDevuelto

                listaTareas = datos.listaTareas
                usuarios = datos.listaUsuarios

                refrescarPantalla()
            }
        }

    private fun devolverResultadoYSalir() {
        val result = Intent().apply {
            putExtra("datos", datos)
            putExtra("user", user)
        }
        setResult(Activity.RESULT_OK, result)
        finish()
    }

    private fun refrescarPantalla() {
        val tarea = datos.listaTareas.firstOrNull { it.Id == tareaId } ?: return

        val totalMin = tarea.DuracionMinutos
        val h = totalMin / 60
        val m = totalMin % 60
        txtTotal.text = "${h}h ${m}m"

        val usuariosEnTarea = usuarios.filter { it.Id in tarea.listaUsuarios }
        MostraRecyclerViewUsuario(usuariosEnTarea)

        MostarNombreDeTarea(tarea)
        MostarFecha(tarea)
        MostarDescripcion(tarea)
        MostraRecyclerViewSubtarea(tarea, datos, user)
        MostarEstado(tarea)
    }

    private fun mostrarDialogoTiempo() {
        val tarea = datos.listaTareas.firstOrNull { it.Id == tareaId } ?: return

        val dialogView = layoutInflater.inflate(R.layout.dialog_tiempo_tarea, null)
        val npHoras = dialogView.findViewById<NumberPicker>(R.id.npHoras)
        val npMinutos = dialogView.findViewById<NumberPicker>(R.id.npMinutos)

        npHoras.minValue = 0
        npHoras.maxValue = 24
        npMinutos.minValue = 0
        npMinutos.maxValue = 59

        val totalMin = tarea.DuracionMinutos
        npHoras.value = totalMin / 60
        npMinutos.value = totalMin % 60

        androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle(getString(R.string.horas_dedicadas))
            .setView(dialogView)
            .setPositiveButton(android.R.string.ok) { _, _ ->
                val duracionMinutos = npHoras.value * 60 + npMinutos.value

                val tareaEnDatos = datos.listaTareas.firstOrNull { it.Id == tareaId }
                if (tareaEnDatos != null) {
                    tareaEnDatos.DuracionMinutos = duracionMinutos
                }

                txtTotal.text = "${npHoras.value}h ${npMinutos.value}m"
                guardarDatos(datos)
            }
            .setNegativeButton(android.R.string.cancel, null)
            .show()
    }

    fun MostraRecyclerViewUsuario(usuarios: List<Usuario>) {
        val rv = findViewById<RecyclerView>(R.id.rvDetalleTareaUsuariosAsignado)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = UsuarioAdapter(usuarios)
    }

    fun MostraRecyclerViewSubtarea(tarea: Tarea, datos: Datos, user: Usuario) {
        val rv = findViewById<RecyclerView>(R.id.rvDetalleTareaSubtareas)
        val subtareasIds = tarea.Subtareas

        if (subtareasIds != null) {
            val subtareasTarea = subtareasIds
                .mapNotNull { id -> listaTareas.find { it.Id == id } }
                .toMutableList()

            val adapter = MyTaskAdapter(
                subtareasTarea,
                onItemClick = { subTarea ->
                    val intent = Intent(this, DetalleTareaActivity::class.java).apply {
                        putExtra("datos", datos)
                        putExtra("tarea", subTarea)
                        putExtra("user", user)
                    }
                    subTareaLauncher.launch(intent)
                }
            )

            rv.layoutManager = LinearLayoutManager(this)
            rv.adapter = adapter
        }
    }

    fun MostarNombreDeTarea(tarea: Tarea) {
        findViewById<TextView>(R.id.lblDetalleTareaNombreTarea).text = tarea.Titulo
    }

    fun MostarFecha(tarea: Tarea) {
        val lblInicio = findViewById<TextView>(R.id.lblDetalleTareaFechaInicio)
        val lblFinal = findViewById<TextView>(R.id.lblDetalleTareaFechaFinal)

        val inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
        val outputFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy")

        val fechaInicio = runCatching { LocalDateTime.parse(tarea.FechaInicio, inputFormat).format(outputFormat) }
            .getOrElse { tarea.FechaInicio }
        val fechaFinal = runCatching { LocalDateTime.parse(tarea.FechaFinal, inputFormat).format(outputFormat) }
            .getOrElse { tarea.FechaFinal }

        lblInicio.text = fechaInicio
        lblFinal.text = fechaFinal
    }

    fun MostarDescripcion(tarea: Tarea) {
        findViewById<TextView>(R.id.lblDetalleTareaDescripcion).text = tarea.Descripcion
    }

    fun configurarSpinnerEstado() {
        val labels = resources.getStringArray(R.array.estados)

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, labels)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinnerEstado.adapter = adapter

        spinnerEstado.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: android.view.View?,
                position: Int,
                id: Long
            ) {
                if (ignorarEventoSpinner) {
                    return
                }

                val nuevoEstado = Tarea.Estados.values()[position]

                val tareaEnDatos = datos.listaTareas.firstOrNull { it.Id == tareaId }
                if (tareaEnDatos != null && tareaEnDatos.Estado != nuevoEstado) {
                    tareaEnDatos.Estado = nuevoEstado
                    guardarDatos(datos)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) { }
        }
    }


    fun MostarEstado(tarea: Tarea) {
        ignorarEventoSpinner = true
        spinnerEstado.setSelection(tarea.Estado.ordinal, false)
        ignorarEventoSpinner = false
    }
}
