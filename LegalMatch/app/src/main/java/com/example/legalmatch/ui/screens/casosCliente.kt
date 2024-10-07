package com.example.legalmatch.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.app.navigation.Routes
import com.example.legalmatch.ui.components.CustomBottomBarClientes
import com.example.legalmatch.ui.components.CustomTopBar
import com.example.legalmatch.ui.components.ItemCard
import com.example.legalmatch.ui.theme.GhostWhite

private const val TAG = "MainActivity"

@Composable
fun CasosClienteScreen(navController: NavController, loginVM: LoginViewModel) {

    loginVM.getCasosRelacionados()
    val state = loginVM.loginState.collectAsState()

    // Mostrar el contenido según el estado actual
    if (state.value.isLoading) {
        CircularProgressIndicator()
        return
    }

    Scaffold(
        topBar = { CustomTopBar(title = "Sus Casos", navIcon = false, actIcon = true, navController = navController, /*rutaActButton = Routes.CrearCasoCliente.route)*/) },
        bottomBar = { CustomBottomBarClientes(navController = navController)} // Usa el navController pasado
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(color = GhostWhite)
        ) {
            items(state.value.casosRelacionados) { caso -> // Cambié "Caso" a "caso" para mayor claridad
                ItemCard(
                    title = caso.delito,
                    description = caso.descripcion,
                    onClick = { navController.navigate(Routes.CasoDetalle.createRoute(caso.id))}
                )
            }
        }
    }
}