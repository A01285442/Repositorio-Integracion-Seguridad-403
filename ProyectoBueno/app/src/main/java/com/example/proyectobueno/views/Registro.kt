package com.example.proyectobueno.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.proyectobueno.R
import androidx.navigation.compose.composable
import com.example.proyectobueno.ui.theme.BlueTEC
import com.example.proyectobueno.ui.theme.RegistroTheme
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.postgrest.from

val supabase = createSupabaseClient(
    supabaseUrl = "https://qcbrsxgfoadcbvbhmbai.supabase.co",
    supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InFjYnJzeGdmb2FkY2J2YmhtYmFpIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MjY3NzQ0NjQsImV4cCI6MjA0MjM1MDQ2NH0.8TrdE-W_ACszmnsgoolg4eAFS-1g0NS3H3PNUnZibwM"
) {
    install(Auth)
}

@Composable
fun RegisterScreen(navController: NavController) {
    RegistroTheme{
        var currentStep by remember { mutableStateOf(1) }

        // Variables para almacenar los datos del usuario
        var name by remember { mutableStateOf("") }
        var dateBirth by remember { mutableStateOf("") }
        var gender by remember { mutableStateOf("") }
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }

        // Estado para controlar cuando insertar
        var shouldInsert by remember { mutableStateOf(false) }

        when (currentStep) {
            1 -> NameScreen(name = name, onValueChange = { name = it }, onContinue = { currentStep = 2 })
            2 -> DateOfBirthScreen(dateBirth = dateBirth, onValueChange = { dateBirth = it }, onContinue = { currentStep = 3 })
            3 -> GenderScreen(gender = gender, onValueChange = { gender = it }, onContinue = { currentStep = 4 })
            4 -> EmailScreen(email = email, onValueChange = { email = it }, onContinue = { currentStep = 5 })
            5 -> PasswordScreen(password = password, onValueChange = { password = it }, onContinue = {
                shouldInsert = true
            })
        }

        // Lógica para insertar en Supabase
        if (shouldInsert) {
            LaunchedEffect(Unit) {
                val user = mapOf(
                    "name" to name,
                    "dateBirth" to dateBirth,
                    "gender" to gender,
                    "email" to email,
                    "password" to password
                )

                try {
                    supabase.from("registro").insert(user)
                    // Una vez insertado, realizar la navegación
                    navController.navigate("HomeScreen")
                } catch (e: Exception) {
                    e.printStackTrace() // Manejo de errores
                } finally {
                    shouldInsert = false
                }
            }
        }

    }

}






@Composable
fun NameScreen(name: String, onValueChange: (String) -> Unit, onContinue: () -> Unit) {

    RegisterTemplate(
        progress = 1,
        currentStep = 1,
        label = "Dame tu nombre completo",
        value = name,
        onValueChange = onValueChange,
        onContinue = onContinue
    )
}

@Composable
fun DateOfBirthScreen(dateBirth: String, onValueChange: (String) -> Unit, onContinue: () -> Unit) {

    RegisterTemplate(
        progress = 2,
        currentStep = 2,
        label = "Fecha de nacimiento",
        value = dateBirth,
        onValueChange = onValueChange,
        onContinue = onContinue
    )
}

@Composable
fun GenderScreen(gender: String, onValueChange: (String) -> Unit, onContinue: () -> Unit) {

    RegisterTemplate(
        progress = 3,
        currentStep = 3,
        label = "Cual es tu género",
        value = gender,
        onValueChange = onValueChange,
        onContinue = onContinue
    )
}

@Composable
fun EmailScreen(email: String, onValueChange: (String) -> Unit, onContinue: () -> Unit) {

    RegisterTemplate(
        progress = 4,
        currentStep = 4,
        label = "Tu E-mail",
        value = email,
        onValueChange = onValueChange,
        onContinue = onContinue
    )
}

@Composable
fun PasswordScreen(password: String, onValueChange: (String) -> Unit, onContinue: () -> Unit) {

    RegisterTemplate(
        progress = 5,
        currentStep = 5,
        label = "Tu contraseña",
        value = password,
        onValueChange = onValueChange,
        visualTransformation = PasswordVisualTransformation(),
        onContinue = onContinue
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterTemplate(
    progress: Int,
    currentStep: Int,
    label: String,
    value: String,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onValueChange: (String) -> Unit,
    onContinue: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text (
                        text = "Registro",
                        color = BlueTEC,
                        fontWeight = FontWeight.Bold

                    )
                },
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // Logo más grande
            Image(
                painter = painterResource(id = R.drawable.casaazul),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(200.dp)
                    .padding(16.dp)
            )

            Text(
                text = "Create account",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Indicadores de progreso con palomita
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                repeat(5) { index ->
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(20.dp)
                            .clip(CircleShape)
                            .background(if (index < progress) Color.Blue else Color.Gray) // Colorea según progreso
                    ) {
                        if (index < progress) {
                            Icon(
                                imageVector = ImageVector.vectorResource(id = R.drawable.ic_launcher_foreground),  // Usa un ícono de check
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                    if (index < 4) {
                        Spacer(modifier = Modifier.width(8.dp))
                        HorizontalDivider(
                            modifier = Modifier
                                .width(40.dp),
                            thickness = 4.dp,
                            color = if (index+1 < progress) BlueTEC else Color.Gray
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Campo de texto
            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                label = { Text(label) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )

            // Botón de continuar
            Button(
                onClick = onContinue,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {
                Text("Continue")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Texto de inicio de sesión
            TextButton(onClick = { }) {
                Text("I already have an account Log in", color = BlueTEC)
            }
        }
    }
}