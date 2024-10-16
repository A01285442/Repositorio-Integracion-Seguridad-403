package com.example.legalmatch.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
            containerColor = MaterialTheme.colorScheme.surface
        ) {
            NavigationBarItem(
                icon = { Icon(Icons.Default.Menu, contentDescription = "Mis Casos") },
                selected = false,
                onClick = { navController.navigate(Routes.CasosCliente.route) }
            )
            NavigationBarItem(
                icon = { Image(
                    painter = painterResource(id = R.drawable.news),
                    contentDescription = "news",
                    modifier = Modifier.size(34.dp)
                ) },
                selected = false,
                onClick = { navController.navigate("NoticiasCliente") }
            )
            NavigationBarItem(
                icon = {
                    Image(
                        painter = painterResource(id = R.drawable.profile),
                        contentDescription = "Profile",
                        modifier = Modifier.size(34.dp)
                    )
                },
                selected = false,
                onClick = { navController.navigate(Routes.Perfil.route) }
            )
        }
    }

}
