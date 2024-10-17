package com.example.legalmatch.Noticias

import android.content.Context
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.storage.Storage
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.delay

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import java.util.UUID
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


val supabase = createSupabaseClient(
    supabaseUrl = "https://wbhyplodhfxcyeochnpf.supabase.co",
    supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6IndiaHlwbG9kaGZ4Y3llb2NobnBmIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MjU0MjExNTUsImV4cCI6MjA0MDk5NzE1NX0.sqMEMPdCx9u-kC0TI0OLWKm1KXZjSkeDS1N3bQiG-jI",
) {
    install(Auth)
    install(Postgrest)
    install(Storage)
}

@Serializable
data class Noticia(
    val id: String,
    val titulo: String,
    val descripcion: String,
//    val fecha: String,
    val imagenurl: String? = null
)


@RequiresApi(Build.VERSION_CODES.O)
fun getCurrentDate(): String {
    val current = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
    return current.format(formatter)
}

private const val TAG = "NoticiasViewModel"

@RequiresApi(Build.VERSION_CODES.O)
class NoticiasViewModel : ViewModel() {

    private val _noticiasState = MutableStateFlow<List<Noticia>>(emptyList())
    val noticiasState: StateFlow<List<Noticia>> get() = _noticiasState

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    init {
        viewModelScope.launch {
            delay(1000)

            fetchNoticias()
        }
    }


    private suspend fun fetchNoticias() {
        _isLoading.value = true
        try {
            val noticias = supabase.from("noticias")
                .select()
                .decodeList<Noticia>()

            Log.d(TAG, "Noticias fetched: $noticias")
            _noticiasState.value = noticias
        } catch (e: Exception) {
            Log.e(TAG, "Error fetching noticias: ${e.message}", e)
        } finally {
            _isLoading.value = false
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun agregarNoticia(titulo: String, descripcion: String, imagenUri: Uri?, context: Context) {
        viewModelScope.launch {
            try {
                val imageUrl = if (imagenUri != null) {
                    uploadImageToSupabase(context, imagenUri)
                } else {
                    null
                }

                val nuevaNoticia = Noticia(
                    id = UUID.randomUUID().toString(),
                    titulo = titulo,
                    descripcion = descripcion,
//                    fecha = getCurrentDate(),
                    imagenurl = imageUrl
                )

                val response = supabase.from("noticias").insert(nuevaNoticia)
                Log.d(TAG, "Noticia agregada: $response")

                fetchNoticias()
            } catch (e: Exception) {
                Log.e(TAG, "Error agregando noticia: ${e.message}", e)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun uploadImageToSupabase(context: Context, uri: Uri): String? {
        return try {
            val imageBytes = uriToByteArray(context, uri)
            if (imageBytes != null) {
                val fileName = "images/${UUID.randomUUID()}.png"
                supabase.storage.from("NoticiasImagenes")
                    .upload(fileName, imageBytes)

                Log.d("Upload", "Imagen subida con éxito: $fileName")

                supabase.storage.from("NoticiasImagenes")
                    .publicUrl(fileName)
                    .toString()
            } else {
                Log.e("Upload", "Error al convertir la URI a ByteArray")
                null
            }
        } catch (e: Exception) {
            Log.e("Upload", "Error subiendo imagen: ${e.message}")
            null
        }
    }

    fun eliminarNoticia(id: String) {
        viewModelScope.launch {
            try {
                val response = supabase.from("noticias")
                    .delete(){
                        filter{
                            eq("id", id)
                        }
                    }
                Log.d(TAG, "Noticia eliminada: $response")
                fetchNoticias()
            } catch (e: Exception) {
                Log.e(TAG, "Error deleting noticia: ${e.message}", e)
            }
        }
    }

    // Convertir URI a ByteArray
    private suspend fun uriToByteArray(context: Context, uri: Uri): ByteArray? {
        return context.contentResolver.openInputStream(uri)?.readBytes()
    }


}
