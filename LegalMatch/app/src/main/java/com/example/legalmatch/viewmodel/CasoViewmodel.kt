package com.example.legalmatch.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.legalmatch.data.api.models.Caso
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.launch

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
    val errorMessage: String = ""

)

class CasosViewModel() : ViewModel() {

    private var _state by mutableStateOf(CasoState())
    val state: CasoState get() = _state


    fun getCasoInfo(id: Int) : Caso? {
        return state.copy().casos.firstOrNull{it.id == id}
    }


    init {
        viewModelScope.launch {

            _state = state.copy(isLoading = true) // Inicia el estado de carga

            // Obtenemos información a través de la base de datos

            try {
                // Obtener una lista
                val fetchedCasos = supabase.from("casos")
                    .select()
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