package com.example.taskiller

import Datos
import Proyecto
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.example.taskiller.models.Tarea
import com.example.taskiller.models.Usuario
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.animation.Easing
import java.util.UUID

class GraficosActivity : AppCompatActivity() {

    private lateinit var pieChart: PieChart
    private lateinit var graficosbtnvolver: ImageButton
    private lateinit var tfBold: Typeface
    private lateinit var tfMedium: Typeface
    private lateinit var lblnombreproyecto: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_graficos)

        pieChart = findViewById(R.id.pieChart)
        graficosbtnvolver = findViewById(R.id.graficosbtnvolver)
        lblnombreproyecto = findViewById(R.id.lblGraficosNombreProyecto)

        tfBold = ResourcesCompat.getFont(this, R.font.montserrat_bold)!!
        tfMedium = ResourcesCompat.getFont(this, R.font.montserrat_medium)!!

        val datos = intent.getSerializableExtra("datos") as Datos
        val user = intent.getSerializableExtra("user") as Usuario
        val proyectoId = intent.getSerializableExtra("proyectoId") as UUID
        val proyecto = datos.listaProyectos.firstOrNull { it.Id == proyectoId } ?: return
        val todasTareas = datos.listaTareas.filter { it.IdProyecto == proyectoId }
        lblnombreproyecto.text = proyecto.Titulo


        val estadoCounts = Tarea.Estados.values().associateWith { estado ->
            todasTareas.count { it.Estado == estado }
        }

        val (entries, colors) = preparePieChartData(estadoCounts)

        if (entries.isEmpty()) {
            pieChart.centerText = "Sin tareas"
            pieChart.data = null
            pieChart.invalidate()
            return
        }

        setupPieChart(entries, colors, proyecto?.Titulo)

        graficosbtnvolver.setOnClickListener {
            val resultIntent = Intent()
            resultIntent.putExtra("datos", datos)
            resultIntent.putExtra("user", user)
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }

    private fun preparePieChartData(estadoCounts: Map<Tarea.Estados, Int>): Pair<List<PieEntry>, List<Int>> {
        val entries = mutableListOf<PieEntry>()
        val colors = mutableListOf<Int>()

        val estadoColorMap = mapOf(
            Tarea.Estados.Por_Comenzar to R.color.Por_comenzar,
            Tarea.Estados.En_Progreso to R.color.En_progreso,
            Tarea.Estados.Entregado to R.color.Entregado,
            Tarea.Estados.Revisado to R.color.Revisado,
            Tarea.Estados.Bloqueado to R.color.Bloqueado
        )

        estadoCounts.forEach { (estado, count) ->
            if (count > 0) {
                entries.add(PieEntry(count.toFloat(), estado.name.replace("_", " ")))
                colors.add(ContextCompat.getColor(this, estadoColorMap[estado]!!))
            }
        }

        return entries to colors
    }
    private fun setupPieChart(entries: List<PieEntry>, colors: List<Int>, title: String?) {
        val dataSet = PieDataSet(entries, "").apply {
            this.colors = colors
            sliceSpace = 5f
            selectionShift = 10f
            valueTextSize = 14f
            valueTextColor = Color.DKGRAY
            valueTypeface = tfMedium
            valueFormatter = PercentFormatter(pieChart)
            yValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
            xValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
            valueLinePart1OffsetPercentage = 55f
            valueLinePart1Length = 0.3f
            valueLinePart2Length = 0.2f
            valueLineColor = Color.DKGRAY
        }

        pieChart.apply {
            isDrawHoleEnabled = true
            holeRadius = 45f
            setHoleColor(Color.TRANSPARENT)
            setTransparentCircleRadius(50f)
            setTransparentCircleAlpha(110)
            setUsePercentValues(true)
            setEntryLabelColor(Color.BLACK)
            setEntryLabelTextSize(12f)
            setEntryLabelTypeface(tfBold)
            setExtraOffsets(30f, 10f, 30f, 10f)
            animateY(1200, Easing.EaseInOutQuad)

            legend.apply {
                isEnabled = true
                textSize = 14f
                formSize = 12f
                verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
                horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
                orientation = Legend.LegendOrientation.HORIZONTAL
                typeface = tfMedium
                setDrawInside(false)
            }

            data = PieData(dataSet)
            invalidate()
        }
    }
}
