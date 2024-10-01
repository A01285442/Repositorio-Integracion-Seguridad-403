package com.example.legalmatch.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.app.navigation.Routes
import com.example.legalmatch.ui.components.CustomBottomBar
import com.example.legalmatch.ui.components.CustomTopBar


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CasoDetalleScreen(
    navController: NavController,
    viewModel: CasosViewModel,
    casoId: Int?
) {
    var NcasoId = 0
    if (casoId!=null){
        NcasoId = casoId
    }

    val caso = viewModel.getCasoInfo(NcasoId)

    Scaffold(
        topBar = { CustomTopBar(title = "Caso #${caso.id}", navIcon = true, actIcon = false, navController, Routes.Casos.route) },
        bottomBar = { CustomBottomBar(navController=navController) }
    ) { InnerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding((InnerPadding))
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Case information section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "El Estado vs. Soluciones Tech S.A.",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = caso.delito + "\nCarpeta de Investigación: " + caso.c_investigacion + "\nEn estapa de juicio Oral",
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "En mayo de 2023, la empresa de tecnología Soluciones Tech S.A. fue acusada de violar las leyes de protección de datos personales tras la filtración masiva de información sensible de más de 500,000 usuarios. Según la denuncia, la filtración se debió a una negligencia en la implementación de medidas de seguridad para resguardar los datos de sus clientes. La información comprometida incluye nombres, direcciones, números de identificación y datos bancarios.\n" +
                            "\n" +
                            "El Estado presentó una demanda alegando que Soluciones Tech S.A. no cumplió con la normativa de seguridad cibernética estipulada en la Ley de Protección de Datos Personales. La empresa, por su parte, argumenta que fue víctima de un ataque externo sofisticado y que su infraestructura cumplía con los estándares del sector.\n" +
                            "\n" +
                            "El caso plantea preguntas clave sobre la responsabilidad corporativa en la protección de datos, el alcance de las medidas de seguridad requeridas por ley y las posibles consecuencias para las víctimas de la filtración.",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Justify
                )

                Text(
                    text = "Delito: asesinato\nFiscalía: 12345\nCarpeta: 12345\nEstatus: Iniciado (1,2,3)",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                Text(
                    text = "Información del cliente",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Nombre completo: Maria Bolaños Amargo" +
                            "\nGénero: female" +
                            "\nNacimiento: Sep 12, 2000" +
                            "\nCelularr: 47-1234-1234",
                    style = MaterialTheme.typography.bodyMedium
                )

                // PDF file section
                FileListSection()
            }

            // Button to add files
            Button(
                onClick = { /* Acción para añadir archivos */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Blue,
                    contentColor = Color.White
                )
            ) {
                Text(text = "Añadir archivos")
            }
        }
    }
}

@Composable
fun FileListSection() {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        FileItem(fileName = "Archivo número 1")
        FileItem(fileName = "Archivo número 1")
        FileItem(fileName = "Archivo número 1")
    }
}

@Composable
fun FileItem(fileName: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Icon(
            imageVector = Icons.Default.FavoriteBorder,
            contentDescription = "PDF File",
            tint = Color.Red,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = fileName,
            style = MaterialTheme.typography.bodySmall
        )
    }
}
