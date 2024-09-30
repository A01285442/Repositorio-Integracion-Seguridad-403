package com.example.legalmatch.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.legalmatch.data.api.models.Asesoria
import com.example.legalmatch.data.api.models.Caso
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


private const val TAG = "MainActivity"

class AsesoriaViewModel() : ViewModel() {

    var asesorias by mutableStateOf(listOf<Asesoria>())
        private set

    var isLoading by mutableStateOf(true)
        private  set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    init {
        viewModelScope.launch {

            isLoading = true // Inicia el estado de carga

            //delay(1000)

            // Obtenemos información a través de la base de datos

            try {
                // Obtener una lista
                val fetchedAsesorias = supabase.from("asesorias")
                    .select()
                    .decodeList<Asesoria>()
                asesorias = fetchedAsesorias
                isLoading = false
            } catch (e: Exception) {
                errorMessage = e.message
                isLoading = false
            }
        }
    }
}
