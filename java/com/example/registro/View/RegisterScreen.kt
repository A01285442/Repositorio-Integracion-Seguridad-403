package com.example.registro.View

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.registro.R
import com.example.registro.ui.theme.RegistroTheme

@Composable
fun RegisterScreen(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "name_screen") {
        composablea("name_screen") { NameScreen(navController) }
        composable("dob_screen") { DateOfBirthScreen(navController) }
        composable("gender_screen") { GenderScreen(navController) }
        composable("email_screen") { EmailScreen(navController) }
        composable("password_screen") { PasswordScreen(navController) }
    }
}

@Composable
fun NameScreen(navController: NavHostController) {
    var name by remember { mutableStateOf("") }
    RegisterTemplate(
        progress = 1,
        currentStep = 1,
        label = "Your Full Name",
        value = name,
        onValueChange = { name = it },
        onContinue = { navController.navigate("dob_screen") }
    )
}

@Composable
fun DateOfBirthScreen(navController: NavHostController) {
    var dob by remember { mutableStateOf("") }
    RegisterTemplate(
        progress = 2,
        currentStep = 2,
        label = "Date of Birth",
        value = dob,
        onValueChange = { dob = it },
        onContinue = { navController.navigate("gender_screen") }
    )
}

@Composable
fun GenderScreen(navController: NavHostController) {
    var gender by remember { mutableStateOf("") }
    RegisterTemplate(
        progress = 3,
        currentStep = 3,
        label = "Select your gender",
        value = gender,
        onValueChange = { gender = it },
        onContinue = { navController.navigate("email_screen") }
    )
}

@Composable
fun EmailScreen(navController: NavHostController) {
    var email by remember { mutableStateOf("") }
    RegisterTemplate(
        progress = 4,
        currentStep = 4,
        label = "Your Email",
        value = email,
        onValueChange = { email = it },
        onContinue = { navController.navigate("password_screen") }
    )
}

@Composable
fun PasswordScreen(navController: NavHostController) {
    var password by remember { mutableStateOf("") }
    RegisterTemplate(
        progress = 5,
        currentStep = 5,
        label = "Your Password",
        value = password,
        onValueChange = { password = it },
        onContinue = { /* Final action or navigate to another screen */ }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterTemplate(
    progress: Int,
    currentStep: Int,
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    onContinue: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Register") },
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
                painter = painterResource(id = R.drawable.ima_reg), // Cambiar esto por recurso de logo
                contentDescription = "Logo",
                modifier = Modifier
                    .size(300.dp)
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
                            .size(24.dp)
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
                        Divider(
                            color = if (index+1 < progress) Color.Blue else Color.Gray,
                            thickness = 4.dp,
                            modifier = Modifier
                                .width(40.dp)
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
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
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
            TextButton(onClick = { /* Acción para iniciar sesión */ }) {
                Text("I already have an account Log in", color = Color.Blue)
            }
        }
    }
}