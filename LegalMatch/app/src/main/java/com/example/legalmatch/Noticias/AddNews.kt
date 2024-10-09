package com.example.legalmatch.Noticias

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.example.legalmatch.ui.components.CustomTopBar
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddNewsScreen(noticiasViewModel: NoticiasViewModel, navController: NavHostController) {
    var titulo by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var imagenUri by remember { mutableStateOf<Uri?>(null) }

    val context = LocalContext.current

    // Intent launcher para seleccionar imagen
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.data?.let { uri ->
                imagenUri = uri

                noticiasViewModel.viewModelScope.launch {
                    noticiasViewModel.uploadImageToSupabase(context, uri)
                }
            }
        }
    }

    Scaffold(
        topBar = { CustomTopBar(title = "Agregar Noticia", navIcon = true, actIcon = false) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // título
            TextField(
                value = titulo,
                onValueChange = { titulo = it },
                label = { Text("Título") },
                modifier = Modifier.fillMaxWidth()
            )

            // descripción
            TextField(
                value = descripcion,
                onValueChange = { descripcion = it },
                label = { Text("Descripción") },
                modifier = Modifier.fillMaxWidth()
            )

            // imagen
            Button(
                onClick = {
                    // Abre el intent para seleccionar imagen
                    val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    launcher.launch(intent)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Agregar Imagen")
            }

            // Mostrar la imagen seleccionada (si existe)
            imagenUri?.let {
                AsyncImage(
                    model = it,
                    contentDescription = "Imagen seleccionada",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )
            }

            // guardar la noticia
            Button(
                onClick = {
                    // Verifica que los campos no estén vacíos antes de guardar
                    if (titulo.isNotEmpty() && descripcion.isNotEmpty() && imagenUri != null) {
                        noticiasViewModel.agregarNoticia( // Se manda al DB
                            titulo = titulo,
                            descripcion = descripcion,
                            imagenUri = imagenUri, // Aquí pasa el Uri directamente
                            context = context // Pasa el contexto para la función de subir imagen
                        )
                        navController.popBackStack() // Navega hacia atrás después de guardar
                    } else {
                        Toast.makeText(context, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar Noticia")
            }

        }
    }
}
