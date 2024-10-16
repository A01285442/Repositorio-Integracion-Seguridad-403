package com.example.legalmatch.ui.screens

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.util.Log
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavController
import com.example.app.navigation.Routes
import com.example.legalmatch.ui.components.CustomBottomBar
import com.example.legalmatch.ui.components.CustomBottomBarClientes
import com.example.legalmatch.ui.components.CustomTopBar
import com.example.legalmatch.ui.components.SpacedInformation
import com.example.legalmatch.viewmodel.UsuariosViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "StateFlowValueCalledInComposition",
    "SuspiciousIndentation"
)
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
    usuariosVM.getAbogadoInfo(caso.id_abogado)
    val fiscalTitular = usuariosVM.state.infoAbogado
    val rolUsuario = loginVM.loginState.value.userClient?.rol

    Scaffold(
        topBar = {
            CustomTopBar(title = "Caso #${caso.id}", navIcon = true, actIcon = false, navController = navController)
        },
        bottomBar = {
            if(rolUsuario == "cliente") CustomBottomBarClientes(navController = navController)
            else CustomBottomBar(navController=navController)
        }
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
            SpacedInformation("Delito:", caso.delito, style = MaterialTheme.typography.bodySmall)
            SpacedInformation("NUC:", caso.nuc, style = MaterialTheme.typography.bodySmall)
            SpacedInformation("Carpeta Judicial:", caso.c_judicial, style = MaterialTheme.typography.bodySmall)
            SpacedInformation("Carpeta de Investigación:", caso.c_investigacion, style = MaterialTheme.typography.bodySmall)
            SpacedInformation("Contraseña Fiscalía Virtual:", caso.password_fv, style = MaterialTheme.typography.bodySmall)
            SpacedInformation("Fiscal Titular:", fiscalTitular.nombre, style = MaterialTheme.typography.bodySmall)
            SpacedInformation("Unidad de Investigación:", caso.unidad_investigacion, style = MaterialTheme.typography.bodySmall)
            SpacedInformation("Dirección de la Unidad de Investigación:", caso.direccion_ui, style = MaterialTheme.typography.bodySmall)

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
            SpacedInformation("Nombre:", cliente.nombre, style = MaterialTheme.typography.bodySmall)
            SpacedInformation("Sexo:", cliente.sexo, style = MaterialTheme.typography.bodySmall)
            SpacedInformation("Nacimiento", cliente.fecha_nacimiento.date.toString(), style = MaterialTheme.typography.bodySmall)
            SpacedInformation("Correo:", cliente.correo, style = MaterialTheme.typography.bodySmall)

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(12.dp))

            // Añadir archivo
            if(loginVM.loginState.value.userClient?.rol != "cliente"){
                Button(
                    onClick = {
                        val url = caso.drive_link//Agregar funcionalidad de cambiar el link para los abogados.
                        val i = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        startActivity(context, i, null)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Text(text = "Abrir Carpeta Drive")
                }
                Button(
                    onClick = {
                        val url = caso.fiscalia_virtual //Agregar funcionalidad de cambiar el link para los abogados.
                        val i = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        startActivity(context, i, null)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Text(text = "Abrir Fiscalía Virtual")
                }
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
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
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
                        containerColor = MaterialTheme.colorScheme.error,
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
                            colors = ButtonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = Color.White,
                                disabledContentColor = Color.Gray,
                                disabledContainerColor = Color.Gray
                            ),
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
