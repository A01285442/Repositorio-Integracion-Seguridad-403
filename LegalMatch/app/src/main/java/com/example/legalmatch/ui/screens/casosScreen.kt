package com.example.legalmatch.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.app.navigation.Routes
import com.example.legalmatch.ui.components.CustomBottomBar
import com.example.legalmatch.ui.components.CustomTopBar
import com.example.legalmatch.ui.components.ItemCard
import com.example.legalmatch.ui.theme.GhostWhite
import com.example.legalmatch.viewmodel.SearchViewModel

private const val TAG = "MainActivity"

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun CasosScreen(
    navController: NavController,
    casosViewModel: CasosViewModel,
    searchBoxViewModel: SearchViewModel
) {
    val state = casosViewModel.state.copy()

    // Filtrar la lista de casos según el texto de búsqueda
    val filteredCasos = state.casos.filter {
        it.delito.contains(searchBoxViewModel.searchText, ignoreCase = true) // Filtrado por nombre (delito)
    }

    // Mostrar el contenido según el estado actual
    if (state.isLoading) {
        CircularProgressIndicator()
    }

    Scaffold(
        topBar = {CustomTopBar(
            title = "Casos Activos",
            navIcon = false,
            actIcon = true,
            navController = navController,
            rutaActButton = Routes.FormCaso.route,
            searchButton = true,
            searchVM = searchBoxViewModel
        ) },
        bottomBar = { CustomBottomBar(navController = navController) } // Usa el navController pasado
    ) { padding ->

        if (filteredCasos.isEmpty()){

            Text(
                text = "No hay casos activos",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
            )

            return@Scaffold
        }

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(color = GhostWhite)
        ) {

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