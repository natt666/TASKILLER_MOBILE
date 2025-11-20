package com.example.taskiller
import Datos
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.gson.Gson
import java.io.File
import java.io.FileReader

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.loginActivity)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val datos = getDatos()

        val primerProyecto = datos?.listaProyectos?.firstOrNull()








        val btnLoginIniciarSesion = findViewById<Button>(R.id.btnLoginIniciarSesion)
        val txtBoxLoginUsuario = findViewById<EditText>(R.id.txtBoxLoginUsuario)
        val txtBoxLoginContrasena = findViewById<EditText>(R.id.txtBoxLoginContrasena)

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

        btnLoginIniciarSesion.setOnClickListener {

            for (u in datos!!.listaUsuarios) {
                if (u.Mail == txtBoxLoginUsuario.text.toString() &&
                    u.Contrasena == txtBoxLoginContrasena.text.toString()) {
                    if (primerProyecto != null) {
                        val intent = Intent(this, DetallesProyectoActivity::class.java)
                        intent.putExtra("datos", datos)
                        intent.putExtra("proyecto", primerProyecto)
                        startActivity(intent)
                    }
                }
            }
        }
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
