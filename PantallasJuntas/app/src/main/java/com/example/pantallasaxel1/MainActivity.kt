@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.pantallasaxel1

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import com.example.jorge1.MessagingView
import com.example.pantallasaxel1.ui.theme.PantallasAxel1Theme
import com.example.pantallasaxel1.views.Categories
import com.example.pantallasaxel1.views.ClientView
import com.example.pantallasaxel1.views.DetallesDeCaso
import com.example.pantallasaxel1.views.LoginScreen
import com.example.pantallasaxel1.views.RegisterScreen
import com.example.pantallasaxel1.views.ClientView
import com.example.vistasproyecto.vistas.Casos



class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PantallasAxel1Theme {
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
                        composable("categories") {
                            Categories(navController = navController)
                        }
                        composable("casos"){
                            Casos(navController = navController)
                        }
                        composable("DetallesDecaso"){
                            DetallesDeCaso(navController = navController)
                        }
                        composable("MessagingView"){
                            MessagingView(navController = navController)
                        }
                        composable("Registro") {
                            RegisterScreen(navController = navController)
                        }

                        composable("Cliente"){
                            ClientView(navController = navController)
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
    PantallasAxel1Theme {
        val navController = rememberNavController()
        LoginScreen(navController = navController)
    }
}
