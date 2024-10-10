package com.example.legalmatch.ui.screens

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

    // Define la función como suspend para usarla dentro de corutinas
    suspend fun doesPageExist(url: String): Boolean {
        val client = OkHttpClient()

        val request = Request.Builder()
            .url(url)
            .head() // Usa .head() para solo obtener el encabezado, sin descargar todo el contenido
            .build()

        return withContext(Dispatchers.IO) {
            try {
                val response = client.newCall(request).execute()
                response.isSuccessful // Retorna true si el código de respuesta está entre 200 y 299
            } catch (e: IOException) {

                Log.d(TAG,"Error: ${e.message}")
                false // Retorna false si hay algún error en la solicitud
            }
        }
    }
    fun checkIfPageExists(url: String) {
        if (url.length < 3) return
        // Cancelamos la búsqueda anterior si hay una
        searchJob?.cancel()

        // Iniciamos una nueva corutina con un retraso (debounce)
        searchJob = viewModelScope.launch {
            delay(500L) // Espera 500ms antes de ejecutar la solicitud (puedes ajustar el tiempo)

            val exists = doesPageExist(url)
            _state = state.copy(pageExists = exists)
        }
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
}