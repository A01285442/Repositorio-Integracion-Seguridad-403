package com.example.legalmatch.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.legalmatch.ui.theme.AzulTec

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopBar(title: String, navIcon: Boolean, actIcon: Boolean, navController: NavController = rememberNavController(), rutaBackButton: String = "") {
    CenterAlignedTopAppBar(
        modifier = Modifier.shadow(8.dp),  // Añadir sombra
        colors = TopAppBarColors(
            containerColor = Color.White,
            scrolledContainerColor = Color.Black,
            navigationIconContentColor = Color.Black,
            titleContentColor = AzulTec,
            actionIconContentColor = Color.Black
        ),
        title = {
            Text(text = title, fontWeight = FontWeight.Bold)
        },

        navigationIcon = {
            if(navIcon){
                IconButton(onClick = { navController.navigate(route = rutaBackButton) }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Localized description"
                    )
                }
            }

        },
        actions = {
            if(actIcon){
                IconButton(onClick = { /* do something */ }) {
                    Icon(
                        imageVector = Icons.Filled.MoreVert,
                        contentDescription = "Localized description"
                    )
                }
            }

        },

    )
}
