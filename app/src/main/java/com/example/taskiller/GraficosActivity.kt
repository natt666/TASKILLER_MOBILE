package com.example.taskiller

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate

class GraficosActivity : AppCompatActivity() {

    private lateinit var pieChart: PieChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_graficos1)

        pieChart = findViewById(R.id.pieChart)


        val datos = getDatos()!!
        val proyecto = datos.listaProyectos.firstOrNull()
        val todasTareas = datos.listaTareas.filter { it.IdProyecto?.equals(proyecto?.Id) == true }

        val estadoCounts = Proyecto.Estados.values().associateWith { estado ->
            todasTareas.count { it.Estado.name == estado.name }
        }

        // Creamos entradas para el PieChart
        val entries = estadoCounts.map { (estado, count) ->
            PieEntry(count.toFloat(), estado.name.replace("_", " "))
        }

        val dataSet = PieDataSet(entries, "Tareas por Estado")
        dataSet.colors = ColorTemplate.MATERIAL_COLORS.toList()
        dataSet.sliceSpace = 3f        // separa las porciones
        dataSet.valueTextSize = 14f    // tamaño del texto de los valores
        dataSet.valueTextColor = android.graphics.Color.WHITE
        dataSet.valueFormatter = com.github.mikephil.charting.formatter.PercentFormatter(pieChart)

// PieChart general
        pieChart.isDrawHoleEnabled = true          // dibuja el agujero en el centro
        pieChart.holeRadius = 40f                  // radio del agujero
        pieChart.setHoleColor(android.graphics.Color.TRANSPARENT)
        pieChart.setTransparentCircleRadius(45f)   // círculo transparente alrededor del agujero

        pieChart.setUsePercentValues(true)         // muestra los valores en porcentaje
        pieChart.setEntryLabelColor(android.graphics.Color.BLACK)  // color de etiquetas fuera del agujero
        pieChart.setEntryLabelTextSize(12f)

// Texto central
        pieChart.centerText = proyecto?.Titulo
        pieChart.setCenterTextSize(18f)
        pieChart.setCenterTextColor(android.graphics.Color.DKGRAY)

// Animación
        pieChart.animateY(1000, com.github.mikephil.charting.animation.Easing.EaseInOutQuad)

// Leyenda
        val legend = pieChart.legend
        legend.isEnabled = true
        legend.textSize = 12f
        legend.formSize = 12f
        legend.verticalAlignment = com.github.mikephil.charting.components.Legend.LegendVerticalAlignment.BOTTOM
        legend.horizontalAlignment = com.github.mikephil.charting.components.Legend.LegendHorizontalAlignment.CENTER
        legend.orientation = com.github.mikephil.charting.components.Legend.LegendOrientation.HORIZONTAL
        legend.setDrawInside(false)
    }

}