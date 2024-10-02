package com.example.legalmatch.ui.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.legalmatch.data.api.models.Usuario
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


private const val TAG = "MainActivity"

class LoginViewModel() : ViewModel() {

    private val _isAuthenticated = MutableStateFlow(false)
    val isAuthenticated: StateFlow<Boolean> get() = _isAuthenticated

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> get() = _errorMessage

    private val _userClient = MutableStateFlow<Usuario?>(null)
    val userClient : StateFlow<Usuario?> get() = _userClient

    // Si identifica al usuario cambia el valor de "isAuthenticated"
    fun login(username: String, password: String, onLoginSuccess: () -> Unit) {
        viewModelScope.launch {
            try {

                val user = supabase.from("usuarios")
                    .select() {
                        filter {
                            eq("correo", username)
                        }

                    }.decodeSingleOrNull<Usuario>()

                if (user != null) {
                    if (user.contraseña == password) {
                        _userClient.value = user
                        _isAuthenticated.value = true
                        Log.d(TAG, "Usuario encontrado, iniciando...")
                        onLoginSuccess()
                    } else {
                        _errorMessage.value = "Contraseña Incorrecta"
                    }
                } else {
                    _errorMessage.value = "Usuario no encontrado"
                }

            } catch (e: Exception) {
                _errorMessage.value = "Error en la autenticación: ${e.message}"
            }
        }
    }

    fun registerClient(newUsuario: Usuario){
        viewModelScope.launch {
            try {
                supabase.postgrest["usuarios"].insert(newUsuario)
            } catch (e: Exception){
                e.printStackTrace()
                Log.d(TAG,e.message.toString())
            }
        }
    }

}
