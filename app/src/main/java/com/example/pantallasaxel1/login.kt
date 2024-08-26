package com.example.pantallasaxel1


import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.pantallasaxel1.ui.theme.BlueTEC


@Composable
fun LoginScreen(navController: NavController,modifier: Modifier = Modifier) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var loginError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "LEGAL MATCH",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = BlueTEC,
            modifier = Modifier.padding(1.dp)
                .padding(10.dp)

        )



        Image(
            painter = painterResource(id = R.drawable.casaazul),
            contentDescription = "LogoAxel",
            modifier = Modifier.height(300.dp)

        )
        Spacer(modifier = Modifier.height(20.dp))

        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("E-mail") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(16.dp))

        if (loginError) {
            Text(text = "Invalid credentials", color = Color.Red)
            Spacer(modifier = Modifier.height(8.dp))
        }

        Button(
            onClick = {
                navController.navigate("categories")

            },
            colors = ButtonDefaults.buttonColors(
                containerColor = BlueTEC,
                contentColor = Color.White
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Iniciar")
        }
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "¿No tienes una cuenta?", color = Color.Black)
        Text(text = "Registrate", color = BlueTEC)
        Spacer(modifier = Modifier.height(8.dp))
    }


    }

