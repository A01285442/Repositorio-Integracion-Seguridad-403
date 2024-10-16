package com.example.legalmatch.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.app.navigation.Routes
import com.example.legalmatch.R

@Composable
fun CustomBottomBar(
    navController: NavController
) {
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
        val items = listOf(
            R.drawable.calendar,
            R.drawable.portfolio,
            R.drawable.news,
            R.drawable.profile,
            R.drawable.stats
        )
        val sizes = listOf(
            32.dp,
            32.dp,
            34.dp,
            28.dp,
            28.dp
        )
        //var selectedItem by remember { mutableIntStateOf(0) }
        NavigationBar(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.height(75.dp)
        ) {

            items.forEachIndexed {index, item ->
                NavigationBarItem(
                    icon = { Image(
                        painter = painterResource(id = item),
                        contentDescription = "",
                        modifier = Modifier.size(sizes[index])
                    ) },
                    //label = {Text("item")},
                    //selected = selectedItem == index,
                    selected = false,
                    onClick = {
                        //selectedItem = index
                        when (index) {
                            0 -> navController.navigate(Routes.Asesorias.route)
                            1 -> navController.navigate(Routes.Casos.route)
                            2 -> navController.navigate(Routes.Noticias.route)
                            3 -> navController.navigate(Routes.Perfil.route)
                            4 -> navController.navigate(Routes.Stats.route)
                        }
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,  // Color del ícono cuando está seleccionado
                        unselectedIconColor = Color.Gray, // Color del ícono cuando NO está seleccionado
                        selectedTextColor = Color.White,  // Color del texto cuando está seleccionado
                        unselectedTextColor = Color.Gray  // Color del texto cuando NO está seleccionado
                    )
                )
            }


        }
    }

}
