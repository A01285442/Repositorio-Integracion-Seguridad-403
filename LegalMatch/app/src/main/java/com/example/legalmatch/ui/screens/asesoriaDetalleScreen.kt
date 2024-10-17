package com.example.legalmatch.ui.screens

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.app.navigation.Routes
import com.example.legalmatch.ui.components.CustomBottomBar
import com.example.legalmatch.ui.components.CustomBottomBarClientes
import com.example.legalmatch.ui.components.CustomTopBar
import com.example.legalmatch.ui.components.SpacedInformation
import com.example.legalmatch.utils.toSpanish
import com.example.legalmatch.viewmodel.UsuariosViewModel


@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
@Composable
fun AsesoriaDetalleScreen(
    navController: NavController,
    asesoriaViewModel: AsesoriaViewModel,
    casoId: Int,
    usuariosVM: UsuariosViewModel,
    casoViewModel: CasosViewModel,
    loginVM: LoginViewModel
) {
    val scrollState = rememberScrollState() // Estado del scroll

    val asesoria = asesoriaViewModel.getAsesoriaInfo(casoId)
    if (asesoria == null){
        Text("Asesoría no encontrado. Favor de reiniciar la aplicación.")
        return
    }
    val rolUsuario = loginVM.loginState.value.userClient?.rol
    usuariosVM.getClientInfo(asesoria.id_cliente)
    val cliente = usuariosVM.state.infoCliente

    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CustomTopBar(title = "Asesoría #${asesoria.id}", navIcon = true, actIcon = false, navController = navController)
        },
        bottomBar = {
            if(rolUsuario == "cliente") CustomBottomBarClientes(navController = navController)
            else CustomBottomBar(navController=navController)
        }
    ) { InnerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(InnerPadding)
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .verticalScroll(scrollState),
        ) {
            // Información básica
            Text(
                text = asesoria.titulo,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Left,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            val fecha = asesoria.fecha_asesoria ?: return@Column
            SpacedInformation("Delito:", asesoria.delito,  style = MaterialTheme.typography.bodySmall)
            SpacedInformation("Fecha de la asesoría:", fecha.date.dayOfMonth.toString() + " " + toSpanish(fecha.monthNumber) + " " +fecha.date.year.toString(),  style = MaterialTheme.typography.bodySmall)
            SpacedInformation("Hora de la asesoría", fecha.hour.toString() + ":00", style = MaterialTheme.typography.bodySmall)
            if(asesoria.cliente_confirmado) SpacedInformation("¿Asistencia Confirmada?", "Si.",  style = MaterialTheme.typography.bodySmall)
            else SpacedInformation("¿Asistencia Confirmada?", "No.",  style = MaterialTheme.typography.bodySmall)
            if(asesoria.cliente_denuncio) SpacedInformation("Rol del cliente:", "Denunciante",  style = MaterialTheme.typography.bodySmall)
            else SpacedInformation("Rol del cliente:", "acusado",  style = MaterialTheme.typography.bodySmall)


            // Descripción del caso
            Text(
                text = "Descripción",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Left,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Text(
                text = asesoria.descripcion,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Justify
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Descripción Modificada",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Left,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Text(
                text = asesoria.descripcion_modificada,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Justify
            )



            // Informacioón del cliente
            Text(
                text = "Información del cliente",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Left,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            val fechaNac = cliente.fecha_nacimiento.date
            SpacedInformation("Nombre:", cliente.nombre, style = MaterialTheme.typography.bodySmall)
            SpacedInformation("Sexo:", cliente.sexo, style = MaterialTheme.typography.bodySmall)
            SpacedInformation("Nacimiento:", "${fechaNac.dayOfMonth} ${toSpanish(fechaNac.monthNumber)} ${fechaNac.year}" , style = MaterialTheme.typography.bodySmall)
            SpacedInformation("Correo:", cliente.correo, style = MaterialTheme.typography.bodySmall)

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(12.dp))

            // Aceptar Caso
            if(rolUsuario == "abogado"){
                Button(
                    onClick = {
                        asesoriaViewModel.aceptarcaso(asesoria, loginVM.loginState.value.userClient!!.id)
                        navController.navigate(Routes.Casos.route)
                        casoViewModel.fetchCasos()
                    },
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) { Text(text = "Aceptar Caso") }

                // Rechazar Caso
                Button(
                    onClick = {
                        asesoriaViewModel.reagendarAsesoria(asesoria)
                        navController.popBackStack()
                    },
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) { Text(text = "Reagendar Caso") }

                // Cerrar caso
                Button(
                    onClick = {
                        showDialog = true
                    },
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error,
                        contentColor = Color.White
                    )
                ) { Text(text = "Rechazar Caso") }
            }


            // Dialogo de confirmación
            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false }, // Cerrar el diálogo si el usuario cancela
                    title = { Text("Confirmación") },
                    text = { Text("¿Estás seguro de que deseas rechazar el caso?") },
                    confirmButton = {
                        Button(
                            colors = ButtonColors(
                                containerColor = MaterialTheme.colorScheme.error,
                                contentColor = Color.White,
                                disabledContentColor = Color.Gray,
                                disabledContainerColor = Color.Gray
                            ),
                            onClick = {
                                // Acción para cerrar el caso
                                asesoriaViewModel.cancelarCaso(asesoria)
                                navController.navigateUp() // Navegar hacia atrás
                                showDialog = false // Cerrar el diálogo
                            }
                        ) {
                            Text("Sí, rechazar")
                        }
                    },
                    dismissButton = {
                        Button(
                            colors = ButtonColors(containerColor = MaterialTheme.colorScheme.primary, contentColor = Color.White, disabledContentColor = Color.Gray, disabledContainerColor = Color.Gray),
                            onClick = { showDialog = false } // Cerrar el diálogo sin realizar ninguna acción
                        ) {
                            Text("Cancelar")
                        }
                    }
                )
            }


        }
    }
}

