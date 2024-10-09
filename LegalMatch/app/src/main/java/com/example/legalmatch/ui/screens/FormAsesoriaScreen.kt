package com.example.legalmatch.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.legalmatch.ui.components.CustomBottomBarClientes
import com.example.legalmatch.ui.components.CustomTopBar
import com.example.legalmatch.ui.theme.AzulTec
import com.example.legalmatch.ui.theme.GhostWhite
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL
import java.io.OutputStreamWriter
import org.json.JSONObject


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormAsesoriaScreen(navController: NavController) {
    // Estado para manejar los datos del formulario
    var selectedRole by remember { mutableStateOf("Selecciona un rol") }
    var selectedDate by remember { mutableStateOf("Selecciona una fecha") }
    var selectedHour by remember { mutableStateOf("Selecciona el horario de tu cita") }
    var description by remember { mutableStateOf(TextFieldValue("")) }

    //Estado para manejar eñ api

    var correctedText by remember { mutableStateOf("") }


    LocalDateTime.now().dayOfMonth
    LocalDateTime.now().monthValue
    LocalDateTime.now().year


    val roles = listOf("Demandado", "Demandante")
    val dates = listOf(
        LocalDateTime.now().dayOfMonth.toString() + " de " + LocalDateTime.now().monthValue.toString(),
        (LocalDateTime.now().dayOfMonth +1).toString() + " de " + (LocalDateTime.now().monthValue+1).toString(),
        (LocalDateTime.now().dayOfMonth +2).toString() + " de " + (LocalDateTime.now().monthValue+2).toString(),
        (LocalDateTime.now().dayOfMonth +3).toString() + " de " + (LocalDateTime.now().monthValue+3).toString(),
        (LocalDateTime.now().dayOfMonth +4).toString() + " de " + (LocalDateTime.now().monthValue+4).toString(),
        (LocalDateTime.now().dayOfMonth +5).toString() + " de " + (LocalDateTime.now().monthValue+5).toString(),
        (LocalDateTime.now().dayOfMonth +6).toString() + " de " + (LocalDateTime.now().monthValue+6).toString(),
        (LocalDateTime.now().dayOfMonth +7).toString() + " de " + (LocalDateTime.now().monthValue+7).toString(),
        (LocalDateTime.now().dayOfMonth +8).toString() + " de " + (LocalDateTime.now().monthValue+8).toString(),
        (LocalDateTime.now().dayOfMonth +9).toString() + " de " + (LocalDateTime.now().monthValue+9).toString(),

    ) // Ejemplo de fechas
    val horarios = listOf("9:00 am", "10:00 am", "11:00 am", "12:00 pm", "1:00 pm", "2:00 pm", "3:00 pm", "4:00 pm")

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


            // Menú desplegable para seleccionar el rol
            Text(text = "Selecciona si eres demandado o demandante", fontSize = 18.sp)
            var expandedRole by remember { mutableStateOf(false) }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(12.dp))
                    .padding(horizontal = 12.dp, vertical = 4.dp)
            ) {
                TextButton(
                    onClick = { expandedRole = !expandedRole },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = selectedRole,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                DropdownMenu(
                    expanded = expandedRole,
                    onDismissRequest = { expandedRole = false }
                ) {
                    roles.forEach { role ->
                        DropdownMenuItem(
                            text = { Text(role) },
                            onClick = {
                                selectedRole = role
                                expandedRole = false
                            }
                        )
                    }
                }
            }

            // Menú desplegable para seleccionar la fecha
            Text(text = "Fecha:", fontSize = 18.sp)
            var expandedDate by remember { mutableStateOf(false) }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(12.dp))
                    .padding(horizontal = 12.dp, vertical = 4.dp)
            ) {
                TextButton(
                    onClick = { expandedDate = !expandedDate },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = selectedDate,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                DropdownMenu(
                    expanded = expandedDate,
                    onDismissRequest = { expandedDate = false }
                ) {
                    dates.forEach { date ->
                        DropdownMenuItem(
                            text = { Text(date) },
                            onClick = {
                                selectedDate = date
                                expandedDate = false
                            }
                        )
                    }
                }
            }

            // Menú desplegable para seleccionar el horario
            Text(text = "Horario:", fontSize = 18.sp)
            var expandedHour by remember { mutableStateOf(false) }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(12.dp))
                    .padding(horizontal = 12.dp, vertical = 4.dp)
            ) {
                TextButton(
                    onClick = { expandedHour = !expandedHour },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = selectedHour,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                DropdownMenu(
                    expanded = expandedHour,
                    onDismissRequest = { expandedHour = false }
                ) {
                    horarios.forEach { horario ->
                        DropdownMenuItem(
                            text = { Text(horario) },
                            onClick = {
                                selectedHour = horario
                                expandedHour = false
                            }
                        )
                    }
                }
            }

            // Campo para la descripción del delito
            Text(text = "Descripción Delito:", fontSize = 18.sp)
            BasicTextField(
                value = description,
                onValueChange = { description = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(12.dp))
                    .padding(8.dp)
            )
            // Botón para enviar el texto a la IA
            Button(
                onClick = {
                    makeApiRequest(description.text) { result ->
                        correctedText = result
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Blue, contentColor = Color.White)
            ) {
                Text(text = "Enviar texto a IA", fontSize = 18.sp)
            }

            // Mostrar el texto corregido
            Text(text = "Texto corregido por la IA:", fontSize = 18.sp)
            BasicTextField(
                value = correctedText,
                onValueChange = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .border(1.dp, MaterialTheme.colorScheme.primary, shape = MaterialTheme.shapes.medium)
                    .padding(8.dp),
                enabled = false // Hacer el campo solo de lectura
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

@OptIn(DelicateCoroutinesApi::class)
fun makeApiRequest(ask: String, onResult: (String) -> Unit) {
    kotlinx.coroutines.GlobalScope.launch(Dispatchers.IO) {
        val apiKey = "AIzaSyD2BLAqAzEH5-76h16dSpf5sLJkxlcqw7k"
        val url = URL("https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent?key=$apiKey")
        val jsonInputString = """
            {
                "contents": [
                    {
                        "role": "user",
                        "parts": [
                            {"text": "Te proporcionare informacion a continuacion, esta informacion es la descripcion de un caso penal escrita por una persona que ocupa asesoria legal. De esta informacion ocupo que me proves un titulo, que tipo de delito es y una descripcion modificada si tiene falta de ortografia o si la descripcion proveida no es coherente. La descripcion no debe de pasar de 200 palabras y de una escala del 1 al 10 me debes de decir que tanto modificaste la descripcion. Todo me lo debes de dar en este orden y debe de tener un salto de linea en cada parte."}
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

        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "POST"
        connection.setRequestProperty("Content-Type", "application/json")
        connection.doOutput = true

        withContext(Dispatchers.IO) {
            OutputStreamWriter(connection.outputStream).use { writer ->
                writer.write(jsonInputString)
                writer.flush()
            }
        }

        val responseCode = connection.responseCode
        if (responseCode == HttpURLConnection.HTTP_OK) {
            val responseText = connection.inputStream.bufferedReader().use { it.readText() }
            val jsonResponse = parseResponse(responseText)
            withContext(Dispatchers.Main) {
                onResult(jsonResponse)
            }
        } else {
            withContext(Dispatchers.Main) {
                onResult("Error: $responseCode")
            }
        }
        connection.disconnect()
    }
}

fun parseResponse(response: String): String {
    try {
        val jsonObject = JSONObject(response)
        val candidates = jsonObject.getJSONArray("candidates")
        if (candidates.length() > 0) {
            val firstCandidate = candidates.getJSONObject(0)
            val content = firstCandidate.getJSONObject("content")
            val parts = content.getJSONArray("parts")
            if (parts.length() > 0) {
                val firstPart = parts.getJSONObject(0)
                return firstPart.getString("text")
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
        return "Error al parsear la respuesta."
    }
    return "No se encontró contenido relevante."
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
@Preview(showBackground = true)
fun FormAsesoriaScreenPreview() {
    FormAsesoriaScreen(navController = rememberNavController())
}