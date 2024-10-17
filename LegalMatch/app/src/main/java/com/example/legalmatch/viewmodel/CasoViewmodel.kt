package com.example.legalmatch.ui.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.legalmatch.data.api.models.Caso
import com.example.legalmatch.data.api.models.SendCaso
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

// Supabase
val supabase = createSupabaseClient(
    supabaseUrl = "https://wbhyplodhfxcyeochnpf.supabase.co",
    supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6IndiaHlwbG9kaGZ4Y3llb2NobnBmIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MjU0MjExNTUsImV4cCI6MjA0MDk5NzE1NX0.sqMEMPdCx9u-kC0TI0OLWKm1KXZjSkeDS1N3bQiG-jI",
) {
    install(Auth)
    install(Postgrest)
}

private const val TAG = "MainActivity"

data class CasoState(
    val casos: List<Caso> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val pageExists: Boolean = false

)

class CasosViewModel() : ViewModel() {

    private var searchJob: Job? = null

    private var _state by mutableStateOf(CasoState())
    val state: CasoState get() = _state

    init {
        fetchCasos()
    }


    fun getCasoInfo(id: Int) : Caso? {
        return state.copy().casos.firstOrNull{it.id == id}
    }


    fun cerrarCaso(id: Int) {
        viewModelScope.launch {
            try {
                supabase.from("casos")
                    .update({
                        set("caso_cerrado", true)
                    }) {
                        filter {
                            eq("id", id)
                        }
                    }
            } catch (e: Exception) {
                Log.d(TAG, "Error: ${e.message}")
                e.message?.let {
                    _state = state.copy(
                        errorMessage = e.message!!,
                        isLoading = false
                    )
                }
            } finally {
                fetchCasos()
            }
        }
    }

    @SuppressLint("SuspiciousIndentation")
    fun createCaso(caso: SendCaso){
        viewModelScope.launch {
        _state = state.copy(isLoading = true)

            try {
                supabase.from("casos").insert(caso)
                Log.d(TAG,"Caso creado.")
            } catch (e: Exception) {
                Log.d(TAG, "Error: ${e.message}")
            } finally {
                _state = state.copy(isLoading = false)
            }
            delay(500)
        }

        fetchCasos()
    }




    fun fetchCasos(){
        viewModelScope.launch {

            _state = state.copy(isLoading = true) // Inicia el estado de carga

            // Obtenemos información a través de la base de datos

            try {
                // Obtener una lista
                val fetchedCasos = supabase.from("casos")
                    .select(){ filter {
                        eq("caso_cerrado", false)
                    }}
                    .decodeList<Caso>()
                _state = state.copy(casos = fetchedCasos, isLoading = false)

            } catch (e: Exception) {
                e.message?.let {
                    _state = state.copy(
                        errorMessage = e.message!!,
                        isLoading = false
                    )
                }
            }
        }
    }

    fun updateCaso(casoAMandar: SendCaso, id: Int) {
        viewModelScope.launch {
            try{
                supabase.from("casos")
                    .update({
                        set("titulo", casoAMandar.titulo)
                        set("descripcion", casoAMandar.descripcion)
                        set("delito", casoAMandar.delito)
                        set("c_investigacion", casoAMandar.c_investigacion)
                        set("c_judicial", casoAMandar.c_judicial)
                        set("fiscalia_virtual", casoAMandar.fiscalia_virtual)
                        set("password_fv", casoAMandar.password_fv)
                        set("unidad_investigacion", casoAMandar.unidad_investigacion)
                        set("direccion_ui", casoAMandar.direccion_ui)
                    }){ filter { eq("id",id) } }
            } catch (e:Exception){
                e.message?.let { Log.d("MainActivity", it) }
            } finally {
                fetchCasos()
            }
        }

    }
}