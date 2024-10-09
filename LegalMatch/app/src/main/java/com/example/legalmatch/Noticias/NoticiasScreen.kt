package com.example.legalmatch.Noticias

import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import android.net.Uri
import androidx.compose.runtime.rememberCoroutineScope
import coil3.compose.AsyncImage
import com.example.legalmatch.ui.components.CustomBottomBar
import com.example.legalmatch.ui.components.CustomTopBar
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NoticiasScreen(navController: NavHostController, noticiasViewModel: NoticiasViewModel) {
    val noticiasState = noticiasViewModel.noticiasState.collectAsState().value
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    val isLoading = remember { mutableStateOf(true) }
    val errorMessage = remember { mutableStateOf<String?>(null) }

    val getImage = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        selectedImageUri = uri
    }

    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = { CustomTopBar(title = "Noticias", navIcon = false, actIcon = false) },
        bottomBar = { CustomBottomBar(navController = navController) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate("AddNews")
                },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Agregar noticia")
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            items(noticiasState) { noticia ->
                NoticiaItem(noticia)
            }
        }
    }
}

@Composable
fun NoticiaItem(noticia: Noticia) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            // Título de la noticia
            Text(
                text = noticia.titulo,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Descripción de la noticia
            Text(
                text = noticia.descripcion,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Imagen de la noticia (solo si existe una URL válida)
            noticia.imagenurl?.let { imagenUrl ->
                AsyncImage(
                    model = imagenUrl,
                    contentDescription = "Imagen de la noticia",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(top = 8.dp)

                )
            }
        }
    }
}
