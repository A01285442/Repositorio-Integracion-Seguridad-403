package com.example.legalmatch.Noticias

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.example.legalmatch.ui.components.CustomBottomBar
import com.example.legalmatch.ui.components.CustomTopBar

@Composable
fun NoticiasScreen(navController: NavHostController, noticiasViewModel: NoticiasViewModel) {
    // Observa el estado de las noticias
    val noticiasState = noticiasViewModel.noticiasState.collectAsState().value

    Scaffold(
        topBar = { CustomTopBar(title = "Noticias", navIcon = false, actIcon = false) },
        bottomBar = { CustomBottomBar(navController = navController) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    // Aquí navegas a la pantalla de agregar noticia o muestras un formulario
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
                    contentScale = ContentScale.Crop, // Para que la imagen se ajuste bien
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(top = 8.dp)
                )
            }
        }
    }
}
