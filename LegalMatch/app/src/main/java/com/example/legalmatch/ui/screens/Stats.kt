package com.example.legalmatch.ui.screens

import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.legalmatch.R
import com.example.legalmatch.ui.components.CustomBottomBar
import com.example.legalmatch.ui.components.CustomTopBar
import com.example.legalmatch.viewmodel.CasoUsuario
import com.example.legalmatch.viewmodel.EdadStatsState
import com.example.legalmatch.viewmodel.GraficasViewModel
import com.example.legalmatch.viewmodel.StatsState
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.MPPointF
import com.himanshoe.charty.bar.BarChart
import com.himanshoe.charty.bar.model.BarData
import com.himanshoe.charty.common.config.AxisConfig
import kotlinx.coroutines.delay
import com.himanshoe.charty.common.ChartData
import com.himanshoe.charty.common.ChartDataCollection



@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun StatsScreen(navController: NavController,graficasViewModel: GraficasViewModel = viewModel()) {
    // ViewModel
    val stats by graficasViewModel.statsState.collectAsState()
    val isLoading = remember { mutableStateOf(true) }
    val errorMessage = remember { mutableStateOf<String?>(null) }
    val edadStats by graficasViewModel.edadStatsState.collectAsState()
    val statusCase by graficasViewModel.casosStatsState.collectAsState()

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
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    when {
                        stats.isEmpty() -> {
                            isLoading.value = true
                            errorMessage.value = "No hay datos para mostrar"
                            Box(
                                modifier = Modifier
                                    .fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                        else -> {
                            isLoading.value = false
                            errorMessage.value = null
                            PieChartView(stats = stats, modifier = Modifier.size(300.dp))
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(40.dp))
                }

                item {
                    when {
                        edadStats.isEmpty() -> {
                            isLoading.value = true
                            errorMessage.value = "No hay datos para mostrar"
                        }
                        else -> {
                            isLoading.value = false
                            errorMessage.value = null
                            PieChartViewYears(stats = edadStats, modifier = Modifier.size(300.dp))
                        }
                    }
                }

                item {
                    when {
                        statusCase.isEmpty() -> {
                            isLoading.value = true
                            errorMessage.value = "No hay datos para mostrar"
                        }
                        else -> {
                            BarChartView(stats = statusCase, modifier = Modifier.size(300.dp))
                        }
                    }

                }
            }

        }
    }
}

//Genero
@Composable
fun PieChartView(stats: List<StatsState>, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            text = "Género",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
            )
        )

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
                    setEntryLabelColor(Color.BLACK)
                    setEntryLabelTextSize(12f)

                    // Habilitar leyenda
                    legend.isEnabled = true
                    legend.textColor = Color.BLACK
                    legend.textSize = 12f
                    legend.form = Legend.LegendForm.CIRCLE
                    legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
                }
            },
            update = { pieChart ->
                if (stats.isNotEmpty()) {
                    val entries = stats.map { PieEntry(it.count.toFloat(), it.sexo) }

                    val dataSet = PieDataSet(entries, "").apply {
                        setDrawIcons(false)
                        sliceSpace = 3f
                        iconsOffset = MPPointF(0f, 40f)
                        selectionShift = 5f
                        colors = listOf(
                            pieChart.context.getColor(R.color.purple_700), // Hombre
                            pieChart.context.getColor(R.color.red),         // Mujer
                            pieChart.context.getColor(R.color.teal_200)     // Otro
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
}

//Years
@Composable
fun PieChartViewYears(stats: List<EdadStatsState>, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            text = "Rangos de Edad",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
            )
        )

        AndroidView(
            modifier = modifier,
            factory = { context ->
                PieChart(context).apply {
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
                    setEntryLabelColor(Color.BLACK)
                    setEntryLabelTextSize(12f)

                    // Habilitar leyenda
                    legend.isEnabled = true
                    legend.textColor = Color.BLACK
                    legend.textSize = 12f
                    legend.form = Legend.LegendForm.CIRCLE
                    legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
                }
            },
            update = { pieChart ->
                if (stats.isNotEmpty()) {
                    val entries = stats.map { PieEntry(it.count.toFloat(), it.rangoEdad) }

                    val dataSet = PieDataSet(entries, "").apply {
                        setDrawIcons(false)
                        sliceSpace = 3f
                        iconsOffset = MPPointF(0f, 40f)
                        selectionShift = 5f
                        colors = listOf(
                            pieChart.context.getColor(R.color.purple_700), // 18-25
                            pieChart.context.getColor(R.color.red),         // 26-35
                            pieChart.context.getColor(R.color.teal_200),    // 36-45
                            pieChart.context.getColor(R.color.yellow)   // 46+
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
}

@Composable
fun BarChartView(stats: List<CasoUsuario>, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            text = "Datos de Barras",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
            )
        )
        // Agrupar los casos por estado
        val groupedStats = stats.groupBy { it.estado }

        // Aqui se crea la lista para las barras
        val barDataList = groupedStats.map { (estado, casos) ->
            BarData(
                xValue = estado,
                yValue = casos.size.toFloat(),
            )
        }

        val dataCollection = ChartDataCollection(data = barDataList)

        BarChart(
            dataCollection = dataCollection,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            barSpacing = 8.dp,
            padding = 16.dp,
//            barColor = androidx.compose.ui.graphics.Color.Blue,
            axisConfig = AxisConfig(
                showAxes = true,
                showGridLines = true,
                showGridLabel = true,
                axisStroke = 1f,
                minLabelCount = 5,
                axisColor = androidx.compose.ui.graphics.Color.Gray
            )
        )
    }
}


