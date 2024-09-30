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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime

// Supabase
val supabase = createSupabaseClient(
    supabaseUrl = "https://wbhyplodhfxcyeochnpf.supabase.co",
    supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6IndiaHlwbG9kaGZ4Y3llb2NobnBmIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MjU0MjExNTUsImV4cCI6MjA0MDk5NzE1NX0.sqMEMPdCx9u-kC0TI0OLWKm1KXZjSkeDS1N3bQiG-jI",
) {
    install(Auth)
    install(Postgrest)
}

private const val TAG = "MainActivity"

class CasosViewModel() : ViewModel() {

    var casos by mutableStateOf(listOf<Caso>())
        private set

    var isLoading by mutableStateOf(true)
        private  set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun getCasoInfo(id: Int) : Caso {
        var casoDetalle = casos.firstOrNull { it.id == id }
        if (casoDetalle == null){
            casoDetalle = Caso(0, LocalDateTime(1,1,1,1,1,1),false,"NaN","NaN","NaN",0,0)
        }
        return casoDetalle
    }


    init {
        viewModelScope.launch {

            isLoading = true // Inicia el estado de carga

            //delay(1000)

            // Obtenemos información a través de la base de datos

            try {
                // Obtener una lista
                val fetchedCasos = supabase.from("casos")
                    .select()
                    .decodeList<Caso>()
                casos = fetchedCasos
                isLoading = false
            } catch (e: Exception) {
                errorMessage = e.message
                isLoading = false
            }
        }
    }


}
