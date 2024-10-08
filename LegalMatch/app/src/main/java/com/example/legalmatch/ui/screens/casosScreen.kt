package com.example.legalmatch.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.app.navigation.Routes
import com.example.legalmatch.R
import com.example.legalmatch.data.api.models.Caso
import com.example.legalmatch.ui.components.CustomBottomBar
import com.example.legalmatch.ui.components.CustomTopBar
import com.example.legalmatch.ui.components.ItemCard
import com.example.legalmatch.ui.theme.GhostWhite
import kotlin.math.sin

private const val TAG = "MainActivity"

@Composable
fun CasosScreen(navController: NavController, casosViewModel: CasosViewModel) {
    val state = casosViewModel.state.copy()

    var searchText by remember { mutableStateOf("") } // Estado para el texto de búsqueda

    // Filtrar la lista de casos según el texto de búsqueda
    val filteredCasos = state.casos.filter {
        it.delito.contains(searchText, ignoreCase = true) // Filtrado por nombre (delito)
    }

    // Mostrar el contenido según el estado actual
    if (state.isLoading) {
        CircularProgressIndicator()
    }

    Scaffold(
        topBar = { CustomTopBar(title = "Casos Activos", navIcon = false, actIcon = true) },
        bottomBar = { CustomBottomBar(navController = navController) } // Usa el navController pasado
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(color = GhostWhite)
        ) {

            Row {
                // Input de búsqueda
                OutlinedTextField(
                    value = searchText,
                    onValueChange = { searchText = it }, // Actualizar el texto de búsqueda
                    label = { Text("Buscar por nombre de caso", style = MaterialTheme.typography.titleSmall) },
                    singleLine = true,
                    modifier = Modifier
                        .weight(5f)
                        .padding(16.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.filter),
                    contentDescription = "Casos",
                    modifier = Modifier.size(100.dp).padding(24.dp)
                )
            }


            // Lista de casos filtrados
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = GhostWhite)
            ) {
                items(filteredCasos) { caso -> // Mostrar solo los casos filtrados
                    ItemCard(
                        title = caso.delito,
                        description = caso.descripcion,
                        onClick = { navController.navigate(Routes.CasoDetalle.createRoute(caso.id)) }
                    )
                }
            }
        }
    }
}