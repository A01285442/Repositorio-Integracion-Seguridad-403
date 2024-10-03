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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
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
    casoId: Int?,
) {
    val context = LocalContext.current
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
                    text = caso.titulo,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Left
                )
                Text(
                    text = "Delito: " + caso.delito + "\nFiscalia Virtual: " + caso.fiscalia_virtual + "\nCarpeta de Investigación: " + caso.c_investigacion + "\nCarpeta Judicial: " + caso.c_judicial,
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Left
                )
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
            }

            // Button to add files
            Button(
                onClick = { val url = "http://www.example.com" //Agregar funcionalidad de cambiar el link para los abogados.
                    val i = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    ContextCompat.startActivity(context, i, null)
                    },
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
