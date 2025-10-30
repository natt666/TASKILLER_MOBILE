package com.example.taskiller

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.taskiller.adapters.UsuarioAdapter
import com.example.taskiller.models.Usuario

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
                "小茶",
                "Li",
                "xiaocha@example.com",
                "123456",
            ),
            Usuario(
                "宗老师",
                "Saiguu",
                "oc3@taskiller.com",
                "backend666",
            ),
            Usuario(
                "玛朵莫塞尔",
                "Mademoiselle",
                "mado@taskiller.com",
                "parisQueen",
            )
        )


        val lstDetalleTareaUsuarioAsignado = findViewById<RecyclerView>(R.id.lstDetalleTareaUsuarioAsignado)

        val adapter = UsuarioAdapter(usuarios)
        lstDetalleTareaUsuarioAsignado.adapter = adapter
    }
}