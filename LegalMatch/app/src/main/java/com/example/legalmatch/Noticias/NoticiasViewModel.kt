package com.example.legalmatch.Noticias

import android.content.Context
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.legalmatch.viewmodel.Usuario
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.postgrest
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


val supabaseAxel = createSupabaseClient(
    supabaseUrl = "https://zqhgzbtzpdocuenpiqsj.supabase.co",
    supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InpxaGd6YnR6cGRvY3VlbnBpcXNqIiwicm9sZSI6ImFub24iLCJpYXQiOjE3Mjg0NDgyNjIsImV4cCI6MjA0NDAyNDI2Mn0.2JVMpHzlKjDIasi6kCq3lw1vXmOERS6NhvDz4avaZBI",
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
    val fecha: String,
    val imagenurl: String? = null
)


@RequiresApi(Build.VERSION_CODES.O)
fun getCurrentDate(): String {
    val current = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy") // Ajusta el formato si lo necesitas
    return current.format(formatter)
}

private const val TAG = "NoticiasViewModel"

// ViewModel for Noticias
@RequiresApi(Build.VERSION_CODES.O)
class NoticiasViewModel : ViewModel() {

    private val _noticiasState = MutableStateFlow<List<Noticia>>(emptyList())
    val noticiasState: StateFlow<List<Noticia>> get() = _noticiasState

    init {
        viewModelScope.launch {
            delay(1000)

            fetchNoticias()

        }
    }

    fun fetchNoticias() {
        viewModelScope.launch {
            try {
                val noticias = supabaseAxel.from("noticias")
                    .select()
                    .decodeList<Noticia>()

                Log.d(TAG, "Noticias fetched: $noticias")
                _noticiasState.value = noticias
            } catch (e: Exception) {
                Log.e(TAG, "Error fetching noticias: ${e.message}", e)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun agregarNoticia(titulo: String, descripcion: String, imagenUri: String?) {
        viewModelScope.launch {
            try {


                val nuevaNoticia = Noticia(
                    id = UUID.randomUUID().toString(),
                    titulo = titulo,
                    descripcion = descripcion,
                    fecha = getCurrentDate(),
                    imagenurl = imagenUri // La URL de la imagen será la URI en formato string
                )

                // Inserta la noticia en la base de datos
                val response = supabaseAxel.from("noticias")
                    .insert(nuevaNoticia)
                Log.d(TAG, "Noticia agregada: $response")

                // Actualiza la lista de noticias
                fetchNoticias()
            } catch (e: Exception) {
                Log.e(TAG, "Error agregando noticia: ${e.message}", e)
            }
        }
    }


    fun actualizarNoticia(id: Int, noticia: Noticia) {
        viewModelScope.launch {
            try {
                val response = supabaseAxel.from("noticias")
                    .update(noticia)
                    .decodeList<Noticia>()

                Log.d(TAG, "Noticia actualizada: $response")
                fetchNoticias() // Refresh the list after updating
            } catch (e: Exception) {
                Log.e(TAG, "Error updating noticia: ${e.message}", e)
            }
        }
    }

    fun eliminarNoticia(id: Int) {
        viewModelScope.launch {
            try {
                val response = supabaseAxel.from("noticias")
                    .delete()
                    .decodeList<Noticia>()
                Log.d(TAG, "Noticia eliminada: $response")
                fetchNoticias() // Refresh the list after deletion
            } catch (e: Exception) {
                Log.e(TAG, "Error deleting noticia: ${e.message}", e)
            }
        }
    }

    //Imagenes
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun uploadImageToSupabase(context: Context, uri: Uri) {
        val imageBytes = uriToByteArray(context, uri)
        imageBytes?.let {
            val fileName = "images/${UUID.randomUUID()}.png"
            try {
                val uploadResponse = supabaseAxel.storage.from("NoticiasImagenes")
                    .upload(fileName, imageBytes)

                Log.d("Upload", "Imagen subida con éxito: $fileName")

                // Obtiene la URL pública de la imagen
                val imageUrl = supabaseAxel.storage.from("NoticiasImagenes")
                    .publicUrl(fileName)
                    .toString()


                // Crea una nueva noticia con la URL de la imagen
                val nuevaNoticia = Noticia(
                    id = UUID.randomUUID().toString(),
                    titulo = "Título de la Noticia",
                    descripcion = "Descripción de la Noticia",
                    fecha = getCurrentDate(),
                    imagenurl = imageUrl
                )

                // Inserta la noticia en la base de datos
                val response = supabaseAxel.from("noticias")
                    .insert(nuevaNoticia)

                Log.d("Upload", "Noticia agregada: $response")
                fetchNoticias() // Actualiza la lista de noticias después de la inserción
            } catch (e: Exception) {
                Log.e("Upload", "Error subiendo imagen: ${e.message}")
            }
        }
    }


    // Convertir URI a ByteArray
    private suspend fun uriToByteArray(context: Context, uri: Uri): ByteArray? {
        return context.contentResolver.openInputStream(uri)?.readBytes()
    }


}
