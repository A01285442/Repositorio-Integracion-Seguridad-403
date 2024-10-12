package com.example.app.navigation

sealed class Routes(val route: String) {

    // VISTAS DE INICIO DE SESIÓN
    object  Login : Routes("login")
    object  Register : Routes("register")

    // VISTAS DE ABOGADO PRINCIPALES
    object Asesorias : Routes("asesorias")
    object Casos : Routes("casos")
    object Perfil : Routes("perfil")
    object Stats : Routes("Stats")

    // VISTAS DE ABOGADO SECUNDARIAS
    object Noticias : Routes("Noticias")
    object AddNews : Routes("AddNews")
    object CasoDetalle : Routes("casoD/{itemId}") {
        fun createRoute(itemId: Int) = "casoD/$itemId"
    }
    object AsesoriaDetalle : Routes("asesoriaD/{itemId}"){
        fun createRoute(itemId: Int) = "asesoriaD/$itemId"
    }
    object ListaEstudiantes : Routes("estudiantes")
    object FormCaso : Routes("formCaso")
    object EstudiantesInvolucrados : Routes("EstudiantesInvolucrados/{itemId}"){
        fun createRoute(id: Int) = "EstudiantesInvolucrados/$id"
    }
    object EditCaso : Routes("edit_caso/{itemId}"){
        fun createRoute(id: Int) = "edit_caso/$id"
    }


    // VISTAS DE CLIENTE PRINCIPALES
    object CasosCliente : Routes("casosClientes")
    object PerfilCliente : Routes("perfilCliente")

    // VISTAS DE CLIENTE SECUNDARIAS
    object FormAsesoria : Routes("formAsesoria")
}
