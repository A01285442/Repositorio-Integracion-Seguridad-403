package com.example.legalmatch.ui.screens

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.legalmatch.data.api.models.Asesoria
import com.example.legalmatch.ui.components.CustomBottomBar
import com.example.legalmatch.ui.components.CustomTopBar
import com.example.legalmatch.ui.components.ItemCard
import com.example.legalmatch.ui.theme.AzulTec
import com.example.legalmatch.ui.theme.GhostWhite
import java.time.LocalDateTime

private const val TAG = "MainActivity"

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("SimpleDateFormat")
@Composable
fun AsesoriaScreen(navController: NavController, asesoriaViewModel: AsesoriaViewModel, loginViewModel: LoginViewModel) {

    // En lugar de solo un booleano, se usa una asesoría seleccionada
    var selectedAsesoria by remember { mutableStateOf<Asesoria?>(null) }

    val state = asesoriaViewModel.state
    if (state.isLoading) {
        Box(modifier = Modifier.fillMaxSize().background(Color.White), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(modifier = Modifier.size(60.dp))
        }
    } else {
        Scaffold(
            topBar = { CustomTopBar(title = "Asesorías Pendientes", navIcon = false, actIcon = false) },
            bottomBar = { CustomBottomBar(navController = navController) }
        ) { padding ->
            val asesoriaList = state.asesorias

            if (asesoriaList.size <= 0){
                Card {
                    Text("No hay nada que mostrar.")
                }
            }

            LazyColumn(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .background(color = GhostWhite),
            ) {
                val now = LocalDateTime.now()


                val mesesEspanol = listOf("enero", "febrero", "marzo", "abril", "mayo", "junio", "julio", "agosto", "septiembre", "octubre", "noviembre", "diciembre")

                var subtituloAyerMostrado = false
                var subtituloHoyMostrado = false
                var subtituloMananaMostrado = false
                var subtituloOtroDiaMostrado = false



                items(asesoriaList) { asesoria ->
                    if (asesoria.fecha_asesoria.dayOfYear == now.dayOfYear - 1) {
                        if (!subtituloAyerMostrado) {
                            Subtitulo("Ayer")
                            subtituloAyerMostrado = true
                        }
                        ItemCard(
                            title = "${asesoria.fecha_asesoria.hour}:00 • ${asesoria.titulo}",
                            description = asesoria.descripcion,
                            onClick = { selectedAsesoria = asesoria } // Al hacer click, seleccionamos la asesoría
                        )
                    }
                    else if (asesoria.fecha_asesoria.dayOfYear == now.dayOfYear) {
                        if (!subtituloHoyMostrado) {
                            Subtitulo("Hoy")
                            subtituloHoyMostrado = true
                        }
                        ItemCard(
                            title = "${asesoria.fecha_asesoria.hour}:00 • ${asesoria.titulo}",
                            description = asesoria.descripcion,
                            onClick = { selectedAsesoria = asesoria }
                        )
                    }
                    else if (asesoria.fecha_asesoria.dayOfYear == now.dayOfYear + 1) {
                        if (!subtituloMananaMostrado) {
                            Subtitulo("Mañana")
                            subtituloMananaMostrado = true
                        }
                        ItemCard(
                            title = "${asesoria.fecha_asesoria.hour}:00 • ${asesoria.titulo}",
                            description = asesoria.descripcion,
                            onClick = { selectedAsesoria = asesoria }
                        )
                    }
                    else {
                        if (!subtituloOtroDiaMostrado) {
                            Subtitulo("${asesoria.fecha_asesoria.dayOfMonth} de ${mesesEspanol[asesoria.fecha_asesoria.monthNumber - 1]}")
                            subtituloOtroDiaMostrado = true
                        }
                        ItemCard(
                            title = "${asesoria.fecha_asesoria.hour}:00 • ${asesoria.titulo}",
                            description = asesoria.descripcion,
                            onClick = { selectedAsesoria = asesoria }
                        )
                    }
                }
            }
        }

        // Mostrar el diálogo fuera del LazyColumn
        selectedAsesoria?.let { asesoria ->
            ConfirmacionDialog(
                showDialog = selectedAsesoria != null,
                onDismiss = { selectedAsesoria = null }, // Cerrar el diálogo sin cambios
                onCancel = {
                    asesoriaViewModel.cancelarCaso(asesoria)
                    selectedAsesoria = null // Cerrar el diálogo
                },
                onConfirm = {
                    asesoriaViewModel.aceptarcaso(asesoria, loginViewModel)
                    selectedAsesoria = null // Cerrar el diálogo
                }
            )
        }
    }
}

@Composable
fun Subtitulo(text: String){
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium,
        color = AzulTec,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(12.dp)
    )
}

@Composable
fun ConfirmacionDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onCancel: () -> Unit,
    onConfirm: () -> Unit
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            title = { Text(text = "¿Quieres darle seguimiento a esta asesoría?") },
            text = { Text("Si aceptas el caso, la información pasará a la siguiente pestaña\nSi cancelas la asesoría, el usuario podrá reprogramarla.") },
            dismissButton = {
                Button(
                    onClick = onCancel,
                    colors = ButtonColors(Color.Red,Color.White,Color.Gray,Color.Gray)
                ) {
                    Text("Cancelar Asesoría")
                }
            },
            confirmButton = {
                Button(onClick = onConfirm,  colors = ButtonColors(AzulTec,Color.White,Color.Gray,Color.Gray)) {
                    Text("Aceptar Caso")
                }
            }
        )
    }
}