package com.example.taskiller

import Datos
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Locale

class LoginActivity : AppCompatActivity() {
    private lateinit var txtViewLoginTitulo: TextView

    private lateinit var btnLoginIniciarSesion: Button
    private lateinit var txtBoxLoginUsuario: EditText
    private lateinit var txtBoxLoginContrasena: EditText
    private lateinit var spinner: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        cargarIdioma()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.loginActivity)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val datos = getDatos()

        txtViewLoginTitulo = findViewById(R.id.txtViewLoginTitulo)
        btnLoginIniciarSesion = findViewById(R.id.btnLoginIniciarSesion)
        txtBoxLoginUsuario = findViewById(R.id.txtBoxLoginUsuario)
        txtBoxLoginContrasena = findViewById(R.id.txtBoxLoginContrasena)
        spinner = findViewById(R.id.spinnerLoginIdiomas)

        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.idiomas,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        val prefs = getSharedPreferences("configuracion", MODE_PRIVATE)
        val idiomaGuardado = prefs.getString("idioma", "es")
        spinner.setSelection(
            when (idiomaGuardado) {
                "es" -> 0
                "en-GB" -> 1
                "ca" -> 2
                else -> 0
            }
        )

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            private var primerInicializado = true
            override fun onItemSelected(parent: AdapterView<*>, view: android.view.View?, position: Int, id: Long) {
                if (primerInicializado) {
                    primerInicializado = false
                    return
                }
                val idioma = when (position) {
                    0 -> "default"
                    1 -> "en-GB"
                    2 -> "ca"
                    else -> "default"
                }
                guardarIdioma(idioma)
                aplicarLocale(idioma)
                actualizarTextos()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        btnLoginIniciarSesion.setOnClickListener {
            for (u in datos!!.listaUsuarios) {
                if (u.Mail == txtBoxLoginUsuario.text.toString() &&
                    u.Contrasena == txtBoxLoginContrasena.text.toString()) {
                    val intent = Intent(this, MisTareasActivity::class.java)
                    intent.putExtra("datos", datos)
                    intent.putExtra("user", u)
                    startActivity(intent)
                }
            }
        }
        actualizarTextos()
    }

    private fun guardarIdioma(idioma: String) {
        val prefs = getSharedPreferences("configuracion", MODE_PRIVATE)
        prefs.edit().putString("idioma", idioma).apply()
    }

    private fun aplicarLocale(idioma: String) {
        val locale: Locale = when (idioma) {
            "en-GB" -> Locale("en", "GB")
            "es" -> Locale("default")
            "ca" -> Locale("ca")
            else -> Locale("es")
        }

        Locale.setDefault(locale)
        val config = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }


    private fun cargarIdioma() {
        val prefs = getSharedPreferences("configuracion", MODE_PRIVATE)
        val idioma = prefs.getString("idioma", "es") ?: "es"
        aplicarLocale(idioma)
    }

    private fun actualizarTextos() {
        txtViewLoginTitulo.text = getString(R.string.LoginIniciaSesion)
        btnLoginIniciarSesion.text = getString(R.string.LoginIniciaSesion)
        txtBoxLoginUsuario.hint = getString(R.string.LoginNombreUsuario)
        txtBoxLoginContrasena.hint = getString(R.string.LoginContrasena)
    }
}
