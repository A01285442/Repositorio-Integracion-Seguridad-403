package com.example.legalmatch.ui.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.legalmatch.ui.components.CustomBottomBar
import com.example.legalmatch.ui.components.CustomTopBar
import kotlin.reflect.jvm.internal.ReflectProperties.Val

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun PerfilScreen(navController: NavController, loginViewModel: LoginViewModel) {
    val TAG = "MainActivity"

    val user by loginViewModel.userClient.collectAsState()
/*
    if (user == null) {
        Log.d(TAG, "Usuario es null. Es probable que no hayas iniciado sesión correctamente.")
    } else {
        Log.d(TAG, "Usuario autenticado: ${user!!.correo}")
    }

 */


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
            // Imagen de perfil
            // Icono de perfil en la parte superior (usa un Image si tienes la imagen)



            if (user != null) {
                // La UI se actualiza automáticamente cuando el usuario ya no es null
                Text(text = "Bienvenido, ${user!!.correo}")
            } else {
                Text(text = "Por favor, inicie sesión.")
            }

            user?.let {
                Text(
                    text = it.nombre,
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(top = 16.dp),
                    fontWeight = FontWeight.Bold
                )
            }

            Text(
                text = "dTrump@gmail.com",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Stats (años, reseñas, casos cerrados)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ProfileStat(text = "2 años", description = "At Legal Match")
                ProfileStat(text = "4.7 ★", description = "12 Reseñas")
                ProfileStat(text = "62", description = "Casos cerrados")
            }

            // Opciones con switches
            Column(modifier = Modifier.padding(vertical = 16.dp)) {
                SwitchOption("Recordatorio de asesoría", "Te recordaremos 1 hora antes de cada asesoría confirmada")
                SwitchOption("Editar lista de estudiantes", "Te recordaremos 1 hora antes de cada asesoría confirmada")
                SwitchOption("Activar Derechos", "Te recordaremos 1 hora antes de cada asesoría confirmada")
            }

            // Botón de cerrar sesión
            Button(
                onClick = { /* Acción de cerrar sesión */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                elevation = ButtonDefaults.elevatedButtonElevation(10.dp)
            ) {
                Text(text = "Cerrar Sesión", color = Color.White)
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
        Switch(checked = true, onCheckedChange = { /* Acción del switch */ })
    }
}
