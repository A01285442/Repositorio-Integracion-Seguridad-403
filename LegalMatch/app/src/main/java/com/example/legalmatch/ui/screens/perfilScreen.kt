package com.example.legalmatch.ui.screens

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.app.navigation.Routes
import com.example.legalmatch.ui.components.CustomBottomBar
import com.example.legalmatch.ui.components.CustomBottomBarClientes
import com.example.legalmatch.ui.components.CustomTopBar
import com.example.legalmatch.ui.theme.AzulTec
import okio.utf8Size
import java.time.LocalDate
import kotlin.math.log

private const val TAG = "MainActivity"

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun PerfilScreen(navController: NavController, loginViewModel: LoginViewModel) {

    val loginState by loginViewModel.loginState.collectAsState()
    var showDialogCambiarContraseña by remember { mutableStateOf(false) }
    var contraseñaActual by remember { mutableStateOf("") }
    var nuevaContraseña by remember { mutableStateOf("") }
    var nuevaContraseña2 by remember { mutableStateOf("") }
    var botonCambiar by remember { mutableStateOf(false)}
    val usuario = loginState.userClient ?: return

    val esFiscal = loginState.userClient!!.rol == "abogado"

    Scaffold(
        topBar = { CustomTopBar(title = "Perfil", navIcon = false, actIcon = false) },
        bottomBar = {
            if (usuario.rol == "cliente") {
                CustomBottomBarClientes(navController = navController)
            } else {
                CustomBottomBar(navController = navController)
            }
        }
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
            var num = ""
            var text = ""

            userCreationDate?.let {
                if (userCreationDate.year - todaysDate.year != 0){
                    num = (todaysDate.year - userCreationDate.year).toString()
                    text = "años"
                }
                else {
                    num = (todaysDate.dayOfYear - userCreationDate.dayOfYear).toString()
                    text = "dias"
                }
            }



            // Stats (años, reseñas, casos cerrados)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ProfileStat(big = num, medium = text, description = "En Legal Match")
                ProfileStat(big = "4.7", medium = "★", description = "12 Reseñas")
                ProfileStat(big = "62", medium = "",  description = "Casos cerrados")
            }

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(12.dp))

            val fecha = usuario.fecha_nacimiento.date
            if(usuario.matricula.isNotBlank()){ SpacedInformation("Matricula:", usuario.matricula) }
            SpacedInformation("Sexo:", usuario.sexo)
            SpacedInformation("Tipo de cuenta:", usuario.rol)
            SpacedInformation("Fecha de nacimiento:", "${fecha.dayOfMonth} ${toSpanish(fecha.monthNumber)} ${fecha.year}")
            SpacedInformation("Contraseña:", usuario.contraseña.replace(".?".toRegex(),"*"))

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(12.dp))

            // Botón para cambiar contraseña
            Button(
                onClick = { showDialogCambiarContraseña = true},
                colors = ButtonDefaults.buttonColors(
                    containerColor = AzulTec,
                    contentColor = Color.White,
                    disabledContentColor = Color.White,
                    disabledContainerColor = Color(0xFF87B2E4)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                elevation = ButtonDefaults.elevatedButtonElevation(8.dp)
            ) {
                Text("Cambiar contraseña")
            }


            if(esFiscal){
                Button(
                    onClick = {navController.navigate(Routes.ListaEstudiantes.route)},
                    colors = ButtonDefaults.buttonColors(containerColor = AzulTec),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    elevation = ButtonDefaults.elevatedButtonElevation(8.dp)
                ) {
                    Text("Editar Estudiantes")
                }
            }



            // Botón de cerrar sesión
            Button(
                onClick = { loginViewModel.closeSession(then = {
                    navController.navigate(Routes.Login.route)
                }) },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                elevation = ButtonDefaults.elevatedButtonElevation(10.dp)
            ) {
                Text(text = "Cerrar Sesión", color = Color.White)
            }

            if (showDialogCambiarContraseña) {
                AlertDialog(
                    onDismissRequest = { showDialogCambiarContraseña = false },
                    title = { Text(text = "Cambiar Contraseña") },
                    text = {
                        Column {

                            TextField(
                                value = contraseñaActual,
                                onValueChange = { contraseñaActual = it },
                                label = { Text("Contraseña Actual") },
                                modifier = Modifier.fillMaxWidth(),
                                visualTransformation = PasswordVisualTransformation()
                            )


                            // Campo para ingresar la matrícula
                            TextField(
                                value = nuevaContraseña,
                                onValueChange = { nuevaContraseña = it },
                                label = { Text("Nueva Contraseña") },
                                modifier = Modifier.fillMaxWidth(),
                                visualTransformation = PasswordVisualTransformation()
                            )

                            // Campo para ingresar la nueva contraseña
                            TextField(
                                value = nuevaContraseña2,
                                onValueChange = { nuevaContraseña2 = it },
                                label = { Text("Nueva Contraseña") },
                                modifier = Modifier.fillMaxWidth(),
                                visualTransformation = PasswordVisualTransformation() // Para ocultar la contraseña
                            )
                            if (contraseñaActual != loginState.userClient?.contraseña){
                                Text("La contraseña actual es incorrecta.")
                            }
                            else if (nuevaContraseña.toByteArray().size < 8){
                                Text("Mínimo 8 caracteres")
                            }
                            else if (nuevaContraseña!=nuevaContraseña2){
                                Text("Las contraseñas deben ser iguales")
                            }
                            else botonCambiar = true
                        }
                    },
                    confirmButton = {
                        Button(
                            enabled = botonCambiar,
                            colors = ButtonColors(AzulTec, Color.White, Color(0xFF87B2E4), Color.White),
                            onClick = {
                                loginViewModel.cambioContraseña(nuevaContraseña)

                                showDialogCambiarContraseña = false // Cierra el diálogo después de cambiar la contraseña
                            }
                        ) {
                            Text("Cambiar")
                        }
                    },
                    dismissButton = {
                        Button(
                            colors = ButtonColors(Color.Gray, Color.White, Color.Gray, Color.Gray),
                            onClick = { showDialogCambiarContraseña = false }
                        ) {
                            Text("Cancelar")
                        }
                    }
                )
            }

        }
    }
}

fun toSpanish(month: Int): String {
    return if (month == 1){ "ene" }
    else if (month == 2){ "feb" }
    else if (month == 3){ "mar" }
    else if (month == 4){ "abr" }
    else if (month == 5){ "may" }
    else if (month == 6){ "jun" }
    else if (month == 7){ "jul" }
    else if (month == 8){ "ago" }
    else if (month == 9){ "sep" }
    else if (month == 10){ "oct" }
    else if (month == 11){ "nov" }
    else { "dic" }
}

@Composable
fun SpacedInformation(
    label: String,
    value: String,
){
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Absolute.SpaceBetween
    ){
        Text(label, style = MaterialTheme.typography.bodyMedium)
        Text(value, style = MaterialTheme.typography.bodyMedium)
    }
}


@Composable
fun ProfileStat(big: String, medium: String, description: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row {
            Text(text = big, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
            Text(text = medium, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        }

        Text(text = description, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
    }
}