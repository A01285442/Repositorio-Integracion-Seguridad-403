package com.example.app.navigation

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.legalmatch.ui.screens.AsesoriaScreen
import com.example.legalmatch.ui.screens.AsesoriaViewModel
import com.example.legalmatch.ui.screens.CasoDetalleScreen
import com.example.legalmatch.ui.screens.CasosScreen
import com.example.legalmatch.ui.screens.CasosViewModel
import com.example.legalmatch.ui.screens.LoginViewModel
import com.example.legalmatch.ui.screens.PerfilScreen
import com.example.proyectobueno.views.LoginScreen
import com.example.proyectobueno.views.RegisterScreen

val TAG = "MainActivity"

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavGraph(navController: NavHostController, asesoriasViewModel: AsesoriaViewModel) {
    NavHost(
        navController = navController,
        startDestination = Routes.Login.route
    ) {

        composable(Routes.Asesorias.route) {
            Log.d(TAG, "Navigating to AsesoriasScreen")
            AsesoriaScreen(navController, asesoriasViewModel)
        }
        composable(Routes.Casos.route){
            val casosViewModel : CasosViewModel = viewModel()
            CasosScreen(navController, casosViewModel)
        }
        composable(Routes.Perfil.route){
            val loginViewModel: LoginViewModel = viewModel()
            PerfilScreen(navController, loginViewModel)
        }
        composable(Routes.Register.route){
            val loginViewModel: LoginViewModel = viewModel()
            RegisterScreen(navController, loginViewModel)
        }
        composable(Routes.Login.route){
            val loginViewModel: LoginViewModel = viewModel()
            LoginScreen(navController, loginViewModel)
        }

        composable(
            route = Routes.CasoDetalle.route,
            arguments = listOf(navArgument("itemId") { type = NavType.IntType })
        ) { backStackEntry ->
            val itemId = backStackEntry.arguments?.getInt("itemId")
            CasoDetalleScreen(navController, CasosViewModel(), itemId)
        }
    }
}
