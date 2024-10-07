package com.example.legalmatch.ui.screens

import android.util.Log
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

var UserID = 0

// Supabase
val supabaseCasoCliente = createSupabaseClient(
    supabaseUrl = "https://wbhyplodhfxcyeochnpf.supabase.co",
    supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6IndiaHlwbG9kaGZ4Y3llb2NobnBmIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MjU0MjExNTUsImV4cCI6MjA0MDk5NzE1NX0.sqMEMPdCx9u-kC0TI0OLWKm1KXZjSkeDS1N3bQiG-jI",
) {
    install(Auth)
    install(Postgrest)
}

private const val TAG = "MainActivity"

data class CasoClienteState(
    val casos: List<Caso> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String = ""

)

class casosClienteViewModel() : ViewModel() {

    private var _state by mutableStateOf(CasoState())
    val state: CasoState get() = _state

    init {
        fetchCasos()
    }


    fun getCasoInfo(id: Int) : Caso? {
        return state.copy().casos.firstOrNull{it.id == id}
    }


    private fun fetchCasos(){
        viewModelScope.launch {

            _state = state.copy(isLoading = true) // Inicia el estado de carga

            // Obtenemos información a través de la base de datos

            try {
                // Obtener una lista
                val fetchedCasos = supabaseCasoCliente
                    .from("casos")
                    .select(){ filter{ eq("id_cliente", UserID)}}
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

fun setUserID2(userID: Int){
    UserID = userID
}