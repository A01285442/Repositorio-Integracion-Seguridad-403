package com.example.legalmatch.viewmodel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.legalmatch.ui.screens.supabaseCli
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter

// Supabase
val supabase = createSupabaseClient(
    supabaseUrl = "https://wbhyplodhfxcyeochnpf.supabase.co",
    supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6IndiaHlwbG9kaGZ4Y3llb2NobnBmIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MjU0MjExNTUsImV4cCI6MjA0MDk5NzE1NX0.sqMEMPdCx9u-kC0TI0OLWKm1KXZjSkeDS1N3bQiG-jI",
) {
    install(Auth)
    install(Postgrest)
}

private const val TAG = "MainActivity"

@Serializable
data class Usuario(
    val sexo: String,
    val fecha_nacimiento: String
)

data class StatsState(
    val sexo: String,
    val count: Int
)

data class EdadStatsState(
    val rangoEdad: String,
    val count: Int
)

@RequiresApi(Build.VERSION_CODES.O)
fun calcularEdad(fechaNacimiento: String): Int {
    val formatter = DateTimeFormatter.ISO_DATE_TIME
    val nacimiento = LocalDate.parse(fechaNacimiento, formatter)
    val hoy = LocalDate.now()
    return Period.between(nacimiento, hoy).years
}

@RequiresApi(Build.VERSION_CODES.O)
class GraficasViewModel : ViewModel() {
    private val _statsState = MutableStateFlow<List<StatsState>>(emptyList())
    val statsState: StateFlow<List<StatsState>> get() = _statsState

    //Edades
    private val _edadStatsState = MutableStateFlow<List<EdadStatsState>>(emptyList())
    val edadStatsState: StateFlow<List<EdadStatsState>> get() = _edadStatsState

    init {
        viewModelScope.launch {
            delay(1000)

            fetchSexoCounts()
            fetchEdadCounts()
        }
    }

    fun fetchSexoCounts() {
        viewModelScope.launch {
            try {
                val usuarios = supabase.from("usuarios")
                    .select()
                    .decodeList<Usuario>()

                val groupedCounts = usuarios.groupBy {it.sexo}
                    .map { StatsState(it.key.capitalize(), it.value.size) }
                Log.d(TAG, "grupos: $groupedCounts")

                _statsState.value = groupedCounts

            } catch (e: Exception) {
                Log.e(TAG, "Error fetching sexo counts: ${e.message}", e)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun fetchEdadCounts() {
        viewModelScope.launch {
            try {
                val usuarios = supabase.from("usuarios")
                    .select()
                    .decodeList<Usuario>()

                val edades = usuarios.map { calcularEdad(it.fecha_nacimiento) }
                val groupedEdades = edades.groupBy { edad ->
                    when (edad) {
                        in 18..25 -> "18-25"
                        in 26..35 -> "26-35"
                        in 36..45 -> "36-45"
                        else -> "46+"
                    }
                }.map { EdadStatsState(it.key, it.value.size) }

                Log.d(TAG, "Edades agrupadas: $groupedEdades")

                _edadStatsState.value = groupedEdades

            } catch (e: Exception) {
                Log.e(TAG, "Error fetching edad counts: ${e.message}", e)
            }
        }
    }


}