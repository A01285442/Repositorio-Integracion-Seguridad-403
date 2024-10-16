package com.example.legalmatch.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.legalmatch.data.api.models.EstudianteCaso
import com.example.legalmatch.data.api.models.SendEstudianteCaso
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.launch
import com.example.legalmatch.utils.TAG

data class EstudiantesInvolucradosState(
    val estudiantes: List<EstudianteCaso> = emptyList(),
    val isLoading: Boolean = false
)

class EstudiantesInvolucradosViewModel : ViewModel() {

    private var _state by mutableStateOf(EstudiantesInvolucradosState())
    val state: EstudiantesInvolucradosState get() = _state

    fun getEstudiantesInvolucrados(casoId: Int){
        _state = state.copy(isLoading = true)
        viewModelScope.launch {
            try {
                val listaEstudiantesInvolucrados = supabase.from("estudiantecaso")
                    .select() { filter { eq("caso_id", casoId) } }
                    .decodeList<EstudianteCaso>()

                _state = state.copy(estudiantes = listaEstudiantesInvolucrados)
            } catch (e: Exception){
                e.message?.let { Log.d(TAG, it) }
            } finally {
                _state = state.copy(isLoading = false)
            }
        }
    }

    fun deleteEstudianteInvolucrado(casoId: Int, usuarioId:Int){
        _state = state.copy(isLoading = true)
        viewModelScope.launch {
            try {
                supabase.from("estudiantecaso")
                    .delete(){ filter {
                        eq("caso_id", casoId)
                        eq("estudiante_id", usuarioId)
                    }}
            } catch (e: Exception){
                e.message?.let { Log.d(TAG, it) }
            } finally {
                getEstudiantesInvolucrados(casoId)
            }
        }
    }

    fun addEstudianteInvolucrado(casoId: Int, usuarioId: Int){
        Log.d(TAG, "Añadiendo estudiante inv")
        viewModelScope.launch {
            _state = state.copy(isLoading = true)
            val UsuarioAInsertar = SendEstudianteCaso(
                estudiante_id = usuarioId,
                caso_id = casoId
            )
            try {
                supabase.from("estudiantecaso").insert(UsuarioAInsertar)
            } catch (e: Exception){
                e.message?.let { Log.d(TAG, it) }
            } finally {
                getEstudiantesInvolucrados(casoId)
            }
        }
    }
}