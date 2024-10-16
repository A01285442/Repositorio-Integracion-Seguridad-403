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
import com.example.legalmatch.data.api.models.SendAsesoria
import com.example.legalmatch.data.api.models.SendCaso
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Order
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime


private const val TAG = "MainActivity"

data class AsesoriaState(
    val asesorias: List<Asesoria> = emptyList(),
    val isLoading: Boolean = true
)

@RequiresApi(Build.VERSION_CODES.O)
class AsesoriaViewModel : ViewModel() {
    private var _state by mutableStateOf(AsesoriaState())
    val state: AsesoriaState get() = _state

    init { fetchAsesorias() }

    fun getAsesoriaInfo(id: Int) : Asesoria? {
        fetchAsesorias()
        return state.copy().asesorias.firstOrNull{it.id == id}
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun fetchAsesorias() {

        viewModelScope.launch {
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
                Log.d(TAG, fetchedAsesorias[0].titulo)
            } catch (e: Exception) { errorMessage(e) }
        }
    }

    fun aceptarcaso(asesoria: Asesoria, abogadoId: Int){
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
                    id_abogado = abogadoId,
                    id_cliente = asesoria.id_cliente,
                    nuc = "",
                    password_fv = "",
                    titulo = asesoria.titulo,
                    unidad_investigacion = ""
                )

            // Se inserta el nuevo caso en la base de datos
            try { supabase.postgrest["casos"].insert(caso) }
            catch (e:Exception) { errorMessage(e) }

            // Se marca la asesoría como finalizada
            try { supabase.from("asesorias")
                .update({ set("estado", "finalizado") }) {
                    filter { eq("id", asesoria.id) }
                }
                fetchAsesorias()
            } catch (e: Exception){ errorMessage(e) }



        }
    }

    fun cancelarCaso(asesoria: Asesoria){
        viewModelScope.launch {
            try{
                supabase.from("asesorias")
                    .update({ set("estado","cancelado") }) {
                        filter { eq("id", asesoria.id) }
                }
                fetchAsesorias()
            } catch (e:Exception){ errorMessage(e) }
        }
    }

    fun reagendarAsesoria(asesoria: Asesoria){
        viewModelScope.launch {
            try {
                supabase.from("asesorias")
                    .update({ set("fecha_asesoria", LocalDateTime(1,1,1,1,1,1)) }) {
                        filter { eq("id", asesoria.id) }
                    }
                fetchAsesorias()
            }catch (e:Exception){errorMessage(e)}
        }
    }

    fun enviarAsesoria(newAsesoria: SendAsesoria){
        viewModelScope.launch {
            try {
                supabase.postgrest["asesorias"].insert(newAsesoria)
            } catch (e: Exception) {
                e.printStackTrace()
                Log.d(TAG, e.message.toString())
            }
        }
    }

    fun errorMessage(error: Exception){
        Log.d(TAG, "Error: ${error.message}")
        _state = state.copy(isLoading = false)

    }


}

