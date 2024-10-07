package com.example.legalmatch.ui.screens

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewmodel.compose.viewModel
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
import kotlinx.coroutines.delay


@Composable
fun StatsScreen(navController: NavController,graficasViewModel: GraficasViewModel = viewModel()) {
    // ViewModel
    val stats by graficasViewModel.statsState.collectAsState()
    val isLoading = remember { mutableStateOf(true) }
    val errorMessage = remember { mutableStateOf<String?>(null) }

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
            when {
                stats.isEmpty() -> {
                    isLoading.value = true
                    errorMessage.value = "No hay datos para mostrar"
                    CircularProgressIndicator()
                }
                else -> {
                    isLoading.value = false
                    errorMessage.value = null
                    PieChartView(stats = stats, modifier = Modifier.size(300.dp))
                }
            }
        }
    }
}

@Composable
fun PieChartView(stats: List<StatsState>, modifier: Modifier = Modifier) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            PieChart(context).apply {
                // Configuración inicial del PieChart
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
                setEntryLabelColor(Color.BLACK)
                setEntryLabelTextSize(12f)
            }
        },
        update = { pieChart ->
            if (stats.isNotEmpty()) {
                val entries = stats.map { PieEntry(it.count.toFloat(), it.sexo) }

                val dataSet = PieDataSet(entries, "Usuarios por Sexo").apply {
                    setDrawIcons(false)
                    sliceSpace = 3f
                    iconsOffset = MPPointF(0f, 40f)
                    selectionShift = 5f
                    colors = listOf(
                        pieChart.context.getColor(R.color.purple_700),
                        pieChart.context.getColor(R.color.red),
                        pieChart.context.getColor(R.color.teal_200)
                    )
                }

                val pieData = PieData(dataSet).apply {
                    setDrawValues(true)
                    setValueFormatter(PercentFormatter())
                    setValueTextSize(12f)
                    setValueTextColor(Color.BLACK)
                    setValueTypeface(Typeface.DEFAULT_BOLD)
                }

                pieChart.data = pieData
                pieChart.notifyDataSetChanged()
                pieChart.invalidate()
            } else {
                pieChart.data = null
                pieChart.invalidate()
            }
        }
    )
}
