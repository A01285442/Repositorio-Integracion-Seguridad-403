package com.example.legalmatch.ui.screens

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import com.example.legalmatch.R
import com.example.legalmatch.ui.components.CustomBottomBar
import com.example.legalmatch.ui.components.CustomTopBar
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.MPPointF

@Composable
fun StatsScreen(navController: NavController) {
    Scaffold(
        topBar = { CustomTopBar(title = "Estadísticas", navIcon = false, actIcon = false) },
        bottomBar = { CustomBottomBar(navController = navController) }
    ) { padding ->
        // Aquí integramos el PieChart usando AndroidView
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(padding)
        ) {
            PieChartView()
        }
    }
}

@Composable
fun PieChartView() {
    AndroidView(factory = { context ->
        PieChart(context).apply {
            // Configuración del PieChart
            setUsePercentValues(true)
            description.isEnabled = false
            setExtraOffsets(5f, 10f, 5f, 5f)
            dragDecelerationFrictionCoef = 0.95f
            setDrawHoleEnabled(true)
            holeColor = Color.WHITE
            setTransparentCircleColor(Color.WHITE)
            setTransparentCircleAlpha(110)
            holeRadius = 58f
            transparentCircleRadius = 61f
            setDrawCenterText(true)
            rotationAngle = 0f
            isRotationEnabled = true
            setHighlightPerTapEnabled(true)
            animateY(1400, Easing.EaseInOutQuad)
            legend.isEnabled = false
            setEntryLabelColor(Color.WHITE)
            setEntryLabelTextSize(12f)

            // Datos del gráfico
            val entries = ArrayList<PieEntry>().apply {
                add(PieEntry(70f, "OS 1"))
                add(PieEntry(20f, "OS 2"))
                add(PieEntry(10f, "OS 3"))
            }

            val dataSet = PieDataSet(entries, "Mobile OS").apply {
                setDrawIcons(false)
                sliceSpace = 3f
                iconsOffset = MPPointF(0f, 40f)
                selectionShift = 5f
                colors = listOf(
                    context.getColor(R.color.purple_200),
                    context.getColor(R.color.yellow),
                    context.getColor(R.color.red)
                )
            }

            val data = PieData(dataSet).apply {
                setValueFormatter(PercentFormatter())
                setValueTextSize(15f)
                setValueTypeface(Typeface.DEFAULT_BOLD)
                setValueTextColor(Color.WHITE)
            }

            this.data = data
            highlightValues(null)
            invalidate() // Actualizar el gráfico
        }
    })
}
