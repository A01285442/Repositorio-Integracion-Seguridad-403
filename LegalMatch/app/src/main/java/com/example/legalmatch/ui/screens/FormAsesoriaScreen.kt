package com.example.legalmatch.ui.screens

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.legalmatch.data.api.models.SendAsesoria
import com.example.legalmatch.ui.components.CustomBottomBarClientes
import com.example.legalmatch.ui.components.CustomDropdownMenu
import com.example.legalmatch.ui.components.CustomTopBar
import com.example.legalmatch.ui.components.DatePicker
import com.example.legalmatch.ui.theme.AzulTec
import com.example.legalmatch.ui.theme.GhostWhite
import com.example.legalmatch.utils.GEMINI_KEY
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.Serializable
import org.json.JSONObject
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL


@Serializable
data class correcion(
    val titulo :String,
    val tipoDelito :String,
    val descripcionModificada: String
)

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FormAsesoriaScreen(navController: NavController, viewModel:AsesoriaViewModel) {
    // Estado para manejar los datos del formulario
    var selectedRole by remember { mutableStateOf("Seleccionar") }
    var selectedHour by remember { mutableStateOf("Selecciona el horario de tu cita") }
    var description by remember { mutableStateOf("") }

    val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    var selectedDate by remember { mutableStateOf(now.date) }
    var selectedTime by remember { mutableStateOf(now.time) }
    val selectedDateTime = LocalDateTime(selectedDate,selectedTime)
    //Estado para manejar eñ api

    var apiResult by remember { mutableStateOf<correcion?>(null) }
    val roles = listOf("Acusado", "Demandante")
    val horarios = listOf("10:00 am", "11:00 am", "12:00 pm", "1:00 pm", "2:00 pm", "3:00 pm", "4:00 pm")

    Scaffold(
        topBar = {
            CustomTopBar(title = "Agendar Asesoría", navIcon = true, actIcon = false, navController = navController)
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


            // Menú desplegable para seleccionar el rol
            Text(text = "Posición", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)

            CustomDropdownMenu(
                selectedValue = selectedRole,
                options = roles,
                label = "Posición en el caso",
                onValueChangedEvent =  {selectedOption ->
                    selectedRole = selectedOption
                },
                modifier = Modifier

            )

            Text(text = "Fecha y hora de la asesoría", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)

            DatePicker(
                selectedDateTime = selectedDateTime,
                onDateChange = { selectedDate = it.date },
            )

            CustomDropdownMenu(
                selectedValue = selectedHour,
                options = horarios,
                label = "Horarios",
                onValueChangedEvent =  {selectedOption ->
                    selectedHour = selectedOption
                },
                modifier = Modifier
            )

            when (selectedHour) {
                "10:00 am" -> {selectedTime = LocalTime(10,0,0) }
                "11:00 am" -> {selectedTime = LocalTime(11,0,0) }
                "12:00 am" -> {selectedTime = LocalTime(12,0,0) }
                "1:00 pm" -> {selectedTime = LocalTime(13,0,0) }
                "2:00 pm" -> {selectedTime = LocalTime(14,0,0) }
                "3:00 pm" -> {selectedTime = LocalTime(15,0,0) }
                "4:00 pm" -> {selectedTime = LocalTime(16,0,0) }
            }

            // Campo para la descripción del delito
            Text(text = "¿Por qué buscas asesoramiento legal?", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Text("¿Cómo comenzó la situación? ¿Existen pruebas o testigos? ¿Tiene antecedentes penales?")
            BasicTextField(
                value = description,
                onValueChange = { description = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(12.dp))
                    .padding(8.dp)
            )

            // Botón para agendar asesoría
            Button(
                onClick = {
                    makeApiRequest(description) { result ->
                        apiResult = result }

                    val esDemandante = selectedRole == "Demandante"

                    // Crear una instancia de Asesoria con los datos del formulario
                    val newAsesoria = apiResult?.let {
                        SendAsesoria(
                            c_investigacion = "", // Modificar si tienes este dato
                            c_judicial = "",      // Modificar si tienes este dato
                            estado = "pendiente", // Ejemplo de estado
                            cliente_confirmado = true,
                            cliente_denuncio = esDemandante,
                            delito = it.tipoDelito, // Usar el rol seleccionado como delito (modificar si es necesario)
                            descripcion = description,
                            fecha_asesoria = selectedDateTime,
                            id_cliente = 1, // Este es un ejemplo, debes obtener el id del cliente actual
                            nuc = "N/A", // Modificar si tienes este dato
                            titulo = it.titulo, // Este es un título de ejemplo
                            descripcion_modificada = it.descripcionModificada // Aquí va el texto corregido por la API
                        )
                    }

                    // Llamar a la función enviarAsesoria del ViewModel
                    if (newAsesoria != null) {
                        viewModel.enviarAsesoria(newAsesoria)
                    }
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
                Text(text = "Agendar Asesoría", fontSize = 18.sp)
            }

            // Mostrar los resultados si 'apiResult' no es nulo
            apiResult?.let { correccion ->
                Column {
                    Text("Título: ${correccion.titulo}")
                    Text("Tipo de Delito: ${correccion.tipoDelito}")
                    Text("Descripción Corregida: ${correccion.descripcionModificada}")
                }
            } ?: Text("No se ha recibido ninguna corrección aún.")
            //Text(text = correctedText)
        }
    }
}

@OptIn(DelicateCoroutinesApi::class)
fun makeApiRequest(ask: String, onResult: (correcion?) -> Unit) {
    // Se realiza en una corrutina para no bloquear el hilo principal
    kotlinx.coroutines.GlobalScope.launch(Dispatchers.IO) {
        try {
            val url = URL("https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent?key=$GEMINI_KEY")
            val jsonInputString = """
            {
                "contents": [
                    {
                        "role": "user",
                        "parts": [
                            {"text": "Proporcionaré una descripción de un caso penal. Necesito que me des los siguientes elementos en formato JSON: un título, el tipo de delito, y una descripción corregida en caso de que la original tenga errores de no mas de 200 palabras. Ojo, no tienes que poner la palabra json al inicio, solo dame el puro formato json, sin nada extra. El JSON debe tener exactamente los siguientes campos: 'titulo', 'tipoDelito' y 'descripcionModificada'."}
                        ]
                    },
                    {
                        "role": "user",
                        "parts": [
                            {"text": "$ask"}
                        ]
                    }
                ]
            }
            """.trimIndent()

            // Configuración de la conexión HTTP
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "POST"
            connection.setRequestProperty("Content-Type", "application/json")
            connection.doOutput = true

            // Escritura del cuerpo de la solicitud
            withContext(Dispatchers.IO) {
                OutputStreamWriter(connection.outputStream).use { writer ->
                    writer.write(jsonInputString)
                    writer.flush()
                }
            }

            // Lectura de la respuesta
            val responseCode = connection.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                val responseText = connection.inputStream.bufferedReader().use { it.readText() }
                val jsonResponse = parseResponse(responseText)
                val prueba = decodeJson(jsonResponse)
                withContext(Dispatchers.Main) {
                    onResult(prueba)
                }
            } else {
                withContext(Dispatchers.Main) {
                    onResult(null)
                }
            }
            connection.disconnect()
        } catch (e:Exception){
            Log.d("MainActivity", "Error: ${e.message}")
        }

    }
}

// Modificación en esta función para que devuelva un JSON y almacene datos en variables
fun parseResponse(response: String): String {
    try {
        // Convertimos la cadena de respuesta en un objeto JSON
        val jsonObject = JSONObject(response)

        // Accedemos al array "candidates"
        val candidates = jsonObject.getJSONArray("candidates")

        // Tomamos el primer objeto en el array "candidates"
        if (candidates.length() > 0) {
            val firstCandidate = candidates.getJSONObject(0)

            // Accedemos al contenido del primer candidato
            val content = firstCandidate.getJSONObject("content")

            // Accedemos al array "parts"
            val parts = content.getJSONArray("parts")

            // Tomamos el primer objeto en el array "parts" y extraemos el campo "text"
            if (parts.length() > 0) {
                val firstPart = parts.getJSONObject(0)
                val content = firstPart.getString("text")
                return content
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
        return "Error al parsear la respuesta."
    }
    return "No se encontró contenido relevante."
}

fun decodeJson(response: String): correcion? {
    return try {
        // Convertimos el string JSON a un objeto JSON
        val jsonObject = JSONObject(response)

        // Extraemos los valores por sus llaves
        val titulo = jsonObject.getString("titulo")
        val tipoDelito = jsonObject.getString("tipoDelito")
        val descripcionModificada = jsonObject.getString("descripcionModificada")

        // Creamos y retornamos una instancia de 'correcion'
        correcion(titulo, tipoDelito, descripcionModificada)
    } catch (e: Exception) {
        e.message?.let { Log.d("MainActivity", it) }
        e.printStackTrace()
        null
    }
}

