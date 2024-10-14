package com.example.legalmatch.viewmodel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.legalmatch.utils.SUPABASE_KEY
import com.example.legalmatch.utils.SUPABASE_URL
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
import com.example.legalmatch.utils.TAG

// Supabase
val supabase = createSupabaseClient(
    supabaseUrl = SUPABASE_URL,
    supabaseKey = SUPABASE_KEY
) {
    install(Auth)
    install(Postgrest)
}

//Edad
@Serializable
data class Usuario(
    val sexo: String,
    val fecha_nacimiento: String
)

//sexo
data class StatsState(
    val sexo: String,
    val count: Int
)

//Edad
data class EdadStatsState(
    val rangoEdad: String,
    val count: Int
)

//Casos completados
@Serializable
data class CasoUsuario(
    val estado: String,
    val fecha_asesoria: String

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
    //Genero
    private val _statsState = MutableStateFlow<List<StatsState>>(emptyList())
    val statsState: StateFlow<List<StatsState>> get() = _statsState

    //Edades
    private val _edadStatsState = MutableStateFlow<List<EdadStatsState>>(emptyList())
    val edadStatsState: StateFlow<List<EdadStatsState>> get() = _edadStatsState

    // Casos completados
    private val _casosStatsState = MutableStateFlow<List<CasoUsuario>>(emptyList())
    val casosStatsState: StateFlow<List<CasoUsuario>> get() = _casosStatsState

    init {
        viewModelScope.launch {
            delay(1000)

            fetchSexoCounts()
            fetchEdadCounts()
            fetchCaseStatus()
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

    fun fetchCaseStatus(){
        viewModelScope.launch{
            try{
                val casos = supabase.from("asesorias")
                    .select()
                    .decodeList<CasoUsuario>()

                //Funcion para determinar los casos de los meses
                val groupedMonths = casos.groupBy { caso ->
                    val month = caso.fecha_asesoria.substring(0, 7) // se toma el formtato de "YYYY-MM"
                    Pair(month, caso.estado) //Agrupado
                }
                val casosPorMes = groupedMonths.mapValues { (_, casos) -> casos.size } //Conteo de casos por mes

                casosPorMes.forEach { (mesEstado, cantidad) -> // Se obtiene con el foreach el mes y la cantidad de recurencia
                    val (mes,estado) = mesEstado
                    Log.d(TAG, "Mes: $mes, Estado: $estado, Casos: $cantidad")
                }
                _casosStatsState.value = casos //Checar si esto no es lo que manda a llamar tanto al viewmodel


            } catch (e: Exception){
                Log.e(TAG, "Error fetching case status: ${e.message}", e)
            }
        }
    }


}