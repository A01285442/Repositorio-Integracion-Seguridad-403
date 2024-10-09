package com.example.legalmatch.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.legalmatch.data.api.models.Usuario

@Composable
fun EstudianteItem(estudiante: Usuario, onDeleteClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween // Esto empuja el botón hacia la derecha
    ) {
        // Nombre y correo del estudiante a la izquierda
        Column(
            modifier = Modifier.weight(1f) // Permite que el contenido ocupe el espacio disponible
        ) {
            Text(estudiante.nombre)
            Text(estudiante.correo)
        }

        // Botón de eliminar a la derecha
        Button(
            onClick = { onDeleteClick() },
            modifier = Modifier.padding(start = 8.dp), // Separar un poco el botón del contenido
            colors = ButtonColors(Color.Red, Color.White, Color.Gray, Color.Gray)
        ) {
            Text("Eliminar")
        }
    }

    // Línea divisoria entre los ítems
    HorizontalDivider()
}