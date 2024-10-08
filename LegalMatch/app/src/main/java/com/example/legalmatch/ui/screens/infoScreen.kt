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
fun InfoScreen(navController: NavController) {
    // Estado para manejar los datos del formulario
    var selectedRole by remember { mutableStateOf("Selecciona un rol") }
    var selectedDate by remember { mutableStateOf("Selecciona una fecha") }
    var selectedHour by remember { mutableStateOf("Selecciona el horario de tu cita") }
    var description by remember { mutableStateOf(TextFieldValue("")) }

    val roles = listOf("Demandado", "Demandante")
    val dates = listOf("10/10/2024", "15/10/2024", "20/10/2024") // Ejemplo de fechas
    val horarios = listOf("9:00 am", "10:00 am", "11:00 am", "12:00 pm")

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
fun InfoScreenPreview() {
    InfoScreen(navController = rememberNavController())
}
