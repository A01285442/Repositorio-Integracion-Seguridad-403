package com.example.legalmatch.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.app.navigation.Routes
import com.example.legalmatch.data.api.models.Caso
import com.example.legalmatch.ui.components.CustomBottomBar
import com.example.legalmatch.ui.components.CustomTopBar
import com.example.legalmatch.ui.theme.GhostWhite

private const val TAG = "MainActivity"

@Composable
fun CasosClienteScreen(navController: NavController, casosClienteViewModel: CasosViewModel) {

    val state = casosClienteViewModel.state.copy()

    // Mostrar el contenido según el estado actual
    if (state.isLoading) {
        CircularProgressIndicator()
    }

    Scaffold(
        topBar = { CustomTopBar(title = "Sus Casos", navIcon = false, actIcon = true) },
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(color = GhostWhite)
        ) {
            items(state.casos) { caso -> // Cambié "Caso" a "caso" para mayor claridad
                CasoClienteItem(caso)
            }
        }
    }
}


@Composable
fun CasoClienteItem(caso: Caso) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable {},
        elevation = CardDefaults.cardElevation(10.dp),
        colors = CardColors(
            containerColor = Color.White,
            contentColor = Color.Black,
            disabledContainerColor = Color.LightGray,
            disabledContentColor = Color.DarkGray

        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Text(text = caso.delito, fontWeight = FontWeight.Bold)
            Text(text = caso.c_investigacion)
            Text(text = "Fiscalia: ".plus(caso.fiscalia_virtual))


        }
    }
}

/*
@Preview
@Composable
fun PreviewCasosScreen() {
    CasosScreen()
}
*/