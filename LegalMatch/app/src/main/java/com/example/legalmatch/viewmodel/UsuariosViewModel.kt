package com.example.legalmatch.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.legalmatch.data.api.models.SendUsuario
import com.example.legalmatch.data.api.models.Usuario
import com.example.legalmatch.ui.screens.supabase
import com.example.legalmatch.utils.md5
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Order
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime
import com.example.legalmatch.utils.TAG

data class EstudiantesState(
    val estudiantes: List<Usuario> = emptyList(),
    val infoCliente: Usuario = Usuario("","",LocalDateTime(1,1,1,1,1,1), LocalDateTime(1,1,1,1,1,1), 0, "","","estudiante", "hombre"),
    val infoAbogado: Usuario = Usuario("","",LocalDateTime(1,1,1,1,1,1), LocalDateTime(1,1,1,1,1,1), 0, "","","estudiante", "hombre"),
    val isLoading: Boolean = false
)

class UsuariosViewModel : ViewModel(){
    private var _state by mutableStateOf(EstudiantesState())
    val state: EstudiantesState get() = _state

    init {
        fetchEstudiantes()
    }

    fun getEstudianteInfo(id: Int) : Usuario? {
        return state.copy().estudiantes.firstOrNull{it.id == id}
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

    fun eliminaEstudiante(id: Int){

        viewModelScope.launch {
            try {
                supabase.from("usuarios").delete {
                    filter {
                        eq("id", id)
                    }
                }
            } catch (e: Exception){
                Log.d(TAG, "Error: e.message")
            }
            fetchEstudiantes()
        }
    }

    fun creaEstudiante(nombre: String, matricula: String){
        val _matricula = matricula.lowercase()
        val NewEstudiante = SendUsuario(
            contraseña = md5(_matricula),
            correo = _matricula+"@tec.mx",
            fecha_nacimiento = LocalDateTime(1,1,1,1,1,1),
            matricula = matricula,
            nombre = nombre,
            rol = "estudiante",
            sexo = "hombre"
        )
        viewModelScope.launch {
            try {
                supabase.from("usuarios").insert(NewEstudiante)
            } catch (e: Exception){
                Log.d(TAG, "Error: e.message")
            }
            fetchEstudiantes()
        }
    }

    fun getClientInfo(id: Int){
        viewModelScope.launch {
            try {
                _state = state.copy(isLoading = true)

                // Código para obtener cliehnte
                val cliente = supabase.from("usuarios")
                    .select(){ filter { eq("id", id) } }
                    .decodeSingleOrNull<Usuario>()

                _state = cliente?.let { state.copy(infoCliente = it, isLoading = false) }!!
            } catch (e: Exception) {
                Log.d(TAG,"Error: ${e.message}")
                _state = state.copy(isLoading = false) // Aquí también se usa correctamente
            }
        }
    }
    fun getAbogadoInfo(id: Int){
        viewModelScope.launch {
            try {
                _state = state.copy(isLoading = true)

                // Código para obtener cliehnte
                val cliente = supabase.from("usuarios")
                    .select(){ filter { eq("id", id) } }
                    .decodeSingleOrNull<Usuario>()

                _state = cliente?.let { state.copy(infoAbogado = it, isLoading = false) }!!
            } catch (e: Exception) {
                Log.d(TAG,"Error: ${e.message}")
                _state = state.copy(isLoading = false) // Aquí también se usa correctamente
            }
        }
    }

}