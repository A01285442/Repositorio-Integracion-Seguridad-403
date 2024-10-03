package com.example.proyectobueno.views


import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.app.navigation.Routes
import com.example.legalmatch.R
import com.example.legalmatch.data.api.models.Usuario
import com.example.legalmatch.ui.screens.LoginViewModel
import com.example.legalmatch.ui.theme.AzulTec
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


private const val TAG = "MainActivity"

//Login
@Composable
fun LoginScreen(navController: NavController,viewModel: LoginViewModel) {

    //val isAuthenticated by viewModel.isAuthenticated.collectAsState()
    //val errorMessage by viewModel.errorMessage.collectAsState()

    // UI para el inicio de sesión
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
            color = AzulTec,
            modifier = Modifier
                .padding(1.dp)
                .padding(10.dp)

        )

        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = "LogoAxel",
            modifier = Modifier.height(300.dp)

        )
        Spacer(modifier = Modifier.height(20.dp))

        TextField(
            value = username,
            onValueChange = { username = it
                loginError = false},
            label = { Text("E-mail") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = password,
            onValueChange = { password = it
                loginError = false
            },
            label = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(16.dp))

        if (loginError) {
            Text(text = "Usuario o Contraseña no Valido", color = Color.Red)
            Spacer(modifier = Modifier.height(8.dp))
        }

        Button(
            onClick = {
                if (username.isNotEmpty() && password.isNotEmpty()) {
                    Log.d(TAG, "Probando Login")
                    viewModel.login(username.lowercase(), password, onLoginSuccess = {
                        navController.navigate(Routes.Asesorias.route)
                    }
                    )
                }
                loginError = true
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = AzulTec,
                contentColor = Color.White
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Iniciar Sesión")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "¿No tienes una cuenta?", color = Color.Black)
        Text(
            text = "Regístrate",
            color = AzulTec,
            modifier = Modifier
                .clickable {
                    navController.navigate(Routes.Register.route)
                }
        )
        Spacer(modifier = Modifier.height(8.dp))
    }

}