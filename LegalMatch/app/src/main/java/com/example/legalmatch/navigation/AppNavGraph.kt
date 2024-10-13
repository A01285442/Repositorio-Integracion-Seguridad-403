package com.example.app.navigation

import android.annotation.SuppressLint
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
import com.example.legalmatch.Noticias.AddNewsScreen
import com.example.legalmatch.Noticias.NoticiasScreen
import com.example.legalmatch.Noticias.NoticiasViewModel
import com.example.legalmatch.ui.screens.AsesoriaDetalleScreen
import com.example.legalmatch.ui.screens.AsesoriaScreen
import com.example.legalmatch.ui.screens.AsesoriaViewModel
import com.example.legalmatch.ui.screens.CasoDetalleScreen
import com.example.legalmatch.ui.screens.CasosClienteScreen
import com.example.legalmatch.ui.screens.CasosScreen
import com.example.legalmatch.ui.screens.CasosViewModel
import com.example.legalmatch.ui.screens.EstudiantesInvolucradosScreen
import com.example.legalmatch.ui.screens.FormAsesoriaScreen
import com.example.legalmatch.ui.screens.FormCasoScreen
import com.example.legalmatch.ui.screens.ListaEstudiantesScreen
import com.example.legalmatch.ui.screens.LoginViewModel
import com.example.legalmatch.ui.screens.PerfilScreen
import com.example.legalmatch.ui.screens.StatsScreen
import com.example.legalmatch.viewmodel.EstudiantesInvolucradosViewModel
import com.example.legalmatch.viewmodel.GraficasViewModel
import com.example.legalmatch.viewmodel.SearchViewModel
import com.example.legalmatch.viewmodel.UsuariosViewModel
import com.example.proyectobueno.views.LoginScreen
import com.example.proyectobueno.views.RegisterScreen

val TAG = "MainActivity"

@SuppressLint("StateFlowValueCalledInComposition")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavGraph(navController: NavHostController, asesoriasViewModel: AsesoriaViewModel) {

    val loginViewModel: LoginViewModel = viewModel()
    val casosViewModel : CasosViewModel = viewModel()
    val usuariosViewModel: UsuariosViewModel = viewModel()
    val asesoriasViewModel: AsesoriaViewModel = viewModel()
    val searchBoxViewModel : SearchViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = Routes.Login.route
    ) {

        // VISTAS DE INICIO DE SESIÓN
        composable(Routes.Login.route){
            Log.d(TAG, "Navigating to Login")
            LoginScreen(navController, loginViewModel)
        }
        composable(Routes.Register.route){
            Log.d(TAG, "Navigating to Register")
            RegisterScreen(navController, loginViewModel)
        }

        // VISTAS DE ABOGADO PRINCIPALES

        composable(Routes.Asesorias.route) {
            Log.d(TAG, "Navigating to Asesorias")
            AsesoriaScreen(navController, asesoriasViewModel,loginViewModel)
        }
        composable(Routes.Casos.route){
            Log.d(TAG, "Navigating to Casos")
            CasosScreen(navController, casosViewModel, searchBoxViewModel)
        }
        composable(Routes.Stats.route){
            val graficasViewModel: GraficasViewModel = viewModel()
            StatsScreen(navController, graficasViewModel )
        }
        composable(Routes.Perfil.route){
            Log.d(TAG, "Navigating to Perfil")
            PerfilScreen(navController, loginViewModel)
        }

        // VISTAS DE ABOGADO SECUNDARIAS
        composable(
            route = Routes.CasoDetalle.route,
            arguments = listOf(navArgument("itemId") { type = NavType.IntType })
        ) { backStackEntry ->
            val itemId = backStackEntry.arguments?.getInt("itemId")
            if (itemId != null) {
                Log.d(TAG, "Navigating to Caso Detalle")
                CasoDetalleScreen(navController, casosViewModel, itemId, usuariosViewModel, loginViewModel)
            }
        }
        composable(
            route = Routes.EditCaso.route,
            arguments = listOf(navArgument("itemId") { type = NavType.IntType })
        ) { backStackEntry ->
            val itemId = backStackEntry.arguments?.getInt("itemId")
            if (itemId != null) {
                Log.d(TAG, "Navigating to Edit Caso")
                FormCasoScreen(navController, casosViewModel, itemId)
            }
        }
        composable(
            route = Routes.AsesoriaDetalle.route,
            arguments = listOf(navArgument("itemId") { type = NavType.IntType })
        ) { _backStackEntry ->
            val itemId = _backStackEntry.arguments?.getInt("itemId")
            if (itemId != null) {
                Log.d(TAG, "Navigating to Asesoría Detalle")
                AsesoriaDetalleScreen(navController, asesoriasViewModel, itemId, usuariosViewModel, casosViewModel, loginViewModel)
            }
        }
        composable(Routes.ListaEstudiantes.route) {
            Log.d(TAG, "Navigating to Lista de Estudiantes")
            ListaEstudiantesScreen(navController, UsuariosViewModel())
        }
        composable(Routes.FormCaso.route){
            Log.d(TAG, "Navigating to Form Caso")
            FormCasoScreen(navController, casosViewModel)
        }
        composable(Routes.Noticias.route){
            Log.d(TAG, "Navigating to Noticias")
            NoticiasScreen(navController, NoticiasViewModel())
        }
        composable(Routes.AddNews.route){
            Log.d(TAG, "Navigating to Add News")
            val noticiasViewModel: NoticiasViewModel = viewModel()
            AddNewsScreen(noticiasViewModel = noticiasViewModel, navController = navController)
        }
        composable(
            route = Routes.EstudiantesInvolucrados.route,
            arguments = listOf(navArgument("itemId"){type = NavType.IntType})
        ) { entradaAtras ->
            val itemId = entradaAtras.arguments?.getInt("itemId")
            if (itemId != null) {
                Log.d(TAG, "Navigating to Estudiantes Involucrados")
                val estudiantesInvolucradosViewModel: EstudiantesInvolucradosViewModel = viewModel()
                EstudiantesInvolucradosScreen(navController, usuariosViewModel, itemId, estudiantesInvolucradosViewModel)
            }
        }


        // VISTAS DE CLIENTE PRINCIPALES
        composable(Routes.CasosCliente.route){
            Log.d(TAG, "Navigating to Casos Cliente")
            //val casosclientevm : casosClienteViewModel = viewModel()
            CasosClienteScreen(navController, loginViewModel)
        }

        composable(Routes.FormAsesoria.route){
            Log.d(TAG, "Navigating to Forms Asesorías")
            FormAsesoriaScreen(navController, asesoriasViewModel)
        }

    }
}