package com.example.taskiller

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        // Esto ajusta los márgenes para la barra del sistema :)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 🔹 Paso 1: Buscar el Spinner del XML
        val spinner = findViewById<Spinner>(R.id.spinnerLoginIdiomas)

        // 🔹 Paso 2: Crear el adaptador con el array de strings del archivo strings.xml
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.idiomas, // el nombre del string-array del XML
            android.R.layout.simple_spinner_item // diseño simple del ítem
                                                     )

        // 🔹 Paso 3: Asignar diseño al desplegable
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // 🔹 Paso 4: Enlazar el adaptador al Spinner
        spinner.adapter = adapter

        // 🔹 Paso 5 (opcional): Mostrar un mensaje con el idioma seleccionado
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val idioma = parent.getItemAtPosition(position).toString()
                Toast.makeText(applicationContext, "Idioma: $idioma", Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // No hace falta poner nada aquí yey
            }
        }
    }
}
