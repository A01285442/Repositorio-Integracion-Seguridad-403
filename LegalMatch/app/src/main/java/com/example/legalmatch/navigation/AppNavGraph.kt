package com.example.app.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Routes.Login.route
    ) {
        composable(Routes.Asesorias.route) {
            AsesoriaScreen(navController, AsesoriaViewModel())
        }
        composable(Routes.Casos.route){
            CasosScreen(navController, CasosViewModel())
        }
        composable(Routes.Perfil.route){
            PerfilScreen(navController)
        }
        composable(Routes.Register.route){
            RegisterScreen(navController, LoginViewModel())
        }
        composable(Routes.Login.route){
            LoginScreen(navController, LoginViewModel())
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
