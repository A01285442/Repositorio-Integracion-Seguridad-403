package com.example.legalmatch.viewmodel

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.legalmatch.ui.screens.supabaseCli
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

// Supabase
val supabase = createSupabaseClient(
    supabaseUrl = "https://wbhyplodhfxcyeochnpf.supabase.co",
    supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6IndiaHlwbG9kaGZ4Y3llb2NobnBmIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MjU0MjExNTUsImV4cCI6MjA0MDk5NzE1NX0.sqMEMPdCx9u-kC0TI0OLWKm1KXZjSkeDS1N3bQiG-jI",
) {
    install(Auth)
    install(Postgrest)
}

private const val TAG = "MainActivity"

@Serializable
data class Usuario(
    val sexo: String,
)

data class StatsState(
    val sexo: String,
    val count: Int
)

class GraficasViewModel : ViewModel() {
    private val _statsState = MutableStateFlow<List<StatsState>>(emptyList())
    val statsState: StateFlow<List<StatsState>> get() = _statsState

    init {
        fetchSexoCounts()
    }
    fun fetchSexoCounts() {
        viewModelScope.launch {
            try {
                val usuarios = supabase.from("usuarios")
                    .select()
                    .decodeList<Usuario>()

                val groupedCounts = usuarios.groupBy {it.sexo}
                    .map { StatsState(it.key.capitalize(), it.value.size) }
                Log.d(TAG, "grupos: $groupedCounts")

                _statsState.value = groupedCounts

            } catch (e: Exception) {
                Log.e(TAG, "Error fetching sexo counts: ${e.message}", e)
            }
        }
    }
}