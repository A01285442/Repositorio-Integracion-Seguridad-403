package com.example.proyectobueno


import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.proyectobueno.ui.theme.ProyectoBuenoTheme
import com.example.proyectobueno.views.ClientView
import com.example.proyectobueno.views.DetallesDeCaso
import com.example.proyectobueno.views.LoginScreen
import com.example.proyectobueno.views.RegisterScreen
import com.example.proyectobueno.views.MessagingViewAbogado
import com.example.proyectobueno.views.ProfileScreen
import com.example.proyectobueno.views.Casos
import com.example.proyectobueno.views.MessagingView
import com.example.proyectobueno.views.MessagingViewChat
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth

class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProyectoBuenoTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "login",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("login") {
                            LoginScreen(navController = navController)
                        }
                        composable("casos"){
                            Casos(navController = navController)
                        }
                        composable("DetallesDecaso"){
                            DetallesDeCaso(navController = navController)
                        }
                        composable("MessagingView"){
                            MessagingViewChat(navController = navController)
                        }
                        composable("Registro") {
                            RegisterScreen(navController = navController)
                        }

                        composable("Cliente"){
                            ClientView(navController = navController)
                        }

                        composable ("ChatAbogado"){
                            MessagingViewAbogado(navController = navController)
                        }

                        composable("perfil/{email}") { backStackEntry ->
                            val email = backStackEntry.arguments?.getString("email") ?: ""
                            ProfileScreen(navController = navController, email = email)
                        }


                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    ProyectoBuenoTheme {
        val navController = rememberNavController()
        LoginScreen(navController = navController)
    }
}