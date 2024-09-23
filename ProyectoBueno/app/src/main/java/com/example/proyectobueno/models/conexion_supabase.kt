package com.example.proyectobueno.models


import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.postgrest
import io.ktor.websocket.WebSocketDeflateExtension.Companion.install
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable

// Configuración de Supabase
val supabase = createSupabaseClient(
    supabaseUrl = "https://xnmoocycyrqwcztshmra.supabase.co",
    supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InhubW9vY3ljeXJxd2N6dHNobXJhIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MjcwOTkwODEsImV4cCI6MjA0MjY3NTA4MX0.uUSXcWNm6NIpsUvUIoJvEtPdqWIjp_VdEp5jgHe01H8"
) {
    install(Auth)
    install(Postgrest)
}

// Definición del modelo de datos de casos
@Serializable
data class Casoss(
    val name: String,
    val email: String
)

// Función que obtiene la lista de casos desde la base de datos Supabase
suspend fun CasoLista(): List<Casoss> {
    return withContext(Dispatchers.IO) {
        val response = supabase.postgrest["Prueba"].select().decodeList<Casoss>()
        response // Devolvemos la lista de casos
    }
}
