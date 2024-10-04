package com.example.legalmatch.ui.screens

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.legalmatch.data.api.models.Asesoria
import com.example.legalmatch.data.api.models.Caso
import com.example.legalmatch.data.api.models.SendCaso
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Order
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import kotlin.math.log


private const val TAG = "MainActivity"

data class AsesoriaState(
    val asesorias: List<Asesoria> = emptyList(),
    val isLoading: Boolean = true
)

@RequiresApi(Build.VERSION_CODES.O)
class AsesoriaViewModel : ViewModel() {
    private var _state by mutableStateOf(AsesoriaState())
    val state: AsesoriaState get() = _state

    init {
        fetchAsesorias()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun fetchAsesorias() {

        viewModelScope.launch {
            Log.d(TAG, "Iniciando fetch")
            try {
                // Código para obtener asesorías
                val fetchedAsesorias = supabase.from("asesorias")
                    .select(){
                        filter {
                            eq("estado", "pendiente")
                        }
                        order(column = "fecha_asesoria", order = Order.ASCENDING)
                    }
                    .decodeList<Asesoria>()
                _state = state.copy(asesorias = fetchedAsesorias, isLoading = false)
            } catch (e: Exception) {
                Log.d(TAG,"Error: ${e.message}")
                _state = state.copy(isLoading = false) // Aquí también se usa correctamente
            }
        }
    }

    fun aceptarcaso(asesoria: Asesoria, loginViewModel: LoginViewModel){
        viewModelScope.launch {
            val caso = SendCaso(
                    c_investigacion = asesoria.c_investigacion,
                    c_judicial = asesoria.c_judicial,
                    caso_cerrado = false,
                    delito = asesoria.delito,
                    descripcion = asesoria.descripcion,
                    direccion_ui = "",
                    drive_link = "https://google.com",
                    fiscalia_virtual = "https://google.com",
                    id_abogado = 1,
                    id_cliente = asesoria.id_cliente,
                    nuc = "",
                    password_fv = "",
                    titulo = asesoria.titulo,
                    unidad_investigacion = ""
                )

            try {
                supabase.postgrest["casos"].insert(caso)
            } catch (e:Exception) {
                Log.d(TAG,"Error: $e")
            }

            try {
                supabase.from("asesorias").update({
                        set("estado", "finalizado")
                    }
                ){
                    filter {
                        eq("id", asesoria.id)
                    }
                }
            } catch (e: Exception){
                Log.d(TAG,"Error: $e")
            }
            fetchAsesorias()

        }
    }
}