package com.example.legalmatch.ui.screens

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.legalmatch.data.api.models.Asesoria
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.launch


private const val TAG = "MainActivity"

data class AsesoriaState(
    val asesorias: List<Asesoria> = emptyList(),
    val isLoading: Boolean = true
)

class AsesoriaViewModel : ViewModel() {
    private var _state by mutableStateOf(AsesoriaState())
    val state: AsesoriaState get() = _state

    init {
        fetchAsesorias()
    }

    private fun fetchAsesorias() {

        viewModelScope.launch {
            Log.d(TAG, "Iniciando fetch")
            try {
                // Código para obtener asesorías
                val fetchedAsesorias = supabase.from("asesorias").select().decodeList<Asesoria>()
                _state = state.copy(asesorias = fetchedAsesorias, isLoading = false)
            } catch (e: Exception) {
                _state = state.copy(isLoading = false) // Aquí también se usa correctamente
            }
        }
    }
}
