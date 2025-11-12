package com.example.taskiller

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskiller.adapters.UsuarioAdapter

class DetalleTareaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detalle_tarea)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.layoutDetallesTareaMain)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val usuarios = listOf(
            Usuario(
                "Chenxiao",
                "Xu",
                "xiaocha@example.com",
                "123456",
            ),
            Usuario(
                "nanana",
                "nanana",
                "oc3@taskiller.com",
                "backend666",
            ),
            Usuario(
                "kakaka",
                "kakaka",
                "mado@taskiller.com",
                "parisQueen",
            )
        )


        val lstDetalleTareaUsuarioAsignado = findViewById<RecyclerView>(R.id.lstDetalleTareaUsuarioAsignado)

        val adapter = UsuarioAdapter(usuarios)
        lstDetalleTareaUsuarioAsignado.hasFixedSize()
        lstDetalleTareaUsuarioAsignado.layoutManager = LinearLayoutManager(this)
        lstDetalleTareaUsuarioAsignado.adapter = adapter
    }

}