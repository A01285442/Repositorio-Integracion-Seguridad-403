package com.example.legalmatch.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.app.navigation.Routes
import com.example.legalmatch.R

@Composable
fun CustomBottomBarClientes(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                color = Color.Gray,
                offsetX = 0.dp,
                offsetY = (-4).dp,
                blurRadius = 15.dp
            )
    ) {
        NavigationBar(
            containerColor = Color.White
        ) {
            NavigationBarItem(
                icon = { Icon(Icons.Default.Menu, contentDescription = "Mis Casos") },
                label = { Text("Actividad") },
                selected = false,
                onClick = { navController.navigate(Routes.CasosCliente.route) }
            )
            NavigationBarItem(
                icon = { Image(
                    painter = painterResource(id = R.drawable.news),
                    contentDescription = "news",
                    modifier = Modifier.size(34.dp)
                ) },
                ///label = { Text("Casos") },
                selected = false,
                onClick = { navController.navigate("NoticiasCliente") }
            )
            NavigationBarItem(
                icon = { Icon(Icons.Default.Person, contentDescription = "Perfil") },
                label = { Text("Perfil") },
                selected = false,
                onClick = { navController.navigate(Routes.PerfilCliente.route) }
            )
        }
    }

}
