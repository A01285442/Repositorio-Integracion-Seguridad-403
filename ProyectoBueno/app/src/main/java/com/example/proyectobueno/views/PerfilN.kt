package com.example.proyectobueno.views



import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.proyectobueno.R
import com.example.proyectobueno.ui.theme.BlueTEC

//Json
import android.content.Context
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import org.json.JSONObject
import java.io.IOException


fun loadUserData(context: Context, email: String): Pair<String, String>? {
    val json: String
    try {
        json = context.assets.open("Perfiles.json").bufferedReader().use { it.readText() }
        val jsonObject = JSONObject(json)
        val userObject = jsonObject.optJSONObject(email) ?: return null
        val nombre = userObject.optString("nombre")
        val telefono = userObject.optString("telefono")
        return Pair(nombre, telefono)
    } catch (e: IOException) {
        e.printStackTrace()
        return null
    }
}

@Composable
fun UserProfileScreen(context: Context, email: String) {
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }

    LaunchedEffect(email) {
        loadUserData(context, email)?.let { (nombre, telefono) ->
            name = nombre
            phone = telefono
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Nombre: $name")
        Text(text = "Teléfono: $phone")
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController, email: String) {
    val context = LocalContext.current
    val (name, phone) = loadUserData(context, email) ?: Pair("Desconocido", "Desconocido")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Perfil") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
//            UserProfileScreen(context = LocalContext.current, email = email)
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(text = "Nombre: $name", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Text(text = "Correo: $email", fontSize = 16.sp, color = Color.Gray)
                Text(text = "Teléfono: $phone", fontSize = 16.sp, color = Color.Gray)
            }

            Spacer(modifier = Modifier.height(40.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Cambiar Contraseña",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    label = { Text("Contraseña Anterior") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    label = { Text("Nueva Contraseña") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    label = { Text("Confirmar Contraseña") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {},
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = BlueTEC,
                        contentColor = Color.White
                    ),
                ) {
                    Text(text = "Actualizar")
                }
            }
        }
    }
}

