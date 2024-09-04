package com.example.pantallasaxel1.views


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
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.pantallasaxel1.R
import androidx.navigation.compose.composable
import com.example.pantallasaxel1.ui.theme.BlueTEC
import com.example.pantallasaxel1.ui.theme.RegistroTheme


@Composable
fun RegisterScreen(navController: NavController) {
    RegistroTheme{
        var currentStep by remember { mutableStateOf(1) }

        when (currentStep) {
            1 -> NameScreen(onContinue = { currentStep = 2 })
            2 -> DateOfBirthScreen(onContinue = { currentStep = 3 })
            3 -> GenderScreen(onContinue = { currentStep = 4 })
            4 -> EmailScreen(onContinue = { currentStep = 5 })
            5 -> PasswordScreen(onContinue = {})
        }

    }

}

@Composable
fun NameScreen(onContinue: () -> Unit) {
    var name by remember { mutableStateOf("") }
    RegisterTemplate(
        progress = 1,
        currentStep = 1,
        label = "Dame tu nombre completo",
        value = name,
        onValueChange = { name = it },
        onContinue = onContinue
    )
}

@Composable
fun DateOfBirthScreen(onContinue: () -> Unit) {
    var dob by remember { mutableStateOf("") }
    RegisterTemplate(
        progress = 2,
        currentStep = 2,
        label = "Fecha de nacimiento",
        value = dob,
        onValueChange = { dob = it },
        onContinue = onContinue
    )
}

@Composable
fun GenderScreen(onContinue: () -> Unit) {
    var gender by remember { mutableStateOf("") }
    RegisterTemplate(
        progress = 3,
        currentStep = 3,
        label = "Cual es tu género",
        value = gender,
        onValueChange = { gender = it },
        onContinue = onContinue
    )
}

@Composable
fun EmailScreen(onContinue: () -> Unit) {
    var email by remember { mutableStateOf("") }
    RegisterTemplate(
        progress = 4,
        currentStep = 4,
        label = "Tu E-mail",
        value = email,
        onValueChange = { email = it },
        onContinue = onContinue
    )
}

@Composable
fun PasswordScreen(onContinue: () -> Unit) {
    var password by remember { mutableStateOf("") }
    RegisterTemplate(
        progress = 5,
        currentStep = 5,
        label = "Tu contraseña",
        value = password,
        onValueChange = { password = it },
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
                        Divider(
                            color = if (index+1 < progress) BlueTEC else Color.Gray,
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
            TextButton(onClick = { /* Acción para iniciar sesión */ }) {
                Text("I already have an account Log in", color = BlueTEC)
            }
        }
    }
}
