package com.example.legalmatch.ui.screens

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.app.navigation.Routes
import com.example.legalmatch.ui.components.CustomBottomBar
import com.example.legalmatch.ui.components.CustomTopBar
import com.example.legalmatch.ui.theme.AzulTec
import java.time.LocalDate

private const val TAG = "MainActivity"

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun PerfilScreen(navController: NavController, loginViewModel: LoginViewModel) {

    val loginState by loginViewModel.loginState.collectAsState()
    var showDialogAñadirEstudiante by remember { mutableStateOf(false) }
    var matricula by remember { mutableStateOf("") }

    Scaffold(
        topBar = { CustomTopBar(title = "Perfil", navIcon = false, actIcon = false) },
        bottomBar = { CustomBottomBar(navController=navController) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(Color.White)
                .padding(horizontal = 16.dp)
        ) {

            loginState.userClient?.let {
                Text(
                    text = it.nombre,
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(top = 16.dp).fillMaxWidth(),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = it.correo,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 16.dp).fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }

            val userCreationDate = loginState.userClient?.created_at?.date
            val todaysDate = LocalDate.now()
            var res = ""

            userCreationDate?.let {
                if (userCreationDate.year - todaysDate.year != 0){
                    res = (todaysDate.year - userCreationDate.year).toString() + " años"
                }
                else {
                    res = (todaysDate.dayOfYear - userCreationDate.dayOfYear).toString() + " días"
                }
            }



            // Stats (años, reseñas, casos cerrados)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ProfileStat(text = res , description = "En Legal Match")
                ProfileStat(text = "4.7 ★", description = "12 Reseñas")
                ProfileStat(text = "62", description = "Casos cerrados")
            }

            // Opciones con switches
            Column(modifier = Modifier.padding(vertical = 16.dp)) {
                SwitchOption("Recordatorio de asesoría", "Te recordaremos 1 hora antes de cada asesoría confirmada")
                SwitchOption("Editar lista de estudiantes", "Te recordaremos 1 hora antes de cada asesoría confirmada")
                SwitchOption("Activar Derechos", "Te recordaremos 1 hora antes de cada asesoría confirmada")
            }

            Button(
                onClick = {showDialogAñadirEstudiante=true},
                colors = ButtonDefaults.buttonColors(containerColor = AzulTec),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                elevation = ButtonDefaults.elevatedButtonElevation(8.dp)
            ) {
                Text("Añadir estudiantes")
            }
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(containerColor = AzulTec),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                elevation = ButtonDefaults.elevatedButtonElevation(8.dp)
            ) {
                Text("Eliminar estudiantes")
            }

            // Botón de cerrar sesión
            Button(
                onClick = { loginViewModel.closeSession(then = {
                    navController.navigate(Routes.Login.route)
                }) },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                elevation = ButtonDefaults.elevatedButtonElevation(10.dp)
            ) {
                Text(text = "Cerrar Sesión", color = Color.White)
            }

            if (showDialogAñadirEstudiante) {
                AlertDialog(
                    onDismissRequest = {
                        // Acción cuando se cierra el diálogo (fuera del área de alerta o con el botón "Cancelar")
                        showDialogAñadirEstudiante = false
                    },
                    title = { Text(text = "Añade a un estudiante a la clínica penal") },
                    text = {
                        Column {
                            Text("Selecciona una matrícula. Su contraseña será '123', asegúrate que inmediatamente.")

                            TextField(
                                value = matricula,
                                onValueChange = { matricula = it },
                                label = { Text("Matricula") },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }

                    },
                    confirmButton = {
                        Button(
                            colors = ButtonColors(AzulTec,Color.White,Color.Gray,Color.Gray),
                            onClick = {
                                // Acción del botón de confirmación
                                showDialogAñadirEstudiante = false
                            }) {
                            Text("Aceptar Caso")
                        }
                    },
                    dismissButton = {
                        Button(
                            colors = ButtonColors(AzulTec,Color.White,Color.Gray,Color.Gray),
                            onClick = {

                                // Acción del botón de cancelación
                                showDialogAñadirEstudiante = false
                            }) {
                            Text("Cancelar")
                        }
                    }
                )
            }
        }


    }
}

@Composable
fun ProfileStat(text: String, description: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = text, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Text(text = description, style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
    }
}

@Composable
fun SwitchOption(option: String, description: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(text = option, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
            Text(text = description, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
        }
        Switch(checked = true, onCheckedChange = { /* Acción del switch*/}) }
}