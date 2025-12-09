package com.example.taskiller

import android.graphics.Color
import android.os.Bundle
import android.widget.ImageButton
import android.widget.NumberPicker
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.animation.Easing

class GraficosActivity : AppCompatActivity() {

    private lateinit var pieChart: PieChart
    private lateinit var graficosbtnvolver: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_graficos1)

        pieChart = findViewById(R.id.pieChart)
        graficosbtnvolver = findViewById<ImageButton>(R.id.graficosbtnvolver)
        val lblnombreproyecto = findViewById<TextView>(R.id.lblGraficosNombreProyecto)

        val datos = getDatos()!!
        val proyecto = datos.listaProyectos.firstOrNull()
        val todasTareas = datos.listaTareas.filter { it.IdProyecto == proyecto?.Id }

        lblnombreproyecto.text = proyecto?.Titulo
        // Contamos tareas por estado
        val estadoCounts = Proyecto.Estados.values().associateWith { estado ->
            todasTareas.count { it.Estado.name == estado.name }
        }

        // Tipografías Montserrat
        val tfBold = ResourcesCompat.getFont(this, R.font.montserrat_bold)
        val tfMedium = ResourcesCompat.getFont(this, R.font.montserrat_medium)

        // Creamos entradas del PieChart, solo con >0
        val entries = estadoCounts.filter { it.value > 0 }.map { (estado, count) ->
                PieEntry(count.toFloat(), estado.name.replace("_", " "))
            }

        if (entries.isEmpty()) {
            pieChart.centerText = "Sin tareas"
            pieChart.data = null
            pieChart.invalidate()
            return
        }

        // Dataset
        val dataSet = PieDataSet(entries, "")
        dataSet.colors = listOf(
            Color.parseColor("#FF6384"),
            Color.parseColor("#36A2EB"),
            Color.parseColor("#FFCE56"),
            Color.parseColor("#8BC34A"),
            Color.parseColor("#FF9800"),
            Color.parseColor("#9C27B0")
                               )
        dataSet.sliceSpace = 5f
        dataSet.selectionShift = 10f
        dataSet.valueTextSize = 14f
        dataSet.valueTextColor = Color.DKGRAY
        dataSet.valueTypeface = tfMedium
        dataSet.valueFormatter = PercentFormatter(pieChart)
        dataSet.yValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
        dataSet.xValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
        dataSet.valueLinePart1OffsetPercentage = 55f
        dataSet.valueLinePart1Length = 0.3f
        dataSet.valueLinePart2Length = 0.2f
        dataSet.valueLineColor = Color.DKGRAY

        // PieChart general
        pieChart.isDrawHoleEnabled = true
        pieChart.holeRadius = 45f
        pieChart.setHoleColor(Color.TRANSPARENT)
        pieChart.setTransparentCircleRadius(50f)
        pieChart.setTransparentCircleAlpha(110)
        pieChart.setUsePercentValues(true)
        pieChart.setEntryLabelColor(Color.BLACK)
        pieChart.setEntryLabelTextSize(12f)
        pieChart.setEntryLabelTypeface(tfBold)
        pieChart.setExtraOffsets(30f, 10f, 30f, 10f)


        // Texto central
        pieChart.centerText = proyecto?.Titulo
        pieChart.setCenterTextSize(20f)
        pieChart.setCenterTextColor(Color.DKGRAY)
        pieChart.setCenterTextTypeface(tfBold)

        // Animación
        pieChart.animateY(1200, Easing.EaseInOutQuad)

        // Leyenda
        val legend = pieChart.legend
        legend.isEnabled = true
        legend.textSize = 14f
        legend.formSize = 12f
        legend.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        legend.orientation = Legend.LegendOrientation.HORIZONTAL
        legend.typeface = tfMedium
        legend.setDrawInside(false)

        // Asignar datos
        val data = PieData(dataSet)
        pieChart.data = data
        pieChart.invalidate()

        graficosbtnvolver.setOnClickListener {
            val itent
        }
    }

}
