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
import com.example.legalmatch.ui.theme.AzulTec
import com.example.legalmatch.ui.theme.GhostWhite
import java.time.LocalDateTime

private const val TAG = "MainActivity"

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("SimpleDateFormat")
@Composable
fun AsesoriaScreen(navController: NavController,asesoriaViewModel: AsesoriaViewModel, loginViewModel: LoginViewModel) {

    val state = asesoriaViewModel.state
    if (state.isLoading){
        Log.d(TAG, "Loading started")
        Box(modifier = Modifier.fillMaxSize().background(Color.White), contentAlignment = Alignment.Center){
            CircularProgressIndicator(modifier = Modifier.size(60.dp))
        }
    } else {
        Log.d(TAG, "finished loading")
        Scaffold(
            topBar = { CustomTopBar(title = "Asesorías", navIcon = false, actIcon = true) },
            bottomBar = { CustomBottomBar(navController = navController) }
        ) { padding ->
            LazyColumn(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .background(color = GhostWhite),
            ) {
                val now = LocalDateTime.now()
                val asesoriaList = state.asesorias

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
                        AsesoriaItem(asesoria,asesoriaViewModel,loginViewModel)
                    }
                    else if (asesoria.fecha_asesoria.dayOfYear == now.dayOfYear) {
                        if (!subtituloHoyMostrado) {
                            Subtitulo("Hoy")
                            subtituloHoyMostrado = true
                        }
                        AsesoriaItem(asesoria,asesoriaViewModel,loginViewModel)
                    }
                    else if (asesoria.fecha_asesoria.dayOfYear == now.dayOfYear + 1) {
                        if (!subtituloMananaMostrado) {
                            Subtitulo("Mañana")
                            subtituloMananaMostrado = true
                        }
                        AsesoriaItem(asesoria,asesoriaViewModel,loginViewModel)
                    }
                    else {
                        // Para otras fechas, puedes usar una lógica similar, comparando por día y mes
                        if (!subtituloOtroDiaMostrado) {
                            Subtitulo("${asesoria.fecha_asesoria.dayOfMonth} de ${mesesEspanol[asesoria.fecha_asesoria.monthNumber - 1]}")
                            subtituloOtroDiaMostrado = true
                        }
                        AsesoriaItem(asesoria,asesoriaViewModel,loginViewModel)
                    }
                }

            }
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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AsesoriaItem(asesoria: Asesoria,asesoriaViewModel: AsesoriaViewModel, loginViewModel: LoginViewModel) {
    var showDialog by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { showDialog = true },
        elevation = CardDefaults.cardElevation(10.dp),
        colors = CardColors(
            containerColor = Color.White,
            contentColor = Color.Black,
            disabledContainerColor = Color.LightGray,
            disabledContentColor = Color.DarkGray
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            var showFullDescription by remember { mutableStateOf(false) }
            Row(
                Modifier.fillMaxWidth()
            ){
                Text(text = asesoria.fecha_asesoria.hour.toString().plus(":00"), fontWeight = FontWeight.Bold)
                Text(text = " • ", fontWeight = FontWeight.Bold)
                Text(text = asesoria.titulo, fontWeight = FontWeight.Bold)
            }

            // Descripción (completa o truncada)
            val maxChar = 150
            if (showFullDescription) {
                Text(text = asesoria.descripcion)
            } else {
                val truncatedDescription = if (asesoria.descripcion.length > maxChar) {
                    asesoria.descripcion.substring(0, maxChar).plus("...")
                } else {
                    asesoria.descripcion
                }
                Text(text = truncatedDescription)
            }

            // Ícono para expandir o colapsar la descripción

            Row(
                modifier = Modifier.fillMaxWidth().padding(0.dp),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(
                    onClick = { showFullDescription = !showFullDescription },
                ) {
                    Text(
                        text = if (showFullDescription) "Ver menos" else "Ver más",
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(0.dp)
                    )
                }
            }
            


        }
    }
    // Mostrar el AlertDialog si el estado showDialog es verdadero
    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
                // Acción cuando se cierra el diálogo (fuera del área de alerta o con el botón "Cancelar")
                showDialog = false
            },
            title = { Text(text = "¿Quieres darle seguimiento a esta asesoría?") },
            text = { Text("Si aceptas, estos datos pasarán a la ventana de 'Casos', desde donde podrás manejar el resto de información necesaria") },
            confirmButton = {
                Button(
                    colors = ButtonColors(AzulTec,Color.White,Color.Gray,Color.Gray),
                    onClick = {
                        asesoriaViewModel.aceptarcaso(asesoria, loginViewModel)
                        showDialog = false
                    }) {
                    Text("Aceptar Caso")
                }
            },
            dismissButton = {
                Button(
                    colors = ButtonColors(AzulTec,Color.White,Color.Gray,Color.Gray),
                    onClick = {

                        // Acción del botón de cancelación
                        showDialog = false
                    }) {
                    Text("Cancelar")
                }
            }
        )
    }
}