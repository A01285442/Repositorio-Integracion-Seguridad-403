package com.example.legalmatch.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.legalmatch.ui.components.CustomBottomBar
import com.example.legalmatch.ui.components.CustomTopBar
import com.example.legalmatch.ui.theme.GhostWhite

// ViewModel simulado (sin lógica aún)
class InfoViewModel : ViewModel() {
    var videoUrl by mutableStateOf(TextFieldValue(""))
    var imageUrl by mutableStateOf(TextFieldValue(""))
    var newsText by mutableStateOf(TextFieldValue(""))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoScreen(navController: NavController, infoViewModel: InfoViewModel = viewModel()) {

    Scaffold(
        topBar = {
            CustomTopBar(title = "Bufete de Abogados - Cargar Contenido", navIcon = false, actIcon = true)
        },
        bottomBar = {
            CustomBottomBar(navController = navController) // Barra inferior
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(color = GhostWhite)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Campo para URL del video
            Text(text = "Cargar Video:")
            BasicTextField(
                value = infoViewModel.videoUrl,
                onValueChange = { infoViewModel.videoUrl = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .border(1.dp, MaterialTheme.colorScheme.primary)
                    .padding(8.dp)
            )

            // Campo para URL de la imagen
            Text(text = "Cargar Imagen:")
            BasicTextField(
                value = infoViewModel.imageUrl,
                onValueChange = { infoViewModel.imageUrl = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .border(1.dp, MaterialTheme.colorScheme.primary)
                    .padding(8.dp)
            )

            // Campo para el texto de la noticia
            Text(text = "Agregar Noticia:")
            BasicTextField(
                value = infoViewModel.newsText,
                onValueChange = { infoViewModel.newsText = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .border(1.dp, MaterialTheme.colorScheme.primary)
                    .padding(8.dp)
            )

            // Botón para agregar contenido
            Button(
                onClick = { /* Acciones para agregar contenido */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Agregar")
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun InfoScreenPreview() {
    InfoScreen(navController = rememberNavController())
}