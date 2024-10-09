package com.example.legalmatch.Noticias

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.example.legalmatch.ui.components.CustomTopBar

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddNewsScreen(noticiasViewModel: NoticiasViewModel, navController: NavHostController) {
    var titulo by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var imagenUri by remember { mutableStateOf<Uri?>(null) }

    // Intent launcher para seleccionar imagen
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.data?.let { uri ->
                imagenUri = uri
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

            // TextField para el título
            TextField(
                value = titulo,
                onValueChange = { titulo = it },
                label = { Text("Título") },
                modifier = Modifier.fillMaxWidth()
            )

            // TextField para la descripción
            TextField(
                value = descripcion,
                onValueChange = { descripcion = it },
                label = { Text("Descripción") },
                modifier = Modifier.fillMaxWidth()
            )

            // Botón para seleccionar una imagen
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

            // Botón para guardar la noticia
            Button(
                onClick = {
                    // Lógica para enviar la noticia a la base de datos
                    noticiasViewModel.agregarNoticia(
                        titulo = titulo,
                        descripcion = descripcion,
                        imagenUri = imagenUri.toString() // Convertimos la Uri a string
                    )
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar Noticia")
            }
        }
    }
}
