package com.example.legalmatch.ui.screens

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import com.example.legalmatch.R
import com.example.legalmatch.ui.components.CustomBottomBar
import com.example.legalmatch.ui.components.CustomTopBar
import com.example.legalmatch.viewmodel.GraficasViewModel
import com.example.legalmatch.viewmodel.StatsState
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.MPPointF



@Composable
fun StatsScreen(navController: NavController,graficasViewModel: GraficasViewModel) {
    // ViewModel
    val stats by graficasViewModel.statsState.collectAsState()

    Scaffold(
        topBar = { CustomTopBar(title = "Estadísticas", navIcon = false, actIcon = false) },
        bottomBar = { CustomBottomBar(navController = navController) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PieChartView(stats = stats)
        }
    }
}

@Composable
fun PieChartView(stats: List<StatsState>) {
    AndroidView(factory = { context ->
        PieChart(context).apply {
            // PieChart Tamaños
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )

            setUsePercentValues(true)
            description.isEnabled = false
            setExtraOffsets(5f, 10f, 5f, 5f)
            dragDecelerationFrictionCoef = 0.95f
            setDrawHoleEnabled(true)
            setHoleColor(Color.WHITE)
            setTransparentCircleColor(Color.WHITE)
            holeRadius = 55f
            setDrawCenterText(true)
            rotationAngle = 0f
            isRotationEnabled = true
            setHighlightPerTapEnabled(true)
            animateY(1400, Easing.EaseInOutQuad)
            legend.isEnabled = false
            setEntryLabelColor(Color.WHITE)
            setEntryLabelTextSize(12f)

            // Datos del ViewModel (stats)
            val entries = ArrayList<PieEntry>().apply {
                stats.forEach { stat ->
                    add(PieEntry(stat.count.toFloat(), stat.sexo))
                }
            }

            val dataSet = PieDataSet(entries, "Usuarios por Sexo").apply {
                setDrawIcons(false)
                sliceSpace = 3f
                iconsOffset = MPPointF(0f, 40f)
                selectionShift = 5f
                colors = listOf(
                    context.getColor(R.color.purple_700),
                    context.getColor(R.color.teal_200),
                    context.getColor(R.color.red)
                )
            }

            this.data = data
            highlightValues(null)
            invalidate()
        }
    })
}
