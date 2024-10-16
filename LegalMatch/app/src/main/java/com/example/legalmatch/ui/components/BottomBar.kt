package com.example.legalmatch.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.example.legalmatch.R

@Composable
fun CustomBottomBar(navController: NavController) {
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
            containerColor = Color.White,
            modifier = Modifier.height(75.dp)
        ) {
            NavigationBarItem(
                icon = { Image(
                        painter = painterResource(id = R.drawable.calendar),
                        contentDescription = "Asesorías",
                        modifier = Modifier.size(32.dp)

                    ) },

                selected = false,
                onClick = { navController.navigate("asesorias") }
            )
            NavigationBarItem(
                icon = { Image(
                    painter = painterResource(id = R.drawable.portfolio),
                    contentDescription = "Casos",
                    modifier = Modifier.size(32.dp)
                ) },

                selected = false,
                onClick = { navController.navigate("casos") }
            )
            NavigationBarItem(
                icon = { Image(
                    painter = painterResource(id = R.drawable.news),
                    contentDescription = "news",
                    modifier = Modifier.size(34.dp)
                ) },

                selected = false,
                onClick = { navController.navigate("Noticias") }
            )

            NavigationBarItem(
                icon = {
                    Image(
                        painter = painterResource(id = R.drawable.profile),
                        contentDescription = "Profile",
                        modifier = Modifier.size(28.dp)
                    )
                },

                selected = false,
                onClick = { navController.navigate("perfil") }
            )
            NavigationBarItem(
                icon = { Image(
                    painter = painterResource(id = R.drawable.stats),
                    contentDescription = "Stats",
                    modifier = Modifier.size(28.dp)
                )},

                selected = false,
                onClick = { navController.navigate("Stats") }
            )


        }
    }

}
