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
import com.example.legalmatch.data.api.models.Usuario
import com.example.legalmatch.ui.components.CustomBottomBar
import com.example.legalmatch.ui.components.CustomTopBar
import com.example.legalmatch.viewmodel.UsuariosViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CasoDetalleScreen(
    navController: NavController,
<<<<<<< Updated upstream
    casosVM: CasosViewModel,
=======
    casoViewModel: CasosViewModel,
    usuariosViewModel: UsuariosViewModel,
>>>>>>> Stashed changes
    casoId: Int,
    usuariosVM: UsuariosViewModel
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState() // Estado del scroll

<<<<<<< Updated upstream
    val caso = casosVM.getCasoInfo(casoId)
=======
    val caso = casoViewModel.getCasoInfo(casoId)
>>>>>>> Stashed changes
    if (caso == null){
        Text("Caso no encontrado. Favor de reiniciar la aplicación.")
        return
    }
<<<<<<< Updated upstream
    usuariosVM.getClientInfo(caso.id_cliente)
    val cliente = usuariosVM.state.infoCliente
=======
    usuariosViewModel.getUsuario(caso.id_cliente)
    val cliente = usuariosViewModel.state.usuarioEspecifico

>>>>>>> Stashed changes

    Scaffold(
        topBar = {
            CustomTopBar(title = "Caso #${caso.id}", navIcon = true, actIcon = false, navController = navController, rutaBackButton = Routes.Casos.route)
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
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Left,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Text(
                text =
                    "Delito: ${caso.delito}" +
<<<<<<< Updated upstream
                    "\nNUC: ${caso.nuc}" +
=======
                    "\nNum. Único de Causa: ${caso.nuc}" +
>>>>>>> Stashed changes
                    "\nCarpeta Judicial: ${caso.c_judicial}" +
                    "\nCarpeta de Investigación: ${caso.c_investigacion}" +
                    "\nFiscalía Virtual: ${caso.fiscalia_virtual}" +
                    "\nContraseña Fiscalía Virtual: ${caso.password_fv}" +
                    "\nFiscal Titular: ${caso.id_abogado}" +
                    "\nUnidad de investigación: ${caso.unidad_investigacion}" +
                    "\nDirección de la Unidad Inv: ${caso.direccion_ui}",

                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Left,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            // Descripción del caso
            Text(
                text = "Descripción",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Left,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Text(
                text = caso.descripcion,
                style = MaterialTheme.typography.bodySmall,
<<<<<<< Updated upstream
                textAlign = TextAlign.Left
=======
                textAlign = TextAlign.Left,
                modifier = Modifier.padding(vertical = 8.dp)
>>>>>>> Stashed changes
            )


            // Informacioón del cliente
            Text(
                text = "Información del cliente",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Left,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Text(
                text = "Nombre completo: ${cliente.nombre}" +
<<<<<<< Updated upstream
                        "\nSexo: ${cliente.sexo}" +
                        "\nNacimiento: ${cliente.fecha_nacimiento.date}" +
                        "\nCorreo: ${cliente.correo}",
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Left
=======
                        "\nGénero: ${cliente.sexo}" +
                        "\nNacimiento: ${cliente.fecha_nacimiento}" +
                        "\nCorreo: ${cliente.correo}",
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Left,
                modifier = Modifier.padding(vertical = 8.dp)
>>>>>>> Stashed changes
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

<<<<<<< Updated upstream

            // Ver en google maps
            Button(
                onClick = {
                    val url = caso.direccion_ui//Agregar funcionalidad de cambiar el link para los abogados.
                    val i = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    startActivity(context, i, null)
                },
                modifier = Modifier.fillMaxWidth(),
=======
            // Botones
            Button(
                onClick = {
                    val url = caso?.direccion_ui //Agregar funcionalidad de cambiar el link para los abogados.
                    val i = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    startActivity(context, i, null)
                },
                modifier = Modifier
                    .fillMaxWidth(),
>>>>>>> Stashed changes
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Blue,
                    contentColor = Color.White
                )
            ) {
                Text(text = "Ver en Google Maps")
            }
<<<<<<< Updated upstream

            // Editar información
            Button(
                onClick = {
                    casosVM.cerrarCaso(casoId)
                    navController.navigateUp()
                },
                modifier = Modifier.fillMaxWidth(),
=======
            Button(
                onClick = {
                    casoViewModel.cerrarCaso(casoId)
                    navController.navigateUp()
                },
                modifier = Modifier
                    .fillMaxWidth(),
>>>>>>> Stashed changes
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Gray,
                    contentColor = Color.White
                )
            ) {
                Text(text = "Editar Información")
            }
<<<<<<< Updated upstream

            // Cerrar caso
            Button(
                onClick = {
                    casosVM.cerrarCaso(casoId)
                    navController.navigateUp()
                },
                modifier = Modifier.fillMaxWidth(),
=======
            Button(
                onClick = {
                    casoViewModel.cerrarCaso(casoId)
                    navController.navigateUp()
                },
                modifier = Modifier
                    .fillMaxWidth(),
>>>>>>> Stashed changes
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