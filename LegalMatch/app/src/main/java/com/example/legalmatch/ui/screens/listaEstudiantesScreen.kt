package com.example.legalmatch.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.app.navigation.Routes
import com.example.legalmatch.ui.components.CustomBottomBar
import com.example.legalmatch.ui.components.CustomTopBar
import com.example.legalmatch.ui.components.EstudianteItem
import com.example.legalmatch.viewmodel.UsuariosViewModel

@Composable
fun ListaEstudiantesScreen(
    navController: NavController,
    viewmodel: UsuariosViewModel
) {

    val state = viewmodel.state
    var matricula by remember { mutableStateOf("") }
    var nombre by remember { mutableStateOf("") }

    // Estado para manejar la visualización de la Snackbar
    val snackbarHostState = remember { SnackbarHostState() }

    // Mostrar el contenido según el estado actual
    if (state.isLoading) {
        CircularProgressIndicator()
    }

    Scaffold(
        topBar = { CustomTopBar(title = "Estudiantes registrados", navIcon = true, actIcon = false, navController, Routes.Perfil.route) },
        bottomBar = { CustomBottomBar(navController = navController)}, // Usa el navController pasado
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->

        Column(
            Modifier.padding(padding)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween, // Esto empuja el botón hacia la derecha

            ) {
                // Nombre y correo del estudiante a la izquierda
                Column(
                    modifier = Modifier.weight(1f) // Permite que el contenido ocupe el espacio disponible
                ) {
                    OutlinedTextField(
                        value = nombre,
                        onValueChange = { nombre = it },
                        label = { Text("Nombre") },
                        readOnly = false,  // Lo marcamos como solo lectura
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 2.dp)
                            .clickable { /*onClick()*/ }  // Al hacer clic, mostramos el DatePicker
                    )
                    OutlinedTextField(
                        value = matricula,
                        onValueChange = { matricula = it },
                        label = { Text("Matricula") },
                        readOnly = false,  // Lo marcamos como solo lectura
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 2.dp)
                            .clickable { /*onClick()*/ }  // Al hacer clic, mostramos el DatePicker
                    )
                    Button(
                        onClick = {
                            viewmodel
                            viewmodel.creaEstudiante(nombre, matricula)
                                  },
                        colors = ButtonColors(MaterialTheme.colorScheme.primary, Color.White, Color.Gray, Color.Gray),
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(vertical = 2.dp),
                    ) {
                        Text("Crear Estudiante")
                    }
                    // Lanzar la corrutina de Snackbar usando LaunchedEffect
                    LaunchedEffect(viewmodel.state.errorAnadiendoEstudiante) {
                        if (viewmodel.state.errorAnadiendoEstudiante) {
                            snackbarHostState.showSnackbar("Ya existe un usuario con ese correo.")
                        }
                    }
                }



            }

            // Línea divisoria entre los ítems
            HorizontalDivider()

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {


                items(state.estudiantes) { estudiante ->
                    EstudianteItem(estudiante, onDeleteClick = {viewmodel.eliminaEstudiante(estudiante.id)})
                }
        }


        }
    }
}


