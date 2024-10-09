package com.example.legalmatch.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.app.navigation.Routes
import com.example.legalmatch.data.api.models.SendCaso
import com.example.legalmatch.ui.components.CustomBottomBar
import com.example.legalmatch.ui.components.CustomTopBar
import com.example.legalmatch.ui.theme.AzulTec
import com.example.legalmatch.ui.theme.GhostWhite
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext




@Composable
fun FormCasoScreen(navController: NavController, casosViewModel: CasosViewModel) {

    // Estado para manejar los datos del formulario

    var descriptionC_investigacion by remember { mutableStateOf(TextFieldValue("")) }
    var descriptionC_judicial by remember { mutableStateOf(TextFieldValue("")) }
    var description_Delito by remember { mutableStateOf(TextFieldValue("")) }
    var description by remember { mutableStateOf(TextFieldValue("")) }
    var description_Direccion by remember { mutableStateOf(TextFieldValue("")) }
    var description_Drive by remember { mutableStateOf(TextFieldValue("")) }
    var description_Fiscalia by remember { mutableStateOf(TextFieldValue("")) }
    var description_IdAbogado by remember { mutableStateOf(TextFieldValue("")) }
    var description_IdCliente by remember { mutableStateOf(TextFieldValue("")) }
    var description_NUC by remember { mutableStateOf(TextFieldValue("")) }
    var description_Password by remember { mutableStateOf(TextFieldValue("")) }
    var description_Titulo by remember { mutableStateOf(TextFieldValue("")) }
    var description_UnidadInv by remember { mutableStateOf(TextFieldValue("")) }

    //casosViewModel.checkIfPageExists(description_Direccion.text)
    val result = casosViewModel.state.pageExists


    val casoAMandar = SendCaso(
        c_investigacion = descriptionC_investigacion.text,
        c_judicial = descriptionC_judicial.text,
        delito = description_Delito.text,
        descripcion = description.text,
        direccion_ui = description_Direccion.text,
        drive_link = description_Drive.text,
        fiscalia_virtual = description_Fiscalia.text,
        id_abogado = description_IdAbogado.text.toIntOrNull() ?: 0,
        id_cliente = description_IdCliente.text.toIntOrNull() ?: 0,
        nuc = description_NUC.text,
        password_fv = description_Password.text,
        titulo = description_Titulo.text,
        unidad_investigacion = descriptionC_investigacion.text,
        caso_cerrado = false,
    )


    Scaffold(
        topBar = {
            CustomTopBar(
                title = "Crear Caso",
                navIcon = true,
                actIcon = false,
                navController = navController,
                rutaActButton = Routes.FormCaso.route
            )
        },
        bottomBar = {
            CustomBottomBar(navController = navController) // Barra inferior
        }
    ) { paddingValues ->
        // Habilitar desplazamiento
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(color = GhostWhite)
                .verticalScroll(rememberScrollState()) // Habilitar scroll vertical
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Text("Valores obligatorios", style = MaterialTheme.typography.headlineMedium)

            Text(text = "Título:", style = MaterialTheme.typography.bodyMedium)
            BasicTextField(
                value = description_Titulo,
                onValueChange = {description_Titulo = it},
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(4.dp))
                    .padding(8.dp)
            )

            Text(text = "Descripción del caso:", style = MaterialTheme.typography.bodyMedium)
            BasicTextField(
                value = description,
                onValueChange = {description = it},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(4.dp))
                    .padding(8.dp)
            )

            // Campo para la descripción del delito
            Text(text = "Carpeta de investigación:", style = MaterialTheme.typography.bodyMedium)
            BasicTextField(
                value = descriptionC_investigacion,
                onValueChange = { descriptionC_investigacion = it },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(4.dp))
                    .padding(8.dp)
            )

            Text("Valores opcionales", style = MaterialTheme.typography.headlineMedium)

            Text(text = "Carpeta judicial:", style = MaterialTheme.typography.bodyMedium)
            BasicTextField(
                value = descriptionC_judicial,
                onValueChange = {descriptionC_judicial = it},
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(4.dp))
                    .padding(8.dp)
            )

            Text(text = "Delito:", style = MaterialTheme.typography.bodyMedium)
            BasicTextField(
                value = description_Delito,
                onValueChange = {description_Delito = it},
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(4.dp))
                    .padding(8.dp)
            )


            Text(text = "Dirección de la Unidad de Investigación: (Maps URL)", fontSize = 18.sp)
            BasicTextField(
                value = description_Direccion,
                onValueChange = {
                    description_Direccion = it
                                },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(4.dp))
                    .padding(8.dp)
            )

            Text(text = "Carpeta Google Drive: (url)", style = MaterialTheme.typography.bodyMedium)
            BasicTextField(
                value = description_Drive,
                onValueChange = {description_Drive = it},
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(4.dp))
                    .padding(8.dp)
            )

            Text(text = "Fiscalía Virtual", style = MaterialTheme.typography.bodyMedium)
            BasicTextField(
                value = description_Fiscalia,
                onValueChange = {description_Fiscalia = it},
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(4.dp))
                    .padding(8.dp)
            )

            Text(text = "Fiscal Titular:", style = MaterialTheme.typography.bodyMedium)
            BasicTextField(
                value = description_IdAbogado,
                onValueChange = {description_IdAbogado = it},
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(4.dp))
                    .padding(8.dp)
            )

            Text(text = "Número Único de Causa:", style = MaterialTheme.typography.bodyMedium)
            BasicTextField(
                value = description_NUC,
                onValueChange = {description_NUC = it},
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(4.dp))
                    .padding(8.dp)
            )

            Text(text = "Contraseña de la Fiscalía Virtual:", style = MaterialTheme.typography.bodyMedium)
            BasicTextField(
                value = description_Password,
                onValueChange = {description_Password = it},
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(4.dp))
                    .padding(8.dp)
            )



            Text(text = "Unidad de Investigación:", style = MaterialTheme.typography.bodyMedium)
            BasicTextField(
                value = description_UnidadInv,
                onValueChange = {description_UnidadInv = it},
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(4.dp))
                    .padding(8.dp)
            )

            Text("Información del Cliente", style = MaterialTheme.typography.headlineMedium)

            Text(text = "Nombre del cliente:", style = MaterialTheme.typography.bodyMedium)
            BasicTextField(
                value = description_UnidadInv,
                onValueChange = {description_UnidadInv = it},
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(4.dp))
                    .padding(8.dp)
            )

            // Botón para agendar asesoría
            Button(
                onClick = {
                    casosViewModel.checkIfPageExists(description_Direccion.text)
                    //casosViewModel.createCaso(casoAMandar)
                    //navController.navigate(Routes.Casos.route)
                          },
                colors = ButtonDefaults.buttonColors(
                    containerColor = AzulTec,
                    contentColor = Color.White),

                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .padding(vertical = 4.dp),
                shape = RoundedCornerShape(18.dp)
            ) {
                Text(text = "Crear Caso", fontSize = 18.sp)
            }





            if(result){
                Text("URL MAPS valido")
            } else {
                Text("URL MAPS no valido")
            }
        }
    }
}