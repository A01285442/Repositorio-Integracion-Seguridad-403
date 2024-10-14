package com.example.legalmatch.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SnackbarDemo() {
    // Estado para controlar la visibilidad del Snackbar
    val snackbarHostState = remember { SnackbarHostState() }

    // Mostrar el Snackbar cuando sea necesario
    LaunchedEffect(Unit) {
        snackbarHostState.showSnackbar("Mensaje de ejemplo", actionLabel = "Deshacer")
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) {
        // Contenido de tu pantalla
        Text("Presiona algo para mostrar el Snackbar", modifier = Modifier.padding(16.dp))
    }
}

@Preview
@Composable
fun PreviewSnackbarDemo() {
    SnackbarDemo()
}