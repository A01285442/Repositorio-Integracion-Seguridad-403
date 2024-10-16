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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.app.navigation.Routes
import com.example.legalmatch.data.api.models.SendCaso
import com.example.legalmatch.ui.components.CustomBottomBar
import com.example.legalmatch.ui.components.CustomDropdownMenu
import com.example.legalmatch.ui.components.CustomTopBar

data class CasoFormState(
    var titulo: TextFieldValue = TextFieldValue(""),
    var descripcion: TextFieldValue = TextFieldValue(""),
    var cInvestigacion: TextFieldValue = TextFieldValue(""),
    var cJudicial: TextFieldValue = TextFieldValue(""),
    var delito: TextFieldValue = TextFieldValue(""),
    var direccion: TextFieldValue = TextFieldValue(""),
    var drive: TextFieldValue = TextFieldValue(""),
    var fiscalia: TextFieldValue = TextFieldValue(""),
    var nuc: TextFieldValue = TextFieldValue(""),
    var password: TextFieldValue = TextFieldValue(""),
    var unidadInv: TextFieldValue = TextFieldValue(""),
    // client info
    var nombre: TextFieldValue = TextFieldValue(""),
    var correo: TextFieldValue = TextFieldValue(""),
    var sexo: String = "sexo"
)

@Composable
fun FormCasoScreen(
    navController: NavController,
    casosViewModel: CasosViewModel,
    casoId: Int? = null
) {
    val casoToEdit = casoId?.let {
        casosViewModel.getCasoInfo(it) // Supón que tienes una función que busca el caso
    }

    val isEditMode = casoToEdit != null
    val formState = remember {
        mutableStateOf(
            casoToEdit?.let {
                // Si se está editando, cargar la información del caso en el formulario
                CasoFormState(
                    titulo = TextFieldValue(it.titulo),
                    descripcion = TextFieldValue(it.descripcion),
                    cInvestigacion = TextFieldValue(it.c_investigacion),
                    cJudicial = TextFieldValue(it.c_judicial),
                    delito = TextFieldValue(it.delito),
                    direccion = TextFieldValue(it.direccion_ui),
                    drive = TextFieldValue(it.drive_link),
                    fiscalia = TextFieldValue(it.fiscalia_virtual),
                    nuc = TextFieldValue(it.nuc),
                    password = TextFieldValue(it.password_fv),
                    unidadInv = TextFieldValue(it.unidad_investigacion),
                    nombre = TextFieldValue(),
                    correo = TextFieldValue(),
                    sexo = "",
                )
            } ?: CasoFormState() // Si es nuevo, usar estado vacío
        )
    }

    // Client Info
    var nombre by remember { mutableStateOf(TextFieldValue("")) }
    var correo by remember { mutableStateOf(TextFieldValue("")) }
    var sexo by remember { mutableStateOf("") }

    // Errores
    var errorMessage1 by remember { mutableStateOf("") }
    var errorMessage2 by remember { mutableStateOf("") }
    var errorMessage3 by remember { mutableStateOf("") }
    var errorMessage4 by remember { mutableStateOf("") }

    // Actualización del estado usando copy
    val casoAMandar = SendCaso(
        titulo = formState.value.titulo.text,
        descripcion = formState.value.descripcion.text,
        c_investigacion = formState.value.cInvestigacion.text,
        c_judicial = formState.value.cJudicial.text,
        delito = formState.value.delito.text,
        direccion_ui = formState.value.direccion.text,
        drive_link = formState.value.drive.text,
        fiscalia_virtual = formState.value.fiscalia.text,
        nuc = formState.value.nuc.text,
        password_fv = formState.value.password.text,
        unidad_investigacion = formState.value.unidadInv.text,
        id_cliente = 1,
        id_abogado = 1,
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
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Información del Caso", style = MaterialTheme.typography.titleLarge)
            Text("Responde el siguiente formulario para tener la información sobre el caso de manera sencilla de obtener. Recuerda que * significa que el campo es obligatorio.", style = MaterialTheme.typography.bodySmall)

            Text("Campos obligatorios", style = MaterialTheme.typography.titleSmall)
            InputField(
                label = "*Título:",
                value = formState.value.titulo,
                onValueChange = { formState.value = formState.value.copy(titulo = it) }
            )
            InputField(
                label = "*Delito:",
                value = formState.value.delito,
                onValueChange = { formState.value = formState.value.copy(delito = it) }
            )
            InputField(
                label = "*Descripción del caso:",
                value = formState.value.descripcion,
                onValueChange = { formState.value = formState.value.copy(descripcion = it) },
                singleLine = false,
                modifier = Modifier.height(200.dp)
            )

            if (errorMessage2.isNotBlank()){ Text(errorMessage2)}
            Text("Campos opcionales", style = MaterialTheme.typography.titleSmall)

            InputField(
                label = "Carpeta de investigación:",
                value = formState.value.cInvestigacion,
                onValueChange = { formState.value = formState.value.copy(cInvestigacion = it) }
            )
            InputField(
                label = "Carpeta judicial:",
                value = formState.value.cJudicial,
                onValueChange = { formState.value = formState.value.copy(cJudicial = it) }
            )

            InputField(
                label = "Unidad de Investigación:",
                value = formState.value.unidadInv,
                onValueChange = { formState.value = formState.value.copy(unidadInv = it) }
            )

            if(errorMessage3.isNotBlank()){Text(errorMessage3)}

            InputField(
                label = "Número Único de Causa:",
                value = formState.value.nuc,
                onValueChange = { formState.value = formState.value.copy(nuc = it) }
            )
            InputField(
                label = "Contraseña de la Fiscalía Virtual:",
                value = formState.value.password,
                onValueChange = { formState.value = formState.value.copy(password = it) }
            )


            Text("Links de acceso rápido", style = MaterialTheme.typography.titleSmall)

            InputField(
                label = "Carpeta Google Drive:",
                value = formState.value.drive,
                onValueChange = { formState.value = formState.value.copy(drive = it) }
            )
            if(formState.value.drive.text.isNotBlank() && !formState.value.drive.text.startsWith("https://")){
                Text("La URL debe comenzar con https://", color = Color.Red, style = MaterialTheme.typography.bodySmall)
            }
            InputField(
                label = "Fiscalía Virtual:",
                value = formState.value.fiscalia,
                onValueChange = { formState.value = formState.value.copy(fiscalia = it) }
            )
            if(formState.value.fiscalia.text.isNotBlank() && !formState.value.fiscalia.text.startsWith("https://")){
                Text("La URL debe comenzar con https://", color = Color.Red, style = MaterialTheme.typography.bodySmall)
            }
            InputField(
                label = "Dirección de la Unidad de Investigación:",
                value = formState.value.direccion,
                onValueChange = { formState.value = formState.value.copy(direccion = it) }
            )
            if(formState.value.direccion.text.isNotBlank() && !formState.value.direccion.text.startsWith("https://")){
                Text("La URL debe comenzar con https://", color = Color.Red, style = MaterialTheme.typography.bodySmall)
            }

            if(errorMessage4.isNotBlank()){Text(errorMessage4)}

            Text("Información del Cliente", style = MaterialTheme.typography.titleLarge)

            InputField(
                label = "*Nombre del cliente:",
                value = nombre,
                onValueChange = { nombre = it }
            )
            InputField(
                label = "*Correo del cliente:",
                value = correo,
                onValueChange = { correo = it }
            )
            Text("La contraseña del cliente será 'LEGALMATCH', se recomienda que el cliente la actualice lo antes posible.")
            CustomDropdownMenu(
                selectedValue = sexo,
                options = listOf("Hombre", "Mujer"),
                label = "Sexo:",
                onValueChangedEvent = { selectedOption ->
                    sexo = selectedOption
                }
            )
            /*
CustomDropdownMenu(
    selectedValue = "xd",
    options = listOf("Acusado", "demandante"),
    label = "Rol",
    onValueChangedEvent = { selectedOption ->
        println("")
    }
)

 */

            // Botón para agendar asesoría
            Button(
                onClick = {
                    errorMessage1 = if(nombre.text.isBlank() || correo.text.isBlank()){
                        "Nombre y correo son obligatorios" } else { "" }

                    errorMessage2 = if(formState.value.titulo.text.isBlank() || formState.value.descripcion.text.isBlank()){
                        "Titulo y descripcion son obligatorios" } else { "" }
                    errorMessage3 = if(isUrlValid(formState.value.direccion.text)){
                        "URL no válido" } else {""}
                    errorMessage4 = if(isUrlValid(formState.value.drive.text)){
                        "URL no válido"} else {""}


                    if(errorMessage1.isBlank() && errorMessage2.isBlank()){

                        if (isEditMode) {
                            casosViewModel.updateCaso(casoAMandar) // Actualiza el caso existente
                        } else {
                            casosViewModel.createCaso(casoAMandar) // Crea un nuevo caso
                        }
                        navController.navigate(Routes.Casos.route)

                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .padding(vertical = 4.dp),
                shape = RoundedCornerShape(18.dp)
            ) {
                if(isEditMode){
                    Text(text = "Guardar Cambios", fontSize = 18.sp)
                } else {
                    Text(text = "Crear Caso", fontSize = 18.sp)
                }

            }
            Text("La contraseña del cliente será 'LEGALMATCH', se recomienda que el cliente la actualice lo antes posible.", style = MaterialTheme.typography.bodySmall)
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
) {
    OutlinedTextField(
        value = value,
        label = { Text(text = label) },
        onValueChange = onValueChange,
        singleLine = singleLine,
        textStyle = TextStyle(fontSize = 12.sp),
        modifier = modifier
            .fillMaxWidth()
    )

}

fun isUrlValid(url: String) : Boolean{
    if (url.startsWith("http://") || url.startsWith("https://") || url.isBlank()) return true
    else return false
}