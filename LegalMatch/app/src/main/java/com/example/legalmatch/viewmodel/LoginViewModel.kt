package com.example.legalmatch.ui.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app.navigation.AppNavGraph
import com.example.legalmatch.data.api.models.Asesoria
import com.example.legalmatch.data.api.models.Caso
import com.example.legalmatch.data.api.models.SendUsuario
import com.example.legalmatch.data.api.models.Usuario
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


private const val TAG = "MainActivity"

val supabaseCli = createSupabaseClient(
    supabaseUrl = "https://wbhyplodhfxcyeochnpf.supabase.co",
    supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6IndiaHlwbG9kaGZ4Y3llb2NobnBmIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MjU0MjExNTUsImV4cCI6MjA0MDk5NzE1NX0.sqMEMPdCx9u-kC0TI0OLWKm1KXZjSkeDS1N3bQiG-jI",
) {
    install(Auth)
    install(Postgrest)
}

data class LoginState(
    val isAuthenticated: Boolean = false,
    val userClient: Usuario? = null,
    val errorMessage: String? = null,
    val isLoading: Boolean = false,
    val asesoriasRelacionadas: List<Asesoria> = emptyList(),
    val casosRelacionados: List<Caso> = emptyList()
)


class LoginViewModel() : ViewModel() {

    private val _loginState = MutableStateFlow(LoginState())
    val loginState: StateFlow<LoginState> get() = _loginState

    fun cambioContraseña(password: String) {
        viewModelScope.launch {
            try {
                supabaseCli.from("usuarios").update({
                    set("contraseña", password)
                }) {
                    filter {
                        loginState.value.userClient?.let { eq("id", it.id) }
                    }
                }
            } catch (e: Exception) {
                Log.d(TAG, "Error en la autenticacion: ${e.message}")
                _loginState.value = _loginState.value.copy(
                    errorMessage = "Error en la autenticación: ${e.message}",
                    isLoading = false
                )
            }
        }
    }

    // Si identifica al usuario cambia el valor de "isAuthenticated"
    fun login(username: String, password: String, onLoginSuccessAbogado: () -> Unit, onLoginSuccessCliente: () -> Unit, onLoginError: () -> Unit) {
        Log.d(TAG, "Iniciando función login()")  // Log antes de la coroutine
        viewModelScope.launch {
            Log.d(TAG, "Iniciando coroutine")  // Log dentro de la coroutine
            try {
                val user = supabaseCli.from("usuarios")
                    .select() { filter { eq("correo", username) } }
                        .decodeSingleOrNull<Usuario>()

                if (user != null) {
                    if (user.contraseña == password) {
                        _loginState.value = _loginState.value.copy(
                            isAuthenticated = true,
                            userClient = user,
                            errorMessage = null,
                            isLoading = false
                        )
                        Log.d(TAG, "Usuario encontrado: ${user.correo}")
                        //setUserID2(user.id)
                        // Dependiendo del rol del usuario te manda a otra pantalla, abogados y clientes.
                        if(user.rol == "cliente"){
                            onLoginSuccessCliente()
                        }
                        else{
                            onLoginSuccessAbogado()
                        }
                    } else {
                        Log.d(TAG, "Contraseña Incorrecta")
                        onLoginError()
                        _loginState.value = _loginState.value.copy(
                            errorMessage = "Contraseña Incorrecta",
                            isLoading = false
                        )
                    }
                } else {
                    Log.d(TAG, "Usuario no encontrado")
                    onLoginError()
                    _loginState.value = _loginState.value.copy(
                        errorMessage = "Usuario no encontrado",
                        isLoading = false
                    )
                }

            } catch (e: Exception) {
                Log.d(TAG, "Error en la autenticacion: ${e.message}")
                onLoginError()
                _loginState.value = _loginState.value.copy(
                    errorMessage = "Error en la autenticación: ${e.message}",
                    isLoading = false
                )
            }
        }
    }

    fun registerClient(newUsuario: SendUsuario) {
        viewModelScope.launch {
            try {
                supabase.postgrest["usuarios"].insert(newUsuario)
            } catch (e: Exception) {
                e.printStackTrace()
                Log.d(TAG, e.message.toString())
            }
        }
    }

    fun closeSession(then: () -> Unit) {
        _loginState.value = _loginState.value.copy(
            isAuthenticated = false,
            userClient = null,
            errorMessage = null,
            isLoading = false
        )
        Log.d(TAG, "Sesión cerrada")
        then()
    }

    // Funciones para la vista de Cliente
    fun getAsesoriasRelacionadas(){
        viewModelScope.launch {

            _loginState.value = _loginState.value.copy(isLoading = true) // Inicia el estado de carga

            // Obtenemos información a través de la base de datos

            try {
                // Obtener una lista
                val fetchedCasos = supabaseCasoCliente.from("asesorias")
                    .select(){ filter{ _loginState.value.userClient?.let { eq("id_cliente", it.id) } }}
                    .decodeList<Asesoria>()
                _loginState.value = _loginState.value.copy(asesoriasRelacionadas = fetchedCasos, isLoading = false)

            } catch (e: Exception) {
                e.message?.let {
                    _loginState.value = _loginState.value.copy(
                        errorMessage = e.message!!,
                        isLoading = false
                    )
                }
            }
        }
    }

    fun getCasosRelacionados(){
        viewModelScope.launch {

            _loginState.value = _loginState.value.copy(isLoading = true) // Inicia el estado de carga

            // Obtenemos información a través de la base de datos

            try {
                // Obtener una lista
                val fetchedCasos = supabaseCasoCliente.from("casos")
                    .select(){ filter{ _loginState.value.userClient?.let { eq("id_cliente", it.id) } }}
                    .decodeList<Caso>()
                _loginState.value = _loginState.value.copy(casosRelacionados = fetchedCasos, isLoading = false)

            } catch (e: Exception) {
                e.message?.let {
                    _loginState.value = _loginState.value.copy(
                        errorMessage = e.message!!,
                        isLoading = false
                    )
                }
            }
        }
    }
}