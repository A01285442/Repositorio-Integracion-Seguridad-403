package com.example.legalmatch.ui.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.legalmatch.data.api.models.Usuario
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


private const val TAG = "MainActivity"

class LoginViewModel() : ViewModel() {

    private val _isAuthenticated = MutableStateFlow(false)
    val isAuthenticated: StateFlow<Boolean> get() = _isAuthenticated

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> get() = _errorMessage

    // Si identifica al usuario cambia el valor de "isAuthenticated"
    fun login(username: String, password: String) {
        viewModelScope.launch {
            try {
                var result = false

                val response = supabase.from("usuarios")
                    .select()
                    .decodeList<Usuario>()

                val users = response

                // Encontrar el usuario por correo
                val user = users.firstOrNull { it.correo == username }

                if (user != null) {
                    if (user.contraseña == password) {
                        result = true
                    } else {
                        //_errorMessage.value = "Contraseña Incorrecta"
                    }
                } else {
                    //_errorMessage.value = "Usuario no encontrado"
                }

                if (result) {
                    _isAuthenticated.value = true
                } else {
                    //_errorMessage.value = "Credenciales incorrectas"
                }
            } catch (e: Exception) {
                //_errorMessage.value = "Error en la autenticación: ${e.message}"
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
