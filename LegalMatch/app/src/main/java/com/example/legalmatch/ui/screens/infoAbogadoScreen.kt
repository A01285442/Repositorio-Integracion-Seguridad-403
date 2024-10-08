package com.example.legalmatch.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.graphics.Shape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.legalmatch.ui.components.CustomBottomBar
import com.example.legalmatch.ui.components.CustomTopBar
import com.example.legalmatch.ui.theme.GhostWhite
import androidx.compose.foundation.rememberScrollState
import com.example.legalmatch.ui.components.CustomBottomBarClientes
import com.example.legalmatch.ui.theme.AzulTec

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoAbogadoScreen(navController: NavController) {
    // Estado para manejar los datos del formulario

    var descriptionC_investigacion by remember { mutableStateOf(TextFieldValue("")) }
    var descriptionC_judicial by remember { mutableStateOf(TextFieldValue("")) }
    var descriptionCasoCerrado by remember { mutableStateOf(TextFieldValue("")) }
    var descriptionCreated_at by remember { mutableStateOf(TextFieldValue("")) }
    var description_Delito by remember { mutableStateOf(TextFieldValue("")) }
    var description by remember { mutableStateOf(TextFieldValue("")) }
    var description_Direccion by remember { mutableStateOf(TextFieldValue("")) }
    var description_Drive by remember { mutableStateOf(TextFieldValue("")) }
    var description_Fiscalia by remember { mutableStateOf(TextFieldValue("")) }
    var description_Id by remember { mutableStateOf(TextFieldValue("")) }
    var description_IdAbogado by remember { mutableStateOf(TextFieldValue("")) }
    var description_IdCliente by remember { mutableStateOf(TextFieldValue("")) }
    var description_NUC by remember { mutableStateOf(TextFieldValue("")) }
    var description_Password by remember { mutableStateOf(TextFieldValue("")) }
    var description_Titulo by remember { mutableStateOf(TextFieldValue("")) }
    var description_UnidadInv by remember { mutableStateOf(TextFieldValue("")) }



    Scaffold(
        topBar = {
            CustomTopBar(title = "Agendar Asesoría", navIcon = false, actIcon = false)
        },
        bottomBar = {
            CustomBottomBarClientes(navController = navController) // Barra inferior
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


            // Campo para la descripción del delito
            Text(text = "c_investigacion:", fontSize = 18.sp)
            BasicTextField(
                value = descriptionC_investigacion,
                onValueChange = { descriptionC_investigacion = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(12.dp))
                    .padding(8.dp)
            )

            Text(text = "c_judicial", fontSize = 18.sp)
            BasicTextField(
                value = descriptionC_judicial,
                onValueChange = {descriptionC_judicial = it},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(12.dp))
                    .padding(8.dp)
            )

            Text(text = "caso_cerrado", fontSize = 18.sp)
            BasicTextField(
                value = descriptionCasoCerrado,
                onValueChange = {descriptionCasoCerrado = it},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(12.dp))
                    .padding(8.dp)
            )

            Text(text = "created_at", fontSize = 18.sp)
            BasicTextField(
                value = descriptionCreated_at,
                onValueChange = {descriptionCreated_at = it},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(12.dp))
                    .padding(8.dp)
            )

            Text(text = "delito", fontSize = 18.sp)
            BasicTextField(
                value = description_Delito,
                onValueChange = {description_Delito = it},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(12.dp))
                    .padding(8.dp)
            )

            Text(text = "descripcion", fontSize = 18.sp)
            BasicTextField(
                value = description,
                onValueChange = {description = it},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(12.dp))
                    .padding(8.dp)
            )

            Text(text = "direccion_ui", fontSize = 18.sp)
            BasicTextField(
                value = description_Direccion,
                onValueChange = {description_Direccion = it},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(12.dp))
                    .padding(8.dp)
            )

            Text(text = "drive_link", fontSize = 18.sp)
            BasicTextField(
                value = description_Drive,
                onValueChange = {description_Drive = it},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(12.dp))
                    .padding(8.dp)
            )

            Text(text = "fiscalia_virtual", fontSize = 18.sp)
            BasicTextField(
                value = description_Fiscalia,
                onValueChange = {description_Fiscalia = it},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(12.dp))
                    .padding(8.dp)
            )

            Text(text = "id", fontSize = 18.sp)
            BasicTextField(
                value = description_Id,
                onValueChange = {description_Id = it},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(12.dp))
                    .padding(8.dp)
            )

            Text(text = "id_abogado", fontSize = 18.sp)
            BasicTextField(
                value = description_IdAbogado,
                onValueChange = {description_IdAbogado = it},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(12.dp))
                    .padding(8.dp)
            )

            Text(text = "id_cliente", fontSize = 18.sp)
            BasicTextField(
                value = description_IdCliente,
                onValueChange = {description_IdCliente = it},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(12.dp))
                    .padding(8.dp)
            )

            Text(text = "nuc", fontSize = 18.sp)
            BasicTextField(
                value = description_NUC,
                onValueChange = {description_NUC = it},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(12.dp))
                    .padding(8.dp)
            )

            Text(text = "password_fv", fontSize = 18.sp)
            BasicTextField(
                value = description_Password,
                onValueChange = {description_Password = it},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(12.dp))
                    .padding(8.dp)
            )

            Text(text = "titulo", fontSize = 18.sp)
            BasicTextField(
                value = description_Titulo,
                onValueChange = {description_Titulo = it},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(12.dp))
                    .padding(8.dp)
            )

            Text(text = "unidad_investigacion", fontSize = 18.sp)
            BasicTextField(
                value = description_UnidadInv,
                onValueChange = {description_UnidadInv = it},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(12.dp))
                    .padding(8.dp)
            )

            // Botón para agendar asesoría
            Button(
                onClick = { /* Acciones para agendar asesoría */ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = AzulTec,
                    contentColor = Color.White),

                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .padding(vertical = 4.dp),
                shape = RoundedCornerShape(18.dp)
            ) {
                Text(text = "Agendar Asesoría", fontSize = 18.sp)
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun InfoAbogadoScreenPreview() {
    InfoAbogadoScreen(navController = rememberNavController())
}
