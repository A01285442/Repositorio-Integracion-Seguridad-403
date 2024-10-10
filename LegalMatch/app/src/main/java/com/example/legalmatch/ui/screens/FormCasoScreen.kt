package com.example.legalmatch.ui.screens

import android.util.Log
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
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.runtime.mutableStateOf
import com.example.legalmatch.data.api.models.SendUsuario
import kotlinx.coroutines.delay
import kotlinx.datetime.LocalDateTime

@Composable
fun FormCasoScreen(navController: NavController, casosViewModel: CasosViewModel) {

    var titulo by remember { mutableStateOf(TextFieldValue("")) }
    var descripcion by remember { mutableStateOf(TextFieldValue("")) }
    var cInvestigacion by remember { mutableStateOf(TextFieldValue("")) }
    var cJudicial by remember { mutableStateOf(TextFieldValue("")) }
    var delito by remember { mutableStateOf(TextFieldValue("")) }
    var direccion by remember { mutableStateOf(TextFieldValue("")) }
    var drive by remember { mutableStateOf(TextFieldValue("")) }
    var fiscalia by remember { mutableStateOf(TextFieldValue("")) }
    var nuc by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }
    var unidadInv by remember { mutableStateOf(TextFieldValue("")) }
    // Client Info
    var nombre by remember { mutableStateOf(TextFieldValue("")) }
    var correo by remember { mutableStateOf(TextFieldValue("")) }
    var sexo by remember { mutableStateOf(TextFieldValue("")) }

    // Errores
    var errorMessage1 by remember {mutableStateOf("")}
    var errorMessage2 by remember {mutableStateOf("")}
    var errorMessage3 by remember {mutableStateOf("")}
    var errorMessage4 by remember {mutableStateOf("")}


    val casoAMandar = SendCaso(
        titulo = titulo.text,
        descripcion = descripcion.text,
        c_investigacion = cInvestigacion.text,
        c_judicial = cJudicial.text,
        delito = delito.text,
        direccion_ui = direccion.text,
        drive_link = drive.text,
        fiscalia_virtual = fiscalia.text,
        nuc = nuc.text,
        password_fv = password.text,
        unidad_investigacion = unidadInv.text,
        id_cliente = 1,
        id_abogado = 1,
        caso_cerrado = false,
    )
    val usuarioAMandar = SendUsuario(
        nombre = nombre.text,
        contraseña = "LEGALMATCH",
        correo = correo.text,
        fecha_nacimiento = LocalDateTime(1,1,1,1,1,1),
        matricula = "",
        rol = "cliente",
        sexo = sexo.text
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

            Text("Información del Caso", style = MaterialTheme.typography.titleLarge)

            InputField(
                label = "Título:",
                value = titulo,
                onValueChange = { titulo = it }
            )
            InputField(
                label = "Descripción del caso:",
                value = descripcion,
                onValueChange = { descripcion = it },
                singleLine = false,
                height = 100
            )

            if (errorMessage2.isNotBlank()){ Text(errorMessage2, color = Color.Red) }

            InputField(
                label = "Carpeta de investigación:",
                value = cInvestigacion,
                onValueChange = { cInvestigacion = it }
            )
            InputField(
                label = "Carpeta judicial:",
                value = cJudicial,
                onValueChange = { cJudicial = it }
            )
            InputField(
                label = "Delito:",
                value = delito,
                onValueChange = { delito = it }
            )
            InputField(
                label = "Dirección de la Unidad de Investigación: (Maps URL):",
                value = direccion,
                onValueChange = { direccion = it }
            )
            if (errorMessage3.isNotBlank()){ Text(errorMessage3, color = Color.Red) }

            InputField(
                label = "Carpeta Google Drive: (url):",
                value = drive,
                onValueChange = { drive = it }
            )

            if (errorMessage4.isNotBlank()){ Text(errorMessage4, color = Color.Red) }

            InputField(
                label = "Fiscalía Virtual:",
                value = fiscalia,
                onValueChange = { fiscalia = it }
            )
            InputField(
                label = "Número Único de Causa:",
                value = nuc,
                onValueChange = { nuc = it }
            )
            InputField(
                label = "Contraseña de la Fiscalía Virtual:",
                value = password,
                onValueChange = { password = it }
            )
            InputField(
                label = "Unidad de Investigación:",
                value = unidadInv,
                onValueChange = { unidadInv = it }
            )

            Text("Información del Cliente", style = MaterialTheme.typography.titleLarge)

            InputField(
                label = "Nombre del cliente:",
                value = nombre,
                onValueChange = { nombre = it }
            )
            InputField(
                label = "Correo del cliente:",
                value = correo,
                onValueChange = { correo = it }
            )

            if (errorMessage1.isNotBlank()){ Text(errorMessage1, color = Color.Red) }

            Text(text = "Sexo del cliente:", style = MaterialTheme.typography.bodyMedium)
            val sexo: List<String> = listOf("Hombre", "Mujer")
            var sexoSeleccionado by remember { mutableStateOf("") }
            DynamicSelectTextField(
                selectedValue = sexoSeleccionado,
                options = sexo,
                label = sexoSeleccionado,
                onValueChangedEvent = { sexoSeleccionado = it},
                modifier = Modifier

            )


            Text("Se creará una cuenta para el cliente automáticamente. La contraseña será 'LEGALMATCH",
                style = MaterialTheme.typography.bodySmall)


            // Botón para agendar asesoría
            Button(
                onClick = {
                    errorMessage1 = if(nombre.text.isBlank() || correo.text.isBlank()){
                        "Nombre y correo son obligatorios" } else { "" }
                    errorMessage2 = if(titulo.text.isBlank() || descripcion.text.isBlank()){
                        "Titulo y descripcion son obligatorios" } else { "" }
                    errorMessage3 = if(!direccion.text.startsWith("https://") or direccion.text.isBlank()){
                        "Direccion no valida" } else { "" }
                    errorMessage4 = if(!drive.text.startsWith("https://") or drive.text.isBlank()){
                        "Drive no valido" } else { "" }

                    if(errorMessage1.isBlank() and errorMessage2.isBlank() and errorMessage3.isBlank() and errorMessage4.isBlank()){
                        Log.d("MainActivity", "Se ha creado el caso")
                        casosViewModel.createCaso(casoAMandar)

                        navController.navigate(Routes.Casos.route)

                    } else {return@Button}
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

        }
    }
}

@Composable
fun InputField(
    label: String,
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    singleLine: Boolean = true,
    modifier: Modifier = Modifier,
    height: Int = 15
) {
    Column {
        Text(text = label, style = MaterialTheme.typography.bodyMedium)
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = singleLine,
            modifier = modifier
                .fillMaxWidth()
                .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(4.dp))
                .padding(8.dp)
                .height(height.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DynamicSelectTextField(
    selectedValue: String,
    options: List<String>,
    label: String,
    onValueChangedEvent: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        //modifier = modifier.height(15.dp)
    ) {
        OutlinedTextField(
            readOnly = true,
            value = selectedValue,
            onValueChange = {},
            label = { Text(text = label) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            colors = OutlinedTextFieldDefaults.colors(),
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )

        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach { option: String ->
                DropdownMenuItem(
                    text = { Text(text = option) },
                    onClick = {
                        expanded = false
                        onValueChangedEvent(option)
                    }
                )
            }
        }
    }
}