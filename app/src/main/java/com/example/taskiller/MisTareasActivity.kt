package com.example.taskiller

import Datos
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
import com.example.taskiller.adapters.TaskAdapter
import com.example.taskiller.models.Tarea
import com.example.taskiller.models.Usuario

class MisTareasActivity : AppCompatActivity() {

    private lateinit var datos: Datos
    private lateinit var user: Usuario

    private lateinit var recyclerTareas: RecyclerView
    private lateinit var adapter: TaskAdapter
    private val tareas: MutableList<Tarea> = mutableListOf()

    private val detalleLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                val datosDevueltos = data?.getSerializableExtra("datos") as? Datos
                val userDevuelto = data?.getSerializableExtra("user") as? Usuario

                if (datosDevueltos != null) datos = datosDevueltos
                if (userDevuelto != null) user = userDevuelto

                refrescarLista()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_mis_tareas)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        datos = intent.getSerializableExtra("datos") as Datos
        user = intent.getSerializableExtra("user") as Usuario

        recyclerTareas = findViewById(R.id.rVTareas)
        recyclerTareas.layoutManager = LinearLayoutManager(this)

        adapter = TaskAdapter(
            tareas,
            onCardClick = { tarea ->
                val intent = Intent(this, DetalleTareaActivity::class.java).apply {
                    putExtra("datos", datos)
                    putExtra("tarea", tarea)
                    putExtra("user", user)
                }
                detalleLauncher.launch(intent)
            }
        )
        recyclerTareas.adapter = adapter

        refrescarLista()

        val btnVolver = findViewById<ImageButton>(R.id.btnMisTareasVolver)
        btnVolver.setOnClickListener {
            devolverResultadoYSalir()
        }
    }

    override fun onBackPressed() {
        devolverResultadoYSalir()
    }

    private fun devolverResultadoYSalir() {
        val result = Intent().apply {
            putExtra("datos", datos)
            putExtra("user", user)
        }
        setResult(Activity.RESULT_OK, result)
        finish()
    }

    private fun refrescarLista() {
        tareas.clear()
        tareas.addAll(datos.listaTareas.filter { it.listaUsuarios.contains(user.Id) })
        adapter.notifyDataSetChanged()
    }
}
