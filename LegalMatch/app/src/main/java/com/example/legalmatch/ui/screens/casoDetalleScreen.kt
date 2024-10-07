package com.example.legalmatch.ui.screens

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.app.navigation.Routes
import com.example.legalmatch.data.api.models.Caso
import com.example.legalmatch.ui.components.CustomBottomBar
import com.example.legalmatch.ui.components.CustomTopBar


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CasoDetalleScreen(
    navController: NavController,
    viewModel: CasosViewModel,
    casoId: Int,
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState() // Estado del scroll

    val caso = viewModel.getCasoInfo(casoId)
    if (caso == null){
        Text("Caso no encontrado. Favor de reiniciar la aplicación.")
        return
    }

    Scaffold(
        topBar = {
            CustomTopBar(title = "Caso #${caso.id}", navIcon = true, actIcon = false, navController, Routes.Casos.route)
        },
        bottomBar = { CustomBottomBar(navController=navController) }
    ) { InnerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding((InnerPadding))
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Información básica
            Text(
                text = caso.titulo,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Left
            )
            Text(
                text =
                    "NUC: ${caso.nuc}" +
                    "\nCarpeta Judicial: ${caso.c_judicial}" +
                    "\nCarpeta de Investigación: ${caso.c_investigacion}" +
                    "\nAcceso a Fiscalía Virtual: ${caso.fiscalia_virtual}" +
                    "\nContraseña Fiscalía Virtual: ${caso.password_fv}" +
                    "\nFiscal Titular: ${caso.id_abogado}" +
                    "\nUnidad de investigación: ${caso.unidad_investigacion}" +
                    "\nDirección de la Unidad Inv: ${caso.direccion_ui}",

                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Left
            )

            // Descripción del caso
            Text(
                text = "Descripción",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Left
            )
            Text(
                text = caso.descripcion,
                style = MaterialTheme.typography.titleSmall,
                textAlign = TextAlign.Left
            )


            // Informacioón del cliente
            Text(
                text = "Información del cliente",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Left
            )
            Text(
                text = "Nombre completo: Maria Bolaños Amargo" +
                        "\nGénero: female" +
                        "\nNacimiento: Sep 12, 2000" +
                        "\nCelularr: 47-1234-1234",
                style = MaterialTheme.typography.titleSmall,
                textAlign = TextAlign.Left
            )

            // Botones
            Button(
                onClick = {
                    val url = caso?.drive_link//Agregar funcionalidad de cambiar el link para los abogados.
                    val i = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    ContextCompat.startActivity(context, i, null)
                },
                modifier = Modifier
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Blue,
                    contentColor = Color.White
                )
            ) {
                Text(text = "Añadir archivos")
            }

            Row {
                Button(
                    onClick = {
                        viewModel.cerrarCaso(casoId)
                        navController.navigateUp()
                    },
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Gray,
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "Editar Información")
                }
                Button(
                    onClick = {
                        viewModel.cerrarCaso(casoId)
                        navController.navigateUp()
                    },
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red,
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "Cerrar Caso")
                }
            }




        }
    }
}

@Composable
fun InfoCaso(caso: Caso){
    Text("Número Único de Causa: ")
    Text("Carpeta Judicial: ")
    Text("Número Único de : ")
    Text("Número Único de Causa: ")
    Text("Número Único de Causa: ")
    Text("Número Único de Causa: ")
    Text("Número Único de Causa: ")
    Text("Número Único de Causa: ")
}

@Composable
fun RowInformation(tipo: String, texto: String){
    Row {
        Text(tipo, style = MaterialTheme.typography.bodySmall, textDecoration = TextDecoration.Underline)
        Text(texto, style = MaterialTheme.typography.bodySmall)
    }
}