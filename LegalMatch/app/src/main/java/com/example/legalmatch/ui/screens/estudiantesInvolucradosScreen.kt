package com.example.legalmatch.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.app.navigation.Routes
import com.example.legalmatch.ui.components.CustomBottomBar
import com.example.legalmatch.ui.components.CustomTopBar
import com.example.legalmatch.ui.components.EstudianteItem
import com.example.legalmatch.viewmodel.EstudiantesInvolucradosViewModel
import com.example.legalmatch.viewmodel.UsuariosViewModel

private const val TAG = "MainActivity"

@Composable
fun EstudiantesInvolucradosScreen(navController: NavController, viewmodel: UsuariosViewModel, casoId: Int, estudiantesInvolucradosViewModel: EstudiantesInvolucradosViewModel) {

    estudiantesInvolucradosViewModel.getEstudiantesInvolucrados(casoId)
    val state = estudiantesInvolucradosViewModel.state
    val estudiantesState = viewmodel.state
    var selectedRole by remember { mutableStateOf("Selecciona un estudiante") }
    var selectedUserId by remember { mutableStateOf(1) }
    val opciones = estudiantesState.estudiantes

    // Mostrar el contenido según el estado actual
    if (state.isLoading) {
        CircularProgressIndicator()
    }

    Scaffold(
        topBar = { CustomTopBar(title = "Estudiantes Involucrados", navIcon = true, actIcon = false, navController, Routes.Perfil.route) },
        bottomBar = { CustomBottomBar(navController = navController)} // Usa el navController pasado
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

                    var expandedRole by remember { mutableStateOf(false) }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(12.dp))
                            .padding(horizontal = 12.dp, vertical = 4.dp)
                    ) {
                        TextButton(
                            onClick = { expandedRole = !expandedRole },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = selectedRole,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }

                        DropdownMenu(
                            expanded = expandedRole,
                            onDismissRequest = { expandedRole = false }
                        ) {
                            opciones.forEach { usuario ->
                                DropdownMenuItem(
                                    text = { Text("${usuario.matricula} - ${usuario.nombre}") },
                                    onClick = {
                                        selectedRole = usuario.nombre
                                        selectedUserId = usuario.id
                                        expandedRole = false
                                    }
                                )
                            }
                        }
                    }

                    Button(
                        onClick = {
                            estudiantesInvolucradosViewModel.addEstudianteInvolucrado(casoId,selectedUserId)
                            estudiantesInvolucradosViewModel.getEstudiantesInvolucrados(casoId)
                                  },
                        colors = ButtonColors(MaterialTheme.colorScheme.primary, Color.White, Color.Gray, Color.Gray),
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(vertical = 2.dp),
                    ) {
                        Text("Añadir Estudiante")
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
                    val UsuarioEstudiante = viewmodel.getEstudianteInfo(estudiante.estudiante_id)

                    if (UsuarioEstudiante != null) {
                        EstudianteItem(UsuarioEstudiante, onDeleteClick = {
                            estudiantesInvolucradosViewModel.deleteEstudianteInvolucrado(casoId,estudiante.estudiante_id)
                        })
                    }
                }
            }


        }
    }
}

