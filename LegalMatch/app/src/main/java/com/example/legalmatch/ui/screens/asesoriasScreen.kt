package com.example.legalmatch.ui.screens

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.navigation.NavController
import com.example.app.navigation.Routes
import com.example.legalmatch.ui.components.CustomBottomBar
import com.example.legalmatch.ui.components.CustomTopBar
import com.example.legalmatch.ui.components.ItemCard
import java.time.LocalDateTime

private const val TAG = "MainActivity"

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("SimpleDateFormat")
@Composable
fun AsesoriaScreen(
    navController: NavController,
    asesoriaViewModel: AsesoriaViewModel,
    loginViewModel: LoginViewModel
) {

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

            if (asesoriaList.isEmpty()){

                Text(
                    text = "No hay asesorías pendientes",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(16.dp),
                    )

                return@Scaffold
            }

            LazyColumn(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
            ) {
                val now = LocalDateTime.now()


                val mesesEspanol = listOf("enero", "febrero", "marzo", "abril", "mayo", "junio", "julio", "agosto", "septiembre", "octubre", "noviembre", "diciembre")
                var subtituloAyerMostrado = false
                var subtituloHoyMostrado = false
                var subtituloMananaMostrado = false
                var subtituloOtroDiaMostrado = false



                items(asesoriaList) { asesoria ->
                    if(asesoria.fecha_asesoria == null){
                        return@items
                    }
                    if(asesoria.fecha_asesoria == kotlinx.datetime.LocalDateTime(1, 1, 1, 1, 1, 1)){
                        return@items
                    }
                    else if (asesoria.fecha_asesoria.dayOfYear == now.dayOfYear - 1) {
                        if (!subtituloAyerMostrado) {
                            Subtitulo("Ayer")
                            subtituloAyerMostrado = true
                        }
                        ItemCard(
                            title = "${asesoria.fecha_asesoria.hour}:00 • ${asesoria.titulo}",
                            description = asesoria.descripcion,
                            onClick = { navController.navigate(Routes.AsesoriaDetalle.createRoute(asesoria.id)) } // Al hacer click, seleccionamos la asesoría
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
                            onClick = { navController.navigate(Routes.AsesoriaDetalle.createRoute(asesoria.id)) }
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
                            onClick = { navController.navigate(Routes.AsesoriaDetalle.createRoute(asesoria.id)) }
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
                            onClick = { navController.navigate(Routes.AsesoriaDetalle.createRoute(asesoria.id)) }
                        )
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
        color = MaterialTheme.colorScheme.primary,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(12.dp)
    )
}
