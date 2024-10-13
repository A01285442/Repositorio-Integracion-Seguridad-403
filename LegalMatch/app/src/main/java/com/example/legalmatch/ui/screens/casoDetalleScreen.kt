package com.example.legalmatch.ui.screens

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavController
import com.example.app.navigation.Routes
import com.example.legalmatch.ui.components.CustomBottomBar
import com.example.legalmatch.ui.components.CustomTopBar
import com.example.legalmatch.ui.theme.AzulTec
import com.example.legalmatch.viewmodel.UsuariosViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
@Composable
fun CasoDetalleScreen(
    navController: NavController,
    casosVM: CasosViewModel,
    casoId: Int,
    usuariosVM: UsuariosViewModel,
    loginVM: LoginViewModel
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState() // Estado del scroll

    var showDialog by remember { mutableStateOf(false) }

    val caso = casosVM.getCasoInfo(casoId)
    if (caso == null){
        Text("Caso no encontrado. Favor de reiniciar la aplicación.")
        return
    }
    usuariosVM.getClientInfo(caso.id_cliente)
    val cliente = usuariosVM.state.infoCliente

    Scaffold(
        topBar = {
            CustomTopBar(title = "Caso #${caso.id}", navIcon = true, actIcon = false, navController = navController)
        },
        bottomBar = { CustomBottomBar(navController=navController) }
    ) { InnerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding((InnerPadding))
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .verticalScroll(scrollState),
        ) {
            // Información básica
            Text(
                text = caso.titulo,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Left,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Text(
                text =
                    "Delito: ${caso.delito}" +
                    "\nNUC: ${caso.nuc}" +
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
                textAlign = TextAlign.Left,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Text(
                text = caso.descripcion,
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
            Text(
                text = "Nombre completo: ${cliente.nombre}" +
                        "\nSexo: ${cliente.sexo}" +
                        "\nNacimiento: ${cliente.fecha_nacimiento.date}" +
                        "\nCorreo: ${cliente.correo}",
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Left
            )

            // Añadir archivo
            Button(
                onClick = {
                    val url = caso.drive_link//Agregar funcionalidad de cambiar el link para los abogados.
                    val i = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    startActivity(context, i, null)
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Blue,
                    contentColor = Color.White
                )
            ) {
                Text(text = "Añadir archivos")
            }


            // Ver en google maps
            Button(
                onClick = {
                    val url = caso.direccion_ui//Agregar funcionalidad de cambiar el link para los abogados.
                    val i = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    startActivity(context, i, null)
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Blue,
                    contentColor = Color.White
                )
            ) {
                Text(text = "Ver en Google Maps")
            }


            if(loginVM.loginState.value.userClient?.rol == "abogado"){
                // Editar información
                Button(
                    onClick = {
                        navController.navigate(Routes.EditCaso.createRoute(casoId))
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Gray,
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "Editar Información")
                }
                Button(
                    onClick = { Log.d("MainActivity","xd")
                        navController.navigate(Routes.EstudiantesInvolucrados.createRoute(casoId)) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Gray,
                        contentColor = Color.White
                    )
                ) { Text(text = "Estudiantes Involucrados") }

                // Cerrar caso
                Button(
                    onClick = {
                        showDialog = true
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red,
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "Concluir Caso")
                }

            }

            // Dialogo de confirmación
            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false }, // Cerrar el diálogo si el usuario cancela
                    title = { Text("Confirmación") },
                    text = { Text("¿Estás seguro de que deseas concluir el caso? Si lo haces, no podrás volver a abrirlo.") },
                    confirmButton = {
                        Button(
                            colors = ButtonColors(containerColor = Color.Red, contentColor = Color.White, disabledContentColor = Color.Gray, disabledContainerColor = Color.Gray),
                            onClick = {
                                casosVM.cerrarCaso(casoId)
                                navController.navigateUp()
                                showDialog = false // Cerrar el diálogo
                            }
                        ) {
                            Text("Sí, cerrar")
                        }
                    },
                    dismissButton = {
                        Button(
                            colors = ButtonColors(containerColor = AzulTec, contentColor = Color.White, disabledContentColor = Color.Gray, disabledContainerColor = Color.Gray),
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
