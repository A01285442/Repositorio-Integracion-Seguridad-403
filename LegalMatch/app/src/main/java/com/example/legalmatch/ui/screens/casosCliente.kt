package com.example.legalmatch.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.app.navigation.Routes
import com.example.legalmatch.ui.components.CustomBottomBarClientes
import com.example.legalmatch.ui.components.CustomTopBar
import com.example.legalmatch.ui.components.ItemCard
import com.example.legalmatch.ui.theme.AzulTec
import com.example.legalmatch.ui.theme.GhostWhite

private const val TAG = "MainActivity"

@Composable
fun CasosClienteScreen(navController: NavController, loginVM: LoginViewModel) {

    loginVM.getAsesoriasRelacionadas()
    loginVM.getCasosRelacionados()
    val state = loginVM.loginState.collectAsState()

    // Mostrar el contenido según el estado actual
    Scaffold(
        topBar = {
            CustomTopBar(
                title = "Su Actividad",
                navIcon = false,
                actIcon = true,
                navController = navController,
                rutaActButton = Routes.FormAsesoria.route
            )
        },
        bottomBar = { CustomBottomBarClientes(navController = navController) } // Usa el navController pasado
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(color = GhostWhite)
        )
        {
            item { Subtitulo("Casos") }
            items(state.value.casosRelacionados) { casos ->
                ItemCard(
                    title = casos.delito,
                    description = casos.descripcion,
                    onClick = { }
                )
            }

            item { Subtitulo("Asesorias") }
            items(state.value.asesoriasRelacionadas) { asesoria ->
                ItemCard(
                    title = asesoria.delito,
                    description = asesoria.descripcion,
                    onClick = { }
                )
            }
        }
    }
}