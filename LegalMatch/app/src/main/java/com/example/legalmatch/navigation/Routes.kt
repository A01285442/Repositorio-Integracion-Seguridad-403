package com.example.app.navigation

sealed class Routes(val route: String) {

    // Vistas del abogado
    object Asesorias : Routes("asesorias")
    object Casos : Routes("casos")
    object Perfil : Routes("perfil")
    object CasoDetalle : Routes("detalle/{itemId}") {
        fun createRoute(itemId: Int) = "detalle/$itemId"
    }
    object ListaEstudiantes : Routes("estudiantes")


    // Vistas de Inicio de Sesión
    object  Login : Routes("login")
    object  Register : Routes("register")

    // Vistas del cliente





}
