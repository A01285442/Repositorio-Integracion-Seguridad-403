package com.example.legalmatch.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.legalmatch.data.api.models.Usuario
import com.example.legalmatch.ui.screens.supabase
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Order
import kotlinx.coroutines.launch

private const val TAG = "MainActivity"

data class EstudiantesState(
    val estudiantes: List<Usuario> = emptyList(),
    val isLoading: Boolean = false
)

class EstudiantesViewmodel : ViewModel(){
    private var _state by mutableStateOf(EstudiantesState())
    val state: EstudiantesState get() = _state

    init {
        fetchEstudiantes()
    }

    fun fetchEstudiantes(){
        viewModelScope.launch {
            try {
                // Código para obtener asesorías
                val fetchedEstudiantes = supabase.from("usuarios")
                    .select(){
                        filter {
                            eq("rol", "estudiante")
                        }
                        order(column = "nombre", order = Order.ASCENDING)
                    }
                    .decodeList<Usuario>()
                _state = state.copy(estudiantes = fetchedEstudiantes, isLoading = false)
            } catch (e: Exception) {
                Log.d(TAG,"Error: ${e.message}")
                _state = state.copy(isLoading = false) // Aquí también se usa correctamente
            }
        }
    }
}