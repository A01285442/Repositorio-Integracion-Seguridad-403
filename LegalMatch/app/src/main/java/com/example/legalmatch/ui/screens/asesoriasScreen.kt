package com.example.legalmatch.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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

@Composable
fun AsesoriaScreen(navController: NavController,asesoriaViewModel: AsesoriaViewModel) {

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
            item {
                Text(
                    text = "Hoy",
                    style = MaterialTheme.typography.titleMedium,
                    color = AzulTec,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(12.dp)
                )
            }

            items(asesoriaViewModel.asesorias) { asesoria ->
                AsesoriaItem(asesoria)
            }

            item {
                Text(
                    text = "Mañana",
                    style = MaterialTheme.typography.titleMedium,
                    color = AzulTec,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(12.dp)
                )
            }

            items(asesoriaViewModel.asesorias) { asesoria ->
                AsesoriaItem(asesoria)
            }
        }
    }
}

@Composable
fun AsesoriaItem(asesoria: Asesoria) {
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
            Row(
                Modifier.fillMaxWidth()
            ){
                Text(text = asesoria.fecha_asesoria.hour.toString().plus(":00"), fontWeight = FontWeight.Bold)
                Text(text = " • ", fontWeight = FontWeight.Bold)
                Text(text = asesoria.titulo, fontWeight = FontWeight.Bold)
            }

            val maxChar = 150
            if(asesoria.descripcion.length > maxChar){
                Text(text = asesoria.descripcion.substring(0,maxChar).plus("...") )
            } else {
                Text(text = asesoria.descripcion)
            }

        }
    }
    // Mostrar el AlertDialog si el estado `showDialog` es verdadero
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
                    // Acción del botón de confirmación
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

/*
@Preview
@Composable
fun PreviewAsesoriaScreen() {
    val date = LocalDate(2024, 2, 15)
    val time = LocalTime(16, 48)
    val dateTime = LocalDateTime(date, time)

    val datosFalsos = listOf(
        Asesoria(1, dateTime,dateTime,"Titulo","Descripcion",true,1),
        Asesoria(1, dateTime,dateTime,"Titulo","Descripcion",true,1),
        Asesoria(1, dateTime,dateTime,"Titulo","Descripcion",true,1)
    )

    AsesoriaScreen(data = datosFalsos)
}
*/